package com.hms.configs.service.impl;

import com.hms.configs.entity.UIConfiguration;
import com.hms.configs.repo.UIConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UIConfigurationService {

    @Autowired
    private UIConfigurationRepository uiConfigurationRepository;

    public Optional<UIConfiguration> getConfigurationByDomainName(String domainName) {
        return uiConfigurationRepository.findByDomainName(domainName);
    }

    public List<UIConfiguration> getAllConfiguration() {
        return uiConfigurationRepository.findAll();
    }
}

