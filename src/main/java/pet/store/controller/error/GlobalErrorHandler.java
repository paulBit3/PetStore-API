package pet.store.controller.error;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;





/*
 * Global error handler calss
 */



@RestControllerAdvice
@Slf4j                           //Spring Boot uses the Logback logging framework to perform the logging.
public class GlobalErrorHandler {
	
	/*
	 * Exception handler method for all general Exceptions
	*/
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Map<String, String> handleNoSuchElementException(Exception ex) {
		//a Map set of exceptions
		Map<String, String> exception = new HashMap<>();
		String msg = ex.toString();
		
		log.error("Exception: {}", msg);
		
		//add exception message
		exception.put("message", msg);
		
		return exception;
	}


}
