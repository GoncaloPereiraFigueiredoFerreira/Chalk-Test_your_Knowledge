package pt.uminho.di.chalktyk.services.exceptions;

import lombok.Getter;

@Getter
public class ServiceException extends Exception {
    private final int code;
    public ServiceException(int code, String msg) {
        super(msg);
        this.code = code;
    }
}
