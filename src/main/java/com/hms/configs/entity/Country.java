package com.hms.configs.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "countries")
@Data
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countryId;

    @Column(name = "country_name", length = 100)
    private String countryName;

    @Column(name = "country_code", length = 10)
    private String countryCode;

}