package pt.uminho.di.chalktyk.apis.utility;

import pt.uminho.di.chalktyk.apis.utility.ApiException;

public class NotFoundException extends ApiException {
    private int code;
    public NotFoundException (int code, String msg) {
        super(code, msg);
        this.code = code;
    }
}
