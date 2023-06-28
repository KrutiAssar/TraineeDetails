package com.pbma.ngo.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bazaarvoice.jolt.JsonUtils;

@Configuration
public class TraineeDetailsConfig {

	@Autowired
	private ApplicationYaml applicationYaml;
	
	@Bean
	public List<Object> getTraineeDetailsRequestJoltSpec() {
		return JsonUtils.classpathToList(applicationYaml.getJolt().getTraineeDetailsRequestJoltSpec());
	}
	
	@Bean
	public List<Object> getTraineeDetailsGetResponseJoltSpec() {
		return JsonUtils.classpathToList(applicationYaml.getJolt().getTraineeDetailsGetResponseJoltSpec());
	}
	
	@Bean
	public List<Object> getTraineeDetailsPostResponseJoltSpec() {
		return JsonUtils.classpathToList(applicationYaml.getJolt().getTraineeDetailsPostResponseJoltSpec());
	}
	
}
