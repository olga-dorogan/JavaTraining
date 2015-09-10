package com.custom.model.exception;

/**
 * Created by olga on 03.03.15.
 */
public class DAOBusinessException extends RuntimeException {
    public DAOBusinessException(){
        super();
    }

    public DAOBusinessException(String msg) {
        super(msg);
    }

    public DAOBusinessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
