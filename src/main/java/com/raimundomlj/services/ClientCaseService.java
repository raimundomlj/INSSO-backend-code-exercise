package com.raimundomlj.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.raimundomlj.dto.requests.AssociateMessageToClientCaseRequest;
import com.raimundomlj.dto.requests.CreateClientCaseRequest;
import com.raimundomlj.dto.requests.CreateMessageRequest;
import com.raimundomlj.dto.requests.ModifyClientCaseRequest;
import com.raimundomlj.dto.responses.ClientCaseResponse;
import com.raimundomlj.dto.responses.CreateClientCaseResponse;
import com.raimundomlj.dto.responses.CreateMessageResponse;
import com.raimundomlj.dto.responses.MessagesResponse;
import com.raimundomlj.entities.ClientCase;
import com.raimundomlj.entities.Message;
import com.raimundomlj.enums.Channel;
import com.raimundomlj.errors.InssoBackendCodeExerciseError;
import com.raimundomlj.errors.InssoBackendCodeExerciseException;
import com.raimundomlj.repositories.ClientCaseRepository;
import com.raimundomlj.repositories.MessageRepository;

@Service
@Transactional
public class ClientCaseService {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

	@Autowired
	private ClientCaseRepository clientCaseRepository;

	@Autowired
	private MessageRepository messageRepository;
	
	
	public CreateMessageResponse createMessage(CreateMessageRequest request) {
		Message m = Message.builder()
				.authorName(request.getAuthorName())
				.messageContent(request.getMessageContent())
				.channel(Channel.valueOf(request.getChannel().toUpperCase()))
				.creationDate(LocalDateTime.now().format(formatter).toString())
				.build();

		Message saved = messageRepository.save(m);
		if(saved !=null) {
			return CreateMessageResponse.builder()
					.authorName(saved.getAuthorName())
					.messageContent(saved.getMessageContent())
					.channel(saved.getChannel())
					.creationDate(saved.getCreationDate())
					.idMessage(saved.getIdMessage())
					.build();
		}
		else
			throw new InssoBackendCodeExerciseException(InssoBackendCodeExerciseError.NOT_SAVED, HttpStatus.EXPECTATION_FAILED);
	}

	public CreateClientCaseResponse createClientCase(CreateClientCaseRequest request) {
		ClientCase c = ClientCase.builder()
				.clientName(request.getClientName())
				.reference(request.getReference())
				.caseCreationDate(LocalDateTime.now().format(formatter).toString())
				.build();
		
		ClientCase saved = clientCaseRepository.saveAndFlush(c);
		if (saved != null) {
			return CreateClientCaseResponse.builder()
					.idClientCase(saved.getIdClientCase())
					.clientName(saved.getClientName())
					.reference(saved.getReference())
					.caseCreationDate(saved.getCaseCreationDate())
					.build();
		}else
			throw new InssoBackendCodeExerciseException(InssoBackendCodeExerciseError.NOT_SAVED,
					HttpStatus.EXPECTATION_FAILED);
	}
	
	
	public ClientCaseResponse associateMessageToClientCase(AssociateMessageToClientCaseRequest request) {
		Optional<ClientCase> cop = clientCaseRepository.findById(request.getClientCaseId());
		if(cop.isPresent()) {
			ClientCase clientCase = cop.get();
			List<Message> messages = mergeMessages(clientCase, request.getMessageId());
			messages.forEach(m->m.setClientCase(clientCase));
			clientCase.setMessagesList(messages);
			ClientCase saved = clientCaseRepository.saveAndFlush(clientCase);
			if (saved != null) {
				return buildClientCaseResponse(saved);
			}else
				throw new InssoBackendCodeExerciseException(InssoBackendCodeExerciseError.NOT_SAVED,
						HttpStatus.EXPECTATION_FAILED);
		}
		else
			throw new InssoBackendCodeExerciseException(InssoBackendCodeExerciseError.NOT_FOUND,
					HttpStatus.BAD_REQUEST);
	}
	
