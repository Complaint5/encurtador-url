package com.complaint5.exceptions;

public class UrlNotFoundExcecption extends RuntimeException {
    public UrlNotFoundExcecption(){
        super("URL não encontrada!");
    }
    
    public UrlNotFoundExcecption(String mensagem){
        super(mensagem);
    }
}
