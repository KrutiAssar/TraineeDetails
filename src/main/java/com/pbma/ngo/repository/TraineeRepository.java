package com.pbma.ngo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pbma.ngo.entity.Trainee;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Long> {

	public Trainee findByTraineeId (@Param("traineeId") final long traineeId);
	public Trainee findByAadharNumber (@Param("aadharNumber") final String aadharNumber);
	public Trainee findByPanNumber (@Param("panNumber") final String panNumber);
	public Trainee findByVoterIdNumber (@Param("voterIdNumber") final String voterIdNumber);
	public Trainee findByMobileNumber (@Param("mobileNumber") final String mobileNumber);
	
}
