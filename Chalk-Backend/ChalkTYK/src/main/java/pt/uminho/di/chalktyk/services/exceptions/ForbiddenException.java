package pt.uminho.di.chalktyk.services.exceptions;

public class ForbiddenException extends ServiceException{
    public ForbiddenException(String msg) {
        super(403, msg);
    }
}
