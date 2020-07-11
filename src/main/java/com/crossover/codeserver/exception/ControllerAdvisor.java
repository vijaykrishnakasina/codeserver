package com.crossover.codeserver.exception;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
	
	 @ExceptionHandler(SystemNotFoundException.class)
	    public ResponseEntity<Object> systemNotFoundException(
	    		SystemNotFoundException ex, WebRequest request) {
	        Map<String, Object> body = new LinkedHashMap<>();
	        body.put("timestamp", LocalDateTime.now());
	        body.put("message", "System not found");
	        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	    }
	 
	 @ExceptionHandler(ProjectNotFoundException.class)
	    public ResponseEntity<Object> projectNotFoundException(
	    		ProjectNotFoundException ex, WebRequest request) {
	        Map<String, Object> body = new LinkedHashMap<>();
	        body.put("timestamp", LocalDateTime.now());
	        body.put("message", "Project not found");
	        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	    }
	 
	@ExceptionHandler(ConflictingDataException.class)
	    public ResponseEntity<Object> conflictingDataException(
	    		ConflictingDataException ex, WebRequest request) {
	        Map<String, Object> body = new LinkedHashMap<>();
	        body.put("timestamp", LocalDateTime.now());
	        body.put("message", "Conflicting input data");
	        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
	    }
	
	@ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> illegalArgumentException(
    		IllegalArgumentException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Illegal input data");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", status.value());

		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		body.put("errors", errors);

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
}
