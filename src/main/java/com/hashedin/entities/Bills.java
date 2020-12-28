package com.hashedin.entities;

import com.hashedin.converters.AmountPerUser;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "bill")
@Data
public class Bills extends BaseEntity {

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "upload_at", nullable = false, updatable = false)
    private LocalDateTime uploadAt = LocalDateTime.now();

    @Column(name = "paid_by", columnDefinition = "text", nullable = false)
    private String paidBy;

    @Column(name = "settled", columnDefinition = "BOOLEAN")
    private Boolean settled = Boolean.FALSE;

    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "amount_per_user", columnDefinition = "text")
    @Convert(converter = AmountPerUser.class)
    private Map<String,Double> amountPerHead;
}
