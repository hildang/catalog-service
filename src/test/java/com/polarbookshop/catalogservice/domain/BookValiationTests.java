package com.polarbookshop.catalogservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BookValiationTests {
	private static Validator validator;
	
	@BeforeAll
	static void setUp() { 
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void whenAllFieldsCorrectThenValidationSucceeds() {
		var book = Book.build("1234567890", "Title", "Author", 9.90);
		Set<ConstraintViolation<Book>> violations = validator.validate(book);
		assertThat(violations).isEmpty();
	}
	
	@Test
	void whenIsbnDefinedButIncorrectThenValidationFails() {
		var book = Book.build("a234567890", "Title", "Author", 9.90);
		Set<ConstraintViolation<Book>> violations = validator.validate(book);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage())
			.isEqualTo("The ISBN format must follow the standards ISBN-10 or ISBN-13.");
	}
}
