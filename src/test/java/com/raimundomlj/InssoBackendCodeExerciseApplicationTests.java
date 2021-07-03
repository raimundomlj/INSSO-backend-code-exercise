package com.raimundomlj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.raimundomlj.dto.requests.AssociateMessageToClientCaseRequest;
import com.raimundomlj.dto.requests.CreateClientCaseRequest;
import com.raimundomlj.dto.requests.CreateMessageRequest;
import com.raimundomlj.dto.requests.ModifyClientCaseRequest;
import com.raimundomlj.dto.responses.ClientCaseResponse;
import com.raimundomlj.dto.responses.CreateClientCaseResponse;
import com.raimundomlj.dto.responses.CreateMessageResponse;
import com.raimundomlj.dto.responses.MessagesResponse;
import com.raimundomlj.enums.Channel;
import com.raimundomlj.services.ClientCaseService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class InssoBackendCodeExerciseApplicationTests {
	
	@Autowired
	private ClientCaseService clientCaseService;
	
	private static final String  authorName = " author unit test name";
	private static final Channel channel = Channel.MAIL;
	private static final String messageContent = "this is a junit message content test";
	private static final String clientName = "unit test client name";
	private static final String reference = "RMLJ-2021";
	private static Integer clientCaseId;
	private static Integer messageId;
	private static String creationDate;
	private static String caseCreationDate;

	
	@Test
	@Order(1)
	void createMessageTest() {
		CreateMessageResponse expected = createMessageResponse();
		CreateMessageResponse output = clientCaseService.createMessage(createMessageRequestTest());
		assertEquals(expected.getAuthorName(), output.getAuthorName());
		assertEquals(expected.getChannel(), output.getChannel());
		assertEquals(expected.getMessageContent(), output.getMessageContent());
		assertNotEquals(null, output.getIdMessage());
		assertNotEquals(null, output.getCreationDate());
		messageId = output.getIdMessage();
		creationDate = output.getCreationDate();
	}
	
	@Test
	@Order(2)
	void createClientCaseTest() {
		CreateClientCaseResponse expected = createClientCaseResponse();
		CreateClientCaseResponse output = clientCaseService.createClientCase(createClientCaseRequest());
		assertEquals(expected.getClientName(), output.getClientName());
		assertNotEquals(null, output.getIdClientCase());
		assertNotEquals(null, output.getCaseCreationDate());
		clientCaseId = output.getIdClientCase();
		caseCreationDate = output.getCaseCreationDate();
	}
	
	@Test
	@Order(3)
	void associateMessageToClientCaseTest() {
		ClientCaseResponse expected = clientCaseResponse();
		ClientCaseResponse output = clientCaseService.associateMessageToClientCase(associateMessageToClientCaseRequest()); 
		assertEquals(expected, output);
	}
	
	@Test
	@Order(4)
	void modifyClientCaseTest() {
		ClientCaseResponse expected = clientCaseResponse2();
		ClientCaseResponse output = clientCaseService.modifyClientCase(modifyClientCaseRequest());
		assertEquals(expected, output);
	}
	
	@Test
	@Order(5)
	void getAllClientCases() {
		List<ClientCaseResponse> expected = Collections.singletonList(clientCaseResponse2());
		List<ClientCaseResponse> output = clientCaseService.getAllClientCases();
		assertEquals(expected, output);
	}
	
	

	private CreateMessageRequest createMessageRequestTest() {
		return CreateMessageRequest.builder()
				.authorName(authorName)
				.channel(channel.toString())
				.messageContent(messageContent)
				.build();
	}
	
	private CreateMessageResponse createMessageResponse() {
		return CreateMessageResponse.builder()
				.authorName(authorName)
				.channel(channel)
				.messageContent(messageContent)
				.build();
	}
	
	private CreateClientCaseRequest createClientCaseRequest() {
		return CreateClientCaseRequest.builder()
				.clientName(clientName)
				.build();
	}
	
	private CreateClientCaseResponse createClientCaseResponse() {
		return CreateClientCaseResponse.builder()
				.clientName(clientName)
				.build();
	}
	
	private AssociateMessageToClientCaseRequest associateMessageToClientCaseRequest() {
		return AssociateMessageToClientCaseRequest.builder()
				.clientCaseId(clientCaseId)
				.messageId(Collections.singletonList(messageId))
				.build();
	}
	
	private ClientCaseResponse clientCaseResponse() {
		return ClientCaseResponse.builder()
				.idClientCase(clientCaseId)
				.clientName(clientName)
				.caseCreationDate(caseCreationDate)
				.messagesList(Collections.singletonList(MessagesResponse.builder()
						.authorName(authorName)
						.channel(channel)
						.messageContent(messageContent)
						.idMessage(messageId)
						.creationDate(creationDate)
						.build()))
				.build();
	}
	
	private ClientCaseResponse clientCaseResponse2() {
		return ClientCaseResponse.builder()
				.idClientCase(clientCaseId)
				.clientName(clientName)
				.reference(reference)
				.caseCreationDate(caseCreationDate)
				.messagesList(Collections.singletonList(MessagesResponse.builder()
						.authorName(authorName)
						.channel(channel)
						.messageContent(messageContent)
						.idMessage(messageId)
						.creationDate(creationDate)
						.build()))
				.build();
	}
	
	private ModifyClientCaseRequest modifyClientCaseRequest() {
		return ModifyClientCaseRequest.builder()
				.clientCaseId(clientCaseId)
				.reference(reference)
				.build();
	}
	
//
//	void getAllClientCases() {
//		log.info("fetching all client cases");
//		List<ClientCaseResponse> output = clientCaseService.getAllClientCases();
//		log.info("retrieved {} client cases", output.size());
//		return ResponseEntity.ok(output);
//	}

}
