package com.fiteprojects.fitegis.Repositories;

import com.fiteprojects.fitegis.Models.GenericModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface GenericRepository<Model extends GenericModel> extends PagingAndSortingRepository<Model, Integer> {
}
