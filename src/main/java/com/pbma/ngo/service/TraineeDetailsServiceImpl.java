package com.pbma.ngo.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pbma.ngo.config.TraineeDetailsConfig;
import com.pbma.ngo.entity.Trainee;
import com.pbma.ngo.repository.TraineeRepository;
import com.pbma.ngo.util.Constants;
import com.pbma.ngo.util.TraineeDetailsUtils;

@Service
public class TraineeDetailsServiceImpl implements TraineeDetailsService {

	@Autowired
	private TraineeDetailsConfig traineeDetailsConfig;

	@Autowired
	private TraineeRepository traineeRepository;

	private static final Logger traineeDetailsLogger = LoggerFactory.getLogger(TraineeDetailsServiceImpl.class);

	@Override
	public ResponseEntity<String> saveTraineeDetails(String traineeDetailsRequest) throws Exception {

		// jolt for request json - flatten to map to entity
		String transformedTraineeRequest = TraineeDetailsUtils.transformRequest(traineeDetailsRequest,
				traineeDetailsConfig.getTraineeDetailsPostRequestJoltSpec());
		traineeDetailsLogger.debug("Save Trainee Details transformed request : {}", transformedTraineeRequest);

		ObjectMapper objectMapper = new ObjectMapper();
		Trainee requestTraineeObject = objectMapper.readValue(transformedTraineeRequest, Trainee.class);
		Trainee trainee = traineeRepository.save(requestTraineeObject);
		traineeDetailsLogger.info("Trainee Details inserted in database successfully");

		// response jolt for traineeId
		String traineeResponse = new JSONObject(trainee).toString(Constants.JSON_OBJECT_INDENTATION_FACTOR);

		String response = new JSONObject(TraineeDetailsUtils.transformRequest(traineeResponse,
				traineeDetailsConfig.getTraineeDetailsPostResponseJoltSpec()))
				.toString(Constants.JSON_OBJECT_INDENTATION_FACTOR);
		traineeDetailsLogger.debug("Save Trainee Details transformed response : {}", response);

		return new ResponseEntity<String>(response, HttpStatus.CREATED);

	}

	@Override
	public ResponseEntity<String> getTraineeDetails(long traineeId) throws Exception {

		Trainee trainee = traineeRepository.findByTraineeId(traineeId);
		traineeDetailsLogger.info("Trainee Details retrieved from database successfully");

		String traineeResponse = new JSONObject(trainee).toString(Constants.JSON_OBJECT_INDENTATION_FACTOR);

		String response = new JSONObject(TraineeDetailsUtils.transformRequest(traineeResponse,
				traineeDetailsConfig.getTraineeDetailsGetResponseJoltSpec()))
				.toString(Constants.JSON_OBJECT_INDENTATION_FACTOR);
		traineeDetailsLogger.debug("Get Trainee Details transformed response : {}", response);

		return new ResponseEntity<String>(response, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<String> getAllTraineeDetails() throws Exception {

		List<Trainee> trainees = traineeRepository.findAll();
		traineeDetailsLogger.info("All Trainee Details retrieved from database successfully");

		JSONArray transformedTrainees = new JSONArray();

		trainees.forEach(trainee -> {

			JSONObject responseTrainee = new JSONObject(TraineeDetailsUtils.transformRequest(
					new JSONObject(trainee).toString(Constants.JSON_OBJECT_INDENTATION_FACTOR),
					traineeDetailsConfig.getTraineeDetailsGetResponseJoltSpec()));
			transformedTrainees.put(responseTrainee);

		});

		String response = transformedTrainees.toString(Constants.JSON_OBJECT_INDENTATION_FACTOR);
		traineeDetailsLogger.debug("Get All Trainee Details transformed response : {}", response);

		return new ResponseEntity<String>(response, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<String> updateTraineeDetails(final long traineeId, String traineeDetailsRequest)
			throws Exception {

		// add trainee id received as uri param in request body
		JSONObject traineeDetailsRequestJsonObject = new JSONObject(traineeDetailsRequest);
		traineeDetailsRequestJsonObject.getJSONObject(Constants.TRAINEE).put(Constants.TRAINEE_ID, traineeId);

		// create update request and save details
		String transformedTraineeRequest = TraineeDetailsUtils.transformRequest(
				traineeDetailsRequestJsonObject.toString(Constants.JSON_OBJECT_INDENTATION_FACTOR),
				traineeDetailsConfig.getTraineeDetailsPutRequestJoltSpec());
		traineeDetailsLogger.debug("Update Trainee Details transformed request : {}", transformedTraineeRequest);

		ObjectMapper objectMapper = new ObjectMapper();
		Trainee requestTraineeObject = objectMapper.readValue(transformedTraineeRequest, Trainee.class);
		Trainee trainee = traineeRepository.save(requestTraineeObject);
		traineeDetailsLogger.info("Trainee Details updated in database successfully");

		// retrieve updated details from database
		String response = this.getTraineeDetails(traineeId).getBody();
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

}
