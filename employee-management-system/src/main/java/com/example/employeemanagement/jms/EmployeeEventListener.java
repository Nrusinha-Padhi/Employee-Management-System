package com.example.employeemanagement.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.example.employeemanagement.entity.Audit_logs;
import com.example.employeemanagement.repository.AuditLogRepository;

@Component
public class EmployeeEventListener {
	private final AuditLogRepository auditLogRepository;

	public EmployeeEventListener(AuditLogRepository auditLogRepository) {
		this.auditLogRepository = auditLogRepository;
	}

	@JmsListener(destination = "employee.events")
	public void onEmployeeEvent(EmployeeEvent event, @Header(JmsHeaders.MESSAGE_ID) String messageId) {
		Audit_logs logEntry = new Audit_logs();
		logEntry.setEmployeeId(event.getEmployeeId());
		logEntry.setAction(event.getAction());
		logEntry.setTimestamp(event.getTimestamp());
		logEntry.setSource("JMS");
		logEntry.setMessageId(messageId);

		auditLogRepository.save(logEntry);
		System.out.println("[AUDIT] JMS event logged: " + logEntry);
	}

}
