package com.starter;

import javax.jms.ConnectionFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.util.ErrorHandler;

@EnableJms
@SpringBootApplication
public class TestejmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestejmsApplication.class, args);
	}

	// Only required due to defining myFactory in the receiver
	@Bean
	public JmsListenerContainerFactory<?> myFactory(
			ConnectionFactory connectionFactory, 
				DefaultJmsListenerContainerFactoryConfigurer configurer) {
		
		DefaultJmsListenerContainerFactory factory = 
				new DefaultJmsListenerContainerFactory();
		
		factory.setErrorHandler(
			new ErrorHandler() {
				@Override
				public void handleError(Throwable t) {
					System.err.println("An error has occurred in the transaction");				
				}
		});
		
		factory.setErrorHandler(t -> System.out.println(
				"An error has occurred in the transaction"));
		
		configurer.configure(factory, connectionFactory);
		
		return factory;
	}

	// Serialize message content to json using TextMessage
	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		
		MappingJackson2MessageConverter converter = 
				new MappingJackson2MessageConverter();
		
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		
		return converter;
	}

}
