package com.pbma.ngo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pbma.ngo.service.TraineeDetailsService;

@RestController
public class TraineeDetailsController {

	@Autowired
	private TraineeDetailsService traineeDetailsService;

	private static final Logger traineeDetailsLogger = LoggerFactory.getLogger(TraineeDetailsController.class);

	@PostMapping(value = "/trainees", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addTraineeDetails(@RequestBody String traineeDetailsRequest) throws Exception {
		traineeDetailsLogger.info("Received request to Add Trainee Details");
		ResponseEntity<String> responseEntity = traineeDetailsService.saveTraineeDetails(traineeDetailsRequest);
		traineeDetailsLogger.info("Completed request to Add Trainee Details");
		return responseEntity;
	}

	@GetMapping(value = "/trainees/{traineeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> viewTraineeDetails(@PathVariable("traineeId") Long traineeId) throws Exception {
		traineeDetailsLogger.info("Received request to View Trainee Details");
		ResponseEntity<String> responseEntity = traineeDetailsService.getTraineeDetails(traineeId);
		traineeDetailsLogger.info("Completed request to View Trainee Details");
		return responseEntity;
	}

	@GetMapping(value = "/trainees", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> viewAllTraineeDetails() throws Exception {
		traineeDetailsLogger.info("Received request to View All Trainee Details");
		ResponseEntity<String> responseEntity = traineeDetailsService.getAllTraineeDetails();
		traineeDetailsLogger.info("Completed request to View All Trainee Details");
		return responseEntity;
	}

}
