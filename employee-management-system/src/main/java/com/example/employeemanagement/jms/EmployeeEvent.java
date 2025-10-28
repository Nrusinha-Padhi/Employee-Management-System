package com.example.employeemanagement.jms;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class EmployeeEvent implements Serializable {
	private Long employeeId;
	
	private String action; 
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
	private LocalDateTime timestamp;

	public EmployeeEvent() {
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}
