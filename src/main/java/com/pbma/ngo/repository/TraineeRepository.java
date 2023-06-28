package com.pbma.ngo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pbma.ngo.entity.Trainee;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Long> {

	public Trainee findByTraineeId (@Param("traineeId") final long traineeId);
	
}
