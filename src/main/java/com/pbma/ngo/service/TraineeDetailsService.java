package com.pbma.ngo.service;

import org.springframework.http.ResponseEntity;

public interface TraineeDetailsService {

	public ResponseEntity<String> saveTraineeDetails(final String traineeDetails) throws Exception;
	public ResponseEntity<String> getTraineeDetails(final long traineeId) throws Exception;
	public ResponseEntity<String> getAllTraineeDetails() throws Exception;
	public ResponseEntity<String> updateTraineeDetails(final long traineeId, final String traineeDetails) throws Exception;

}
