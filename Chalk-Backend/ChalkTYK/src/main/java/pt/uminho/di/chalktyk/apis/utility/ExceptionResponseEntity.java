package pt.uminho.di.chalktyk.apis.utility;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import pt.uminho.di.chalktyk.services.exceptions.ServiceException;

import java.util.List;

public class ExceptionResponseEntity<T> {
    public ExceptionResponseEntity() {
    }

    public ResponseEntity<T> createRequest(T body, ServiceException exception){
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-error", exception.getMessage());
        headers.setAccessControlExposeHeaders(List.of("*"));
        return new ResponseEntity<T>(body, headers, HttpStatusCode.valueOf(exception.getCode()));
    }

    public ResponseEntity<T> createRequest(ServiceException exception){
        return createRequest(null, exception);
    }
}