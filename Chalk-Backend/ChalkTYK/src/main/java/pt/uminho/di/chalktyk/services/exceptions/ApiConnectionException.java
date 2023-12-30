package pt.uminho.di.chalktyk.services.exceptions;


public class ApiConnectionException extends ServiceException{
    public ApiConnectionException (String msg) {
        super(400, msg);
    }
}
