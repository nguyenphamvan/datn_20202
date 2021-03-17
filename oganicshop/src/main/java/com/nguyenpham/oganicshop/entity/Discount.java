package com.nguyenpham.oganicshop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Builder
@Entity
@Table(name = "discount")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String code;
    private double discountPercent;
    private int discountPrice;
    private int condition;
    private Date startDate;
    private Date endDate;
    private int numberOfUses;
}
