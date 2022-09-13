package com.project.miscroservices.limits.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.miscroservices.limits.configuration.LimitsConfiguration;
import com.project.miscroservices.limits.model.Limits;

@RestController
@RequestMapping("api/limits/v1")
public class LimitsController {
	
	@Autowired
	private LimitsConfiguration configuration;

	@GetMapping("/limits")
	public Limits getLimits() {
//		return new Limits(1, 1000);
		return new Limits(configuration.getMinimum(), configuration.getMaximum());
	}
	 
}
