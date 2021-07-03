package com.raimundomlj.dto.requests;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.raimundomlj.annotation.ValueOfEnum;
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
public class CreateMessageRequest {
	@NotBlank(message = "Please, inform author name")
	private String authorName;
	@NotBlank(message = "Please, inform message content")
	private String messageContent;
	@ValueOfEnum(enumClass = Channel.class, message = "Make sure you are using one of valid channel: MAIL, SMS, FACEBOOK, TWITTER")
	private String channel;
}
