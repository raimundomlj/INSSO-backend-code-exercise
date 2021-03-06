package com.raimundomlj.dto.responses;

import java.util.List;

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
public class ClientCaseResponse {

	private Integer idClientCase;
	private String clientName;
	private String caseCreationDate;
	private String reference;
	private List<MessagesResponse> messagesList;
}

