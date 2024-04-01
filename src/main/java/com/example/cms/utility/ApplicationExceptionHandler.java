package com.example.cms.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.cms.exception.BlogNotFoundByIdException;
import com.example.cms.exception.ContributionPanelNotFoundByIdException;
import com.example.cms.exception.TitleAlreadyExistsException;
import com.example.cms.exception.TopicNotSpecifiedException;
import com.example.cms.exception.UserAlreadyExistsByEmailException;
import com.example.cms.exception.UserNotFoundByIdException;

import lombok.AllArgsConstructor;

@RestControllerAdvice
@AllArgsConstructor
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
	
	private ErrorStructure<Object> errorStructure;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		Map<String, String> messages = new HashMap<String, String>();
		List<ObjectError> errors = ex.getAllErrors();
		
		errors.forEach(error->{
			FieldError fe = (FieldError) error;
			messages.put(fe.getField(), error.getDefaultMessage());
		});
		
		return ResponseEntity.badRequest().body(errorStructure.setErrorCode(HttpStatus.BAD_REQUEST.value())
							 								  .setErrorMessage("Invalid Inputs!")
							 								  .setRootCause(messages));
	}
	
	private ResponseEntity<ErrorStructure<Object>> errorResponse(HttpStatus status, String message, String errorData) {
		return new ResponseEntity<ErrorStructure<Object>>(errorStructure.setErrorCode(status.value())
				.setErrorMessage(message)
				.setRootCause(errorData),status);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<Object>> handleUserAlreadyExistsByEmail(UserAlreadyExistsByEmailException ex) {
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "User already registered");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<Object>> handleUserNotFoundById(UserNotFoundByIdException ex) {
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "User does not exist by this id");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<Object>> handleTitleAlreadyExists(TitleAlreadyExistsException ex) {
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "Title Already exists change the title");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<Object>> handleTopicNotSpecified(TopicNotSpecifiedException ex) {
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "Topic not specified");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<Object>> handleBlogNotFoundById(BlogNotFoundByIdException ex) {
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "Blog Not Found");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<Object>> handleContributionPanelNotFoundById(ContributionPanelNotFoundByIdException ex) {
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "Panel not found by Id");
	}
}
