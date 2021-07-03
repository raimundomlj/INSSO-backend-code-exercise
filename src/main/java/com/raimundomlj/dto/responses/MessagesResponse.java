package com.raimundomlj.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.raimundomlj.enums.Channel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(Include.NON_EMPTY)
public class MessagesResponse{
	private Integer idMessage;
	private String creationDate;
	private String authorName;
	private String messageContent;
	private Channel channel;
}
