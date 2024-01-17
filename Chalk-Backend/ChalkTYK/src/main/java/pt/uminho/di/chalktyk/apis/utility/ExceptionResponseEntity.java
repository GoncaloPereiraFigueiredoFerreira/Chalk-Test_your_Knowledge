package pt.uminho.di.chalktyk.apis.utility;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import pt.uminho.di.chalktyk.services.exceptions.ServiceException;

import java.util.List;

public class ExceptionResponseEntity<T> {
    public ExceptionResponseEntity() {
    }

    public ResponseEntity<T> createRequest(T body, int code, String message){
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-error", message);
        headers.setAccessControlExposeHeaders(List.of("*"));
        return new ResponseEntity<T>(body, headers, HttpStatusCode.valueOf(code));
    }

    public ResponseEntity<T> createRequest(int code, String message){
        return createRequest(null, code, message);
    }

    public ResponseEntity<T> createRequest(T body, ServiceException exception){
        return createRequest(body, exception.getCode(), exception.getMessage());
    }

    public ResponseEntity<T> createRequest(ServiceException exception){
        return createRequest(null, exception.getCode(), exception.getMessage());
    }
}