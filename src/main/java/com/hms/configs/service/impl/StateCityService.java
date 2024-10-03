package com.hms.configs.service.impl;

import com.hms.configs.entity.City;
import com.hms.configs.entity.State;
import com.hms.configs.repo.CityRepository;
import com.hms.configs.repo.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateCityService {

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CityRepository cityRepository;

    public List<City> getCitiesByStateId(Long stateId) {
        return cityRepository.findByStateId(stateId);
    }

    public List<State> getStatesByCountryId(Long countryId) {
        return stateRepository.findByCountryId(countryId);
    }

    public List<State> getAllStates() {
        return stateRepository.findAll();
    }

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }
}
