package com.sqs.sqsexample;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;

@SpringBootApplication
@EnableSqs
@EnableEncryptableProperties
public class SqsExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SqsExampleApplication.class, args);
	}

}
