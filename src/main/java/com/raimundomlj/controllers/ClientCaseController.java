package com.raimundomlj.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raimundomlj.dto.requests.AssociateMessageToClientCaseRequest;
import com.raimundomlj.dto.requests.CreateClientCaseRequest;
import com.raimundomlj.dto.requests.CreateMessageRequest;
import com.raimundomlj.dto.requests.ModifyClientCaseRequest;
import com.raimundomlj.dto.responses.ClientCaseResponse;
import com.raimundomlj.dto.responses.CreateClientCaseResponse;
import com.raimundomlj.dto.responses.CreateMessageResponse;
import com.raimundomlj.services.ClientCaseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/client-case")
@Api(value = "Rest api to Innso backend code exercise client case")
public class ClientCaseController {

	@Autowired
	private ClientCaseService clientCaseService;

	@PostMapping("/message/create")
	@ApiOperation(value = "create a new message to be associated after to a client case using /associate endpoint", 
		notes = "Valid channels: MAIL, SMS, FACEBOOK, TWITTER", 
		response = CreateMessageResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 417, message = "expectation failed - operation cannot be realized") })
	public ResponseEntity<?> createMessage(@Valid @RequestBody CreateMessageRequest request) {
		log.info("creating message");
		CreateMessageResponse output = clientCaseService.createMessage(request);
		if (output != null) {
			log.info("message saved with success!");
			return ResponseEntity.ok(output);
		} else {
			log.error("message not saved!");
			return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PostMapping("/create")
	@ApiOperation(value = "create a new client case to be associated after to message using /associate endpoint", response = CreateClientCaseResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 417, message = "expectation failed - operation cannot be realized") })
	public ResponseEntity<?> createClientCase(@Valid @RequestBody CreateClientCaseRequest request) {
		log.info("create client case");
		CreateClientCaseResponse output = clientCaseService.createClientCase(request);
		if (output != null) {
			log.info("client case saved with success!");
			return ResponseEntity.ok(output);
		} else {
			log.error("client case not saved!");
			return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PostMapping("/associate")
	@ApiOperation(value = "associate client case to message(s) using client case and message identifiers ", response = ClientCaseResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 400, message = "if not found client or message on database"), 
			@ApiResponse(code = 417, message = "expectation failed - operation cannot be realized") })
	public ResponseEntity<?> associateMessageToClientCase(
			@Valid @RequestBody AssociateMessageToClientCaseRequest request) {
		log.info("associate message to client case");
		ClientCaseResponse output = clientCaseService.associateMessageToClientCase(request);
		if (output != null) {
			log.info("message associated to client case with success!");
			return ResponseEntity.ok(output);
		} else {
			log.error("message not associated to client case!");
			return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PostMapping("/modify")
	@ApiOperation(value = "modify client case fields", notes="blank fields are ignored", response = CreateClientCaseResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 417, message = "expectation failed - operation cannot be realized") })
	public ResponseEntity<?> modifyClientCase(@Valid @RequestBody ModifyClientCaseRequest request) {
		log.info("modify to client case");
		ClientCaseResponse output = clientCaseService.modifyClientCase(request);
		if (output != null) {
			log.info("client case modified with success!");
			return ResponseEntity.ok(output);
		} else {
			log.error("client case not modified case!");
			return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@GetMapping("/get-all")
	@ApiOperation(value = "fetch all case fields", response = CreateClientCaseResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 417, message = "expectation failed - operation cannot be realized") })
	public ResponseEntity<List<ClientCaseResponse>> getAllClientCases() {
		log.info("fetching all client cases");
		List<ClientCaseResponse> output = clientCaseService.getAllClientCases();
		log.info("retrieved {} client cases", output.size());
		return ResponseEntity.ok(output);
	}
}
