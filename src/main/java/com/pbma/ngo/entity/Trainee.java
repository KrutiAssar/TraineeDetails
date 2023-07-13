package com.pbma.ngo.entity;

import java.sql.Date;
import java.sql.Timestamp;

//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pbma.ngo.util.Constants;

import lombok.Data;

@Data
@Entity
@Table(name = "trainee", schema = "student")
public class Trainee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "trainee_id")
	private Long traineeId;

	@Column(name = "salutation")
	private String salutation;

	@Column(name = "name_of_trainee")
	private String nameOfTrainee;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_PATTERN)
	@Column(name = "date_of_birth")
	private Date dateOfBirth;

	@Column(name = "gender")
	private String gender;

	@Column(name = "marital_status")
	private String maritalStatus;

	@Column(name = "caste_category")
	private String casteCategory;

	@Column(name = "highest_education_level")
	private String highestEducationLevel;

	@Column(name = "currently_pursuing_education")
	private String currentlyPursuingEducation;

	@Column(name = "name_of_educational_institute")
	private String nameOfEducationalInstitute;

	@Column(name = "technical_education")
	private String technicalEducation;

	@Column(name = "guardian_type")
	private String guardianType;

	@Column(name = "name_of_guardian")
	private String nameOfGuardian;

	@Column(name = "number_of_family_members")
	private String numberOfFamilyMembers;

	@Column(name = "family_economic_status")
	private String familyEconomicStatus;

	@Column(name = "source_of_household_income")
	private String sourceOfHouseholdIncome;

	@Column(name = "trainee_annual_income")
	private String traineeAnnualIncome;

	@Column(name = "annual_household_income")
	private String annualHouseholdIncome;

	@Column(name = "type_of_identification")
	private String typeOfIdentification;

	@Column(name = "aadhar_number")
	private String aadharNumber;

	@Column(name = "pan_number")
	private String panNumber;

	@Column(name = "voter_id_number")
	private String voterIdNumber;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "alternate_contact_number")
	private String alternateContactNumber;

	@Column(name = "guardian_contact_number")
	private String guardianContactNumber;

	@Column(name = "email_id")
	private String emailId;

	@Column(name = "trainee_address")
	private String traineeAddress;

	@Column(name = "district")
	private String district;

	@Column(name = "state")
	private String state;

	@Column(name = "pincode")
	private String pincode;

	@Column(name = "type_of_disability")
	private String typeOfDisability;

	@Column(name = "pwd_certificate")
	private String pwdCertificate;

	@Column(name = "mobilisation_technique")
	private String mobilisationTechnique;

	@Column(name = "pre_joining_counselling")
	private String preJoiningCounselling;

	@Column(name = "pre_training_employment_status")
	private String preTrainingEmploymentStatus;

	@Column(name = "current_employment_status")
	private String currentEmploymentStatus;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.TIMESTAMP_PATTERN, timezone = Constants.TIMEZONE_ASIA)
	@Column(name = "creation_timestamp")
	private Timestamp creationTimestamp;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.TIMESTAMP_PATTERN, timezone = Constants.TIMEZONE_ASIA)
	@Column(name = "last_update_timestamp")
	private Timestamp lastUpdateTimestamp;

}
