package com.example.employeemanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.example.employeemanagement.entity.EmployeeXml;

@Configuration
public class XmlConfig {

	@Bean
	public Jaxb2Marshaller marshaller() throws Exception {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(EmployeeXml.class); 
		marshaller.setSchema(new ClassPathResource("employee.xsd"));
		return marshaller;

	}

}
