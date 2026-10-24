package com.eric.common.petclinic.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class ApplicationVersionHealthIndicator implements HealthIndicator 
{

    @Value("${application.version}")
    private String applicationVersion;    

    @Override
    public Health health() 
    {
        return Health.up()
                .withDetail("applicationVersion", applicationVersion)
                .build();
    }
    
}