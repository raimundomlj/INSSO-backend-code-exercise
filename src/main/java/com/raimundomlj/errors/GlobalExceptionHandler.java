package com.raimundomlj.errors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.log4j.Log4j2;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(value = { InssoBackendCodeExerciseException.class })
	public final ResponseEntity<InssoBackendCodeExerciseError> handleInssoBackendCodeExerciseException(InssoBackendCodeExerciseException ex) {
		log.error("handle InssoBackendCodeExerciseException {} - {}", ex.getError().getErroNumber(), ex.getError().getMessage());
		return new ResponseEntity<InssoBackendCodeExerciseError>(ex.getError(), ex.getHttpStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("handle validation errors - begin");
		
		StringBuilder causes = new StringBuilder();
		causes.append("the following fields are not valid: ");
		
		ex.getBindingResult().getAllErrors().forEach(error -> {
			causes.append(error.getDefaultMessage());
			causes.append("; ");
		});
		InssoBackendCodeExerciseError apiError = InssoBackendCodeExerciseError.VALIDATION_ERROR;
		apiError.setMessage(causes.toString().substring(0, causes.length()-2));
		log.error("error number {} - {}", apiError.getErroNumber(), apiError.getMessage());
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
	}
}
