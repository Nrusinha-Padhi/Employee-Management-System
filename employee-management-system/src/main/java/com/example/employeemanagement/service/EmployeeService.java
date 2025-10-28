package com.example.employeemanagement.service;

import java.util.List;
import javax.xml.transform.stream.StreamSource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.entity.EmployeeXml;
import com.example.employeemanagement.jms.EmployeeEvent;
import com.example.employeemanagement.jms.EmployeeEventPublisher;
import com.example.employeemanagement.repository.EmployeeRepository;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final Jaxb2Marshaller marshaller;
	private final EmployeeEventPublisher eventPublisher;

	public EmployeeService(EmployeeRepository employeeRepository, Jaxb2Marshaller marshaller,
			EmployeeEventPublisher eventPublisher) {
		this.employeeRepository = employeeRepository;
		this.marshaller = marshaller;
		this.eventPublisher = eventPublisher;
	}

//   1️ CREATE EMPLOYEE (POST /employees)
	public Employee createEmployeeFromXml(String xmlContent) throws Exception {

		EmployeeXml employeeXml = (EmployeeXml) marshaller.unmarshal(new StreamSource(new StringReader(xmlContent)));

		// Convert to JPA entity
		Employee employee = mapToEntity(employeeXml);

		// Save to database
		Employee saved = employeeRepository.save(employee);

		EmployeeEvent event = new EmployeeEvent();
		event.setEmployeeId(saved.getId());
		event.setAction("CREATE");
		event.setTimestamp(LocalDateTime.now());
		eventPublisher.publishEvent(event);
		return saved;

	}

// 2️ UPDATE EMPLOYEE (PUT /employees/{id})
	public Employee updateEmployeeFromXml(Long id, String xmlContent) throws Exception {
		Employee existing = employeeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
		
		EmployeeXml employeeXml = (EmployeeXml) marshaller.unmarshal(new StreamSource(new StringReader(xmlContent)));

		// Update existing entity fields
		existing.setName(employeeXml.getName());
		existing.setEmail(employeeXml.getEmail());
		existing.setDepartment(employeeXml.getDepartment());
		existing.setDateOfJoining(existing.getDateOfJoining());
		existing.setUpdatedAt(java.time.LocalDateTime.now());

		// Save updated record
		Employee updated = employeeRepository.save(existing);

		EmployeeEvent event = new EmployeeEvent();
		event.setEmployeeId(updated.getId());
		event.setAction("UPDATE");
		event.setTimestamp(LocalDateTime.now());
		eventPublisher.publishEvent(event);
		return updated;
	}

// 3️ GET EMPLOYEE BY ID 
	public Employee getEmployeeById(Long id) {
		return employeeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
	}

//  DELETE EMPLOYEE 
	public void deleteEmployee(Long id) {
		Employee existing = employeeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
		
		employeeRepository.delete(existing);
		EmployeeEvent event = new EmployeeEvent();
		event.setEmployeeId(existing.getId());
		event.setAction("DELETE");
		event.setTimestamp(LocalDateTime.now());
		eventPublisher.publishEvent(event);
	}

//  LIST ALL EMPLOYEES 
	public List<Employee> listAllEmployees() {
		return employeeRepository.findAll();
	}

	// Convert XML model to database entity
	private Employee mapToEntity(EmployeeXml xml) {
		Employee employee = new Employee(null, xml.getName(), xml.getEmail(), xml.getDepartment(), LocalDate.now(),
				null, null, null);
		return employee;
	}

}
