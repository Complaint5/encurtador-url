package com.complaint5.exceptions;

public class IdsDoNotMatchException extends RuntimeException {
    public IdsDoNotMatchException(){
        super("Os ids não coincidem!");
    }
    
    public IdsDoNotMatchException(String mensagem){
        super(mensagem);
    }
}
