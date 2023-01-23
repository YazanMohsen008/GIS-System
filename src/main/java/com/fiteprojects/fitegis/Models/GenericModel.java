package com.fiteprojects.fitegis.Models;

import javax.persistence.*;
import java.io.Serializable;


@MappedSuperclass
public class GenericModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
