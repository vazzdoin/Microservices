package com.project.miscroservices.limits.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties("limits-service")
@Data
public class LimitsConfiguration {
	
	private int minimum;	
	private int maximum;
	
}
