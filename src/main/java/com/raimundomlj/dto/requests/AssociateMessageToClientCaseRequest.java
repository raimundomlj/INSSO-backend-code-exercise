package com.raimundomlj.dto.requests;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
public class AssociateMessageToClientCaseRequest {

	@NotNull(message = "Message id cannot be empty")
	@Size(min = 1, message = "messages id cannot be less than 1")
	private List<Integer> messageId;
	
	@NotNull(message = "Client id cannot be empty")
	@Min(value = 1, message = "Client id cannot be less than 1")
	@Max(value = Integer.MAX_VALUE, message = "Client id cannot be above than "+Integer.MAX_VALUE)
	private Integer clientCaseId;
}
