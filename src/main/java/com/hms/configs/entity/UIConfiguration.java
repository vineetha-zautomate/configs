package com.hms.configs.entity;

import com.hms.configs.util.JsonConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Map;


@Entity
@Table(name = "UI_configurations")
@Data
public class UIConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "domain_name", nullable = false)
    private String domainName;

    @Convert(converter = JsonConverter.class)
    private List<Object> configurationJson;

}

