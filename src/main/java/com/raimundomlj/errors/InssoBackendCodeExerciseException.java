package com.raimundomlj.errors;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class InssoBackendCodeExerciseException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private InssoBackendCodeExerciseError error;
	private HttpStatus httpStatus;
}
