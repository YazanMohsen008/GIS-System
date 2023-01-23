package com.fiteprojects.fitegis.Services;

import com.fiteprojects.fitegis.Models.Lecture;
import com.fiteprojects.fitegis.Models.DTO.LectureDTO;
import com.fiteprojects.fitegis.Repositories.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service("LectureService")
public class LectureService extends GenericService<LectureRepository, Lecture> {
    @Autowired
    CourseService courseService;
    @Autowired
    LocationService locationService;

    private void refreshLecturesTime() {
        repository.findAll().forEach(lecture -> {
            extractTime(lecture);
        });
    }

    @Override
    public List<Lecture> getAll(String lang, Integer page, Integer pageSize) throws Exception {
        refreshLecturesTime();
        List<Lecture> models = new ArrayList<>();
        try {
            repository.findAll().forEach(lecture -> {
                lecture.getCourse().setNameByLang(lang);
                lecture.setLecturerName(lang);
                try {
                    lecture.setLocationName(locationService.getById(lecture.getActualLocationId()).getName(lang));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                models.add(lecture);
            });
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
        return models;
    }

    @Override
    public Lecture getById(Integer id, String lang) throws Exception {
        Lecture lecture;
        try {
            lecture = repository.findById(id).get();
            extractTime(lecture);
            lecture.getCourse().setNameByLang(lang);
            lecture.setLecturerName(lang);
            try {
                lecture.setLocationName(locationService.getById(lecture.getActualLocationId()).getName(lang));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
        return lecture;
    }

    @Override
    public Lecture add(Lecture lecture) throws Exception {
        lecture.setLocation(locationService.getById(lecture.getLocationId()));
        lecture.setActualLocation(locationService.getById(lecture.getLocationId()));
        lecture.setCourse(courseService.getById(lecture.getCourseId()));
        extractTime(lecture);
        try {
            return repository.save(lecture);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    @Override
    public Lecture update(Lecture lecture) throws Exception {
        try {
            Lecture oldLecture = getById(lecture.getId());
            Lecture newLecture = genericConfig.update(oldLecture, lecture);
            if (lecture.getCourseId() != null) {
                newLecture.setCourse(courseService.getById(lecture.getCourseId()));
            }
            if (lecture.getLocationId() != null) {
                newLecture.setLocation(locationService.getById(lecture.getLocationId()));
                newLecture.setActualLocation(locationService.getById(lecture.getLocationId()));
            }
            if (lecture.getActualLocationId() != null) {
                newLecture.setActualLocation(locationService.getById(lecture.getActualLocationId()));
            }
            if (lecture.getActualStartTime() != null || lecture.getActualEndTime() != null) {
                Timestamp currentTime = null;
                if (lecture.getActualStartTime() != null)
                    currentTime = lecture.getActualStartTime();
                if (lecture.getActualEndTime() != null)
                    currentTime = lecture.getActualEndTime();
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Damascus"));
                if (lecture.getWeeksExpirationCount() == null)
                    lecture.setWeeksExpirationCount(1);
                LocalDateTime localDateTime = currentTime.toLocalDateTime().plusDays(7 * lecture.getWeeksExpirationCount());
                newLecture.setExpirationDate(Timestamp.valueOf(localDateTime));

            }
            newLecture.setUpdated(true);
            return repository.save(newLecture);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    private void extractTime(Lecture lecture) {
        Date now = new Date();
        if (lecture.getExpirationDate() != null && now.before(lecture.getExpirationDate()))
            return;

        lecture.setExpirationDate(null);
        lecture.setUpdated(false);
        lecture.setActualLocation(lecture.getLocation());
        Integer day = lecture.getDay();
        Time startTime = lecture.getStartTime();
        Time endTime = lecture.getEndTime();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Damascus"));
        calendar.set(Calendar.DAY_OF_WEEK, day);
        calendar.set(Calendar.AM_PM, 0);
        calendar.set(Calendar.HOUR, startTime.getHours());
        calendar.set(Calendar.MINUTE, startTime.getMinutes());
        Timestamp actualStartTime = new Timestamp(calendar.getTime().getTime());
        Date lectureDate=new Date(actualStartTime.getTime());
//        if (now.after(lectureDate)) {
//            LocalDateTime localDateTime = actualStartTime.toLocalDateTime().plusDays(7);
//            System.out.println(localDateTime);
//            lecture.setActualStartTime(Timestamp.valueOf(localDateTime));
//        } else
//            lecture.setActualStartTime(actualStartTime);
        System.out.println(calendar.getTime());

        calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Damascus"));
        calendar.set(Calendar.DAY_OF_WEEK, day);
        calendar.set(Calendar.AM_PM, 0);
        calendar.set(Calendar.HOUR, endTime.getHours());
        calendar.set(Calendar.MINUTE, endTime.getMinutes());
        Timestamp actualEndTime = new Timestamp(calendar.getTime().getTime());
        if (now.after(actualEndTime)) {
            LocalDateTime localDateTime = actualEndTime.toLocalDateTime().plusDays(7);
            System.out.println(localDateTime);
            lecture.setActualEndTime(Timestamp.valueOf(localDateTime));
        } else
            lecture.setActualEndTime(actualEndTime);
        System.out.println(calendar.getTime());

    }


    public Map<String, Lecture> getCurrentAndNextLecture(Integer hallID) {
        refreshLecturesTime();
        java.util.Date now = new java.util.Date();
        List<Lecture> lectures = repository.findAllByLocationIdSortByActualStartTime(hallID);
        Lecture currentLecture = null;
        int LectureID = 0;
        for (int i = 0; i < lectures.size(); i++) {
            Lecture lecture = lectures.get(i);
//            if (lecture.getExpirationDate() == null || now.after(lecture.getExpirationDate())) {
//                extractTime(lecture, false);
//                lecture.setExpirationDate(null);
//                lecture.setUpdated(false);
//                lecture.setActualLocation(lecture.getLocation());
//                repository.save(lecture);
//            }

            if (now.after(lecture.getActualStartTime()) && now.before(lecture.getActualEndTime())) {
                currentLecture = lecture;
                currentLecture.setActive(true);
                repository.save(lecture);
                LectureID = i;
                break;
            }
        }
        if (currentLecture == null)
            LectureID = 0;
        else
            LectureID++;
        Lecture nextLecture = null;
        if (lectures!=null && LectureID < lectures.size())
            nextLecture = lectures.get(LectureID);


        Map<String, Lecture> currentAndNext = new HashMap<>();
        currentAndNext.put("current", currentLecture);
        currentAndNext.put("next", nextLecture);
        return currentAndNext;
    }

    public List<LectureDTO> getAllLikeWord(String word) {
        refreshLecturesTime();
        List<LectureDTO> lectures = new ArrayList<>();
        List<Object[]> lecturesValues;
        lecturesValues = repository.getAllLikeWord(word);
        lecturesValues.forEach(
                lecture -> {
                    LectureDTO lectureDTO = new LectureDTO();
                    lectureDTO.setId((Integer) lecture[0]);
                    lectureDTO.setAr_name((String) lecture[1]);
                    lectureDTO.setEn_name((String) lecture[2]);
                    lectureDTO.setActualStartTime((Timestamp) lecture[3]);
                    lectureDTO.setActualEndTime((Timestamp) lecture[4]);
                    lectureDTO.setClassNumber((Integer) lecture[5]);
                    lectureDTO.setGroupNumber((Integer) lecture[6]);
                    lectureDTO.setSectionNumber((Integer) lecture[7]);
                    lectureDTO.setOthers((String) lecture[7]);
                    lectures.add(lectureDTO);
                }
        );
        return lectures;
    }

    public List<Lecture> getAllLectureLikeWord(String word) {
        refreshLecturesTime();
        List<Lecture> lectures = repository.getAllLectureLikeWord(word.toLowerCase());
        return lectures;
    }

    public List<Lecture> getLectureFilter(String lang, Integer hallId, Integer year, Integer specialization, Integer day, Integer courseId, Integer classNumber, Integer groupNumber, Integer sectionNumber, Integer count) throws Exception {
        refreshLecturesTime();
        List<Lecture> lectures = repository.getLectureFilter(hallId, year, specialization, day, courseId, classNumber, groupNumber, sectionNumber);
        List<Lecture> result = new ArrayList<>();
        if (count == null)
            count = 10;
        if (count > lectures.size())
            count = lectures.size();
        for (int i = 0; i < count; i++) {
            Lecture lecture = lectures.get(i);
            lecture.setLecturerName(lang);
            lecture.getCourse().setNameByLang(lang);
            lecture.setLocationName(locationService.getById(lecture.getActualLocationId()).getName(lang));
            result.add(lecture);
        }
        return result;
    }


}
