package com.raimundomlj.errors;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_EMPTY)
public class InssoBackendCodeExerciseError {

	private Integer erroNumber;
	private String code;
	private String message;
	
	public static InssoBackendCodeExerciseError buildSpecificError(Integer erroNumber, String code, String message) {
		return InssoBackendCodeExerciseError.builder().erroNumber(erroNumber).code(code).message(message).build();
	}
	
	public static final InssoBackendCodeExerciseError VALIDATION_ERROR = buildSpecificError(1, "VALIDATION_ERROR", StringUtils.EMPTY);
	public static final InssoBackendCodeExerciseError NOT_SAVED = buildSpecificError(2, "NOT_SAVED", "Was not possible save");
	public static final InssoBackendCodeExerciseError EMPTY_LIST = buildSpecificError(3, "EMPTY_LIST", "Empty List");
	public static final InssoBackendCodeExerciseError NOT_FOUND  = buildSpecificError(4, "NOT_FOUND", "NOT FOUND");
	public static final InssoBackendCodeExerciseError NOT_POSSIBLE  = buildSpecificError(5, "NOT_POSSIBLE", "NOT POSSIBLE MAKE OPERATION");
}
