package com.hms.configs.controller;

import com.hms.configs.entity.City;
import com.hms.configs.entity.State;
import com.hms.configs.entity.UIConfiguration;
import com.hms.configs.service.impl.StateCityService;
import com.hms.configs.service.impl.UIConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/configuration")
public class UIConfigurationController {

    @Autowired
    private UIConfigurationService uiConfigurationService;

    @Autowired
    private StateCityService stateCityService;

    @GetMapping()
    public ResponseEntity<UIConfiguration> getConfigurationByDomainName(@RequestParam String domain_name) {
         Optional<UIConfiguration> configuration = uiConfigurationService.getConfigurationByDomainName(domain_name);
        return ResponseEntity.ok(configuration.get());
    }

    @GetMapping("/all")
    public ResponseEntity<List<UIConfiguration>> getConfigurationByDomainName() {
        return  ResponseEntity.ok(uiConfigurationService.getAllConfiguration());
    }

    //all states api - not auth

    @GetMapping("/states/all")
    public List<City> getAllStates() {
        return stateCityService.getAllCities();
    }

    @GetMapping("/cities/all")
    public List<City> getAllCities() {
        return stateCityService.getAllCities();
    }

    @GetMapping("/countries/all")
    public List<City> getAllCountries() {
        return stateCityService.getAllCities();
    }

}
