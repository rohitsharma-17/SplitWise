package com.hashedin.entities;

import com.hashedin.converters.BillListConverter;
import com.hashedin.converters.MemberListConverter;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.TreeSet;

@Entity
@Table(name = "user_group")
@Data
public class Groups extends BaseEntity {

    @Column(name = "group_name", columnDefinition = "text", nullable = false)
    private String groupName;

    @Column(name = "members", columnDefinition = "text")
    @Convert(converter = MemberListConverter.class)
    private List<Users> members;

}
