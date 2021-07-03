package com.raimundomlj.dto.requests;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(Include.NON_EMPTY)
public class ModifyClientCaseRequest {

	@NotNull(message = "Client id cannot be empty")
	@Min(value = 1, message = "Client id cannot be less than 1")
	@Max(value = Integer.MAX_VALUE, message = "Client id cannot be above than "+Integer.MAX_VALUE)
	private Integer clientCaseId;
	private String clientName;
	private String reference;	
}
