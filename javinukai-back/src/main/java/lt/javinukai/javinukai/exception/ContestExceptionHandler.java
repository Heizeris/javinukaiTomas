package lt.javinukai.javinukai.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ContestExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ContestErrorResponse> contestHandleException(ContestNotFoundException e) {
        final ContestErrorResponse errorResponse = new ContestErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
