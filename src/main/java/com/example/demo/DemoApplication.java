package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;

import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Bean
	public OtlpGrpcSpanExporter otlpHttpSpanExporter(@Value("${otel.exporter.otlp.endpoint}") String url) {
		return OtlpGrpcSpanExporter.builder().setEndpoint(url).build();
	}

}
