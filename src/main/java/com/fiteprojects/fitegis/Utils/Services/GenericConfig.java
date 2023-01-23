package com.fiteprojects.fitegis.Utils.Services;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class GenericConfig<Model> {

    public Model update(Model OLD, Model NEW) {
        Class<?> NEWCLASS = NEW.getClass();
        Class<?> OLDCLASS = OLD.getClass();

        try {
            for (Field newField : NEWCLASS.getDeclaredFields()) {
                newField.setAccessible(true);
                Model fieldValue = (Model) newField.get(NEW);
                Field oldField = OLDCLASS.getDeclaredField(newField.getName());
                oldField.setAccessible(true);
                if (fieldValue == null) continue;
                oldField.set(OLD, fieldValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OLD;
    }
}
