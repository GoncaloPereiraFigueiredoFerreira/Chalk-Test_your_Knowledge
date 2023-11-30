package pt.uminho.di.chalktyk.services.exceptions;

public class BadInputException extends ServiceException{
    public BadInputException(String msg) {
        super(400, msg);
    }
}
