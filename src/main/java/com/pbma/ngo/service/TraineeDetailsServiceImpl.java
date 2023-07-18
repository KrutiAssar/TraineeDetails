package com.pbma.ngo.service;

import java.sql.Timestamp;
import java.util.Calendar;
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

		Calendar calendar = Calendar.getInstance();
		calendar.clear(Calendar.ZONE_OFFSET);

		Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
		requestTraineeObject.setCreationTimestamp(timestamp);
		requestTraineeObject.setLastUpdateTimestamp(timestamp);

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

		Calendar calendar = Calendar.getInstance();
		calendar.clear(Calendar.ZONE_OFFSET);
		Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
		requestTraineeObject.setLastUpdateTimestamp(timestamp);

		traineeRepository.save(requestTraineeObject);
		traineeDetailsLogger.info("Trainee Details updated in database successfully");

		// retrieve updated details from database
		String response = this.getTraineeDetails(traineeId).getBody();
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> getTraineeDetailsByIdentification(String typeOfIdentification,
			String identificationDocumentNumber) throws Exception {

		Trainee trainee = null;
		String traineeByIdentificationResponse = null;

		switch (typeOfIdentification) {
		case Constants.AADHAR_NUMBER:
			trainee = traineeRepository.findByAadharNumber(identificationDocumentNumber);
			break;
		case Constants.PAN_NUMBER:
			trainee = traineeRepository.findByPanNumber(identificationDocumentNumber);
			break;
		case Constants.VOTER_ID_NUMBER:
			trainee = traineeRepository.findByVoterIdNumber(identificationDocumentNumber);
			break;
		case Constants.MOBILE_NUMBER:
			trainee = traineeRepository.findByMobileNumber(identificationDocumentNumber);
			break;
		default:
			break;

		}

		if (trainee == null) {
			traineeDetailsLogger.info(
					"Trainee Details not found in database for type of identification : {} and identification document number : {}",
					typeOfIdentification, identificationDocumentNumber);
			JSONObject responseJsonObject = new JSONObject();
			responseJsonObject.put(Constants.RESPONSE_CODE, Constants.RESPONSE_CODE_NOT_FOUND);
			traineeByIdentificationResponse = responseJsonObject.toString(Constants.JSON_OBJECT_INDENTATION_FACTOR);

		} else {

			traineeDetailsLogger.info("Trainee Details retrieved from database successfully");

			String traineeResponse = new JSONObject(trainee).toString(Constants.JSON_OBJECT_INDENTATION_FACTOR);

			String response = new JSONObject(TraineeDetailsUtils.transformRequest(traineeResponse,
					traineeDetailsConfig.getTraineeDetailsGetResponseJoltSpec()))
					.toString(Constants.JSON_OBJECT_INDENTATION_FACTOR);
			traineeDetailsLogger.debug("Get Trainee Details transformed response : {}", response);

			JSONObject responseJsonObject = new JSONObject();
			responseJsonObject.put(Constants.RESPONSE_CODE, Constants.RESPONSE_CODE_RECORD_FOUND);
			responseJsonObject.put(Constants.TRAINEE, new JSONObject(response).getJSONObject(Constants.TRAINEE));

			traineeByIdentificationResponse = responseJsonObject.toString(Constants.JSON_OBJECT_INDENTATION_FACTOR);
		}
		
		traineeDetailsLogger.debug("Get Trainee Details by Identification final response : {}",
				traineeByIdentificationResponse);
		return new ResponseEntity<String>(traineeByIdentificationResponse, HttpStatus.OK);

	}
}
