package com.hashedin.entities;

import com.hashedin.converters.GroupListConverter;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class Users extends BaseEntity {

    @Column(name = "name", columnDefinition = "text")
    private String name;

    @Column(name = "email", columnDefinition = "text", unique = true, nullable = false)
    private String email;

    @Column(name = "group_list", columnDefinition = "text")
    @Convert(converter = GroupListConverter.class)
    private List<Groups> groupsList;
}
