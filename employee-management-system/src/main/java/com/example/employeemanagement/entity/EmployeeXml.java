package com.example.employeemanagement.entity;

import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement(name = "Employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeXml {
	@NotBlank(message = "Name is required")
	@Size(min = 1, max = 100, message = "Name cannot exceed 100 characters")
	private String name;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	@Size(min = 1, max = 150, message = "Email cannot exceed 150 characters")
	private String email;

	@NotBlank(message = "Department is required")
	@Size(min = 1, max = 100, message = "Department cannot exceed 100 characters")
	private String department;

	@XmlTransient
	@NotNull(message = "Date of joining is required")
	@PastOrPresent(message = "Date of joining cannot be in the future")
	private LocalDate dateOfJoining;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public LocalDate getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(LocalDate dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public EmployeeXml(String name, String email, String department) {
		this.name = name;
		this.email = email;
		this.department = department;
	}

	public EmployeeXml() {
	};

}
