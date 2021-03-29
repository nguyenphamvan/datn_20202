package com.nguyenpham.oganicshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

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
    private int minOrderValue;
    private int minDiscountValue;
    private Date startDate;
    private Date endDate;
    private int numberOfUses;
}
