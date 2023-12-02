package pt.uminho.di.chalktyk.services.exceptions;

public class NotFoundException extends ServiceException{
    public NotFoundException(String msg) {
        super(404, msg);
    }
}
