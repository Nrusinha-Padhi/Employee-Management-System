package com.example.employeemanagement.jms;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmployeeEventPublisher {

	private final JmsTemplate jmsTemplate;

	public EmployeeEventPublisher(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void publishEvent(EmployeeEvent event) {
		jmsTemplate.convertAndSend("employee.events", event);
	}
}