	public ClientCaseResponse modifyClientCase(ModifyClientCaseRequest request) {
		Optional<ClientCase> cop = clientCaseRepository.findById(request.getClientCaseId());
		if(cop.isPresent()) {
			ClientCase clientCase = cop.get();
			if(StringUtils.isNotBlank(request.getClientName()))
				clientCase.setClientName(request.getClientName());
			if(StringUtils.isNotBlank(request.getReference()))
				clientCase.setReference(request.getReference());
			ClientCase saved = clientCaseRepository.saveAndFlush(clientCase);
			if (saved != null) 
				return buildClientCaseResponse(saved);
			else
				throw new InssoBackendCodeExerciseException(InssoBackendCodeExerciseError.NOT_SAVED,
						HttpStatus.EXPECTATION_FAILED);
		}
		else
			throw new InssoBackendCodeExerciseException(InssoBackendCodeExerciseError.NOT_FOUND,
					HttpStatus.BAD_REQUEST);
	}
	
	public List<ClientCaseResponse> getAllClientCases(){
		List<ClientCase> clientCases = clientCaseRepository.findAll();
		
		if(clientCases != null && !clientCases.isEmpty()) {
			List<ClientCaseResponse> clientCasesResponse = new ArrayList<>();
			clientCases.forEach(c->
			clientCasesResponse.add(buildClientCaseResponse(c))
					);
			
			return clientCasesResponse;
		}
		else
			throw new InssoBackendCodeExerciseException(InssoBackendCodeExerciseError.EMPTY_LIST,
					HttpStatus.EXPECTATION_FAILED);
	}
	
	private ClientCaseResponse buildClientCaseResponse(ClientCase clientCase) {
		return ClientCaseResponse.builder()
				.idClientCase(clientCase.getIdClientCase())
				.clientName(clientCase.getClientName())
				.caseCreationDate(clientCase.getCaseCreationDate())
				.reference(clientCase.getReference())
				.messagesList(buildMessagesResponse(clientCase.getMessagesList()))
				.build();
	}
	
	
	private List<MessagesResponse> buildMessagesResponse(List<Message> messages) {
		List<MessagesResponse> messagesResponse = new ArrayList<>();
		messages.forEach(m->
			messagesResponse.add(MessagesResponse.builder()
					.idMessage(m.getIdMessage())
					.creationDate(m.getCreationDate())
					.authorName(m.getAuthorName())
					.messageContent(m.getMessageContent())
					.channel(m.getChannel())
					.build())
		);
		return messagesResponse;
	}
	
	private List<Message> findMessages(List<Integer> messagesId){
		List<Message> messages = new ArrayList<>();
		messagesId.forEach(m->{
			Optional<Message> opm = messageRepository.findById(m);
			if(opm.isPresent()) {
				messages.add(opm.get());
			}
		});
		return messages;
	}
	
	private List<Message> mergeMessages(ClientCase clientCase, List<Integer> messagesId){
		List<Message> messagesNotAssociated = findMessages(messagesId);
		List<Message> messagesOnDb = messageRepository.getMessageByClientCase(clientCase.getIdClientCase());
		
		if(messagesOnDb != null && !messagesOnDb.isEmpty() && messagesNotAssociated !=null && !messagesNotAssociated.isEmpty()) {
			messagesNotAssociated.forEach(mna->{
				if(!messagesOnDb.contains(mna))
					messagesOnDb.add(mna);
			});
			return messagesOnDb;
		}
		else if(messagesOnDb != null && !messagesOnDb.isEmpty())
			return messagesOnDb;
		else if(messagesNotAssociated !=null && !messagesNotAssociated.isEmpty())
			return messagesNotAssociated;
		else
			throw new InssoBackendCodeExerciseException(InssoBackendCodeExerciseError.NOT_POSSIBLE,
					HttpStatus.BAD_REQUEST);
		
	}
}
