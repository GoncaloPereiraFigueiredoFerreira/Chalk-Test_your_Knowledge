package pt.uminho.di.chalktyk.services.exceptions;

public class UnauthorizedException extends ServiceException{
    public UnauthorizedException(String msg) {
        super(405, msg);
    }
}
