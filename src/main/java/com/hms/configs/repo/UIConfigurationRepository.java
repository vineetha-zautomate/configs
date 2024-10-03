package com.hms.configs.repo;

import com.hms.configs.entity.UIConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UIConfigurationRepository extends JpaRepository<UIConfiguration, Long> {
    Optional<UIConfiguration> findByDomainName(String domainName);
}

