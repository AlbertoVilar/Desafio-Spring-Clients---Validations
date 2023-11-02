package com.desafiocriente.DSCliente.dto;

public class FildMessage {

    private String fildName;
    private String msg;

    public FildMessage(String fildName, String msg) {
        this.fildName = fildName;
        this.msg = msg;
    }

    public String getFildNameName() {
        return fildName;
    }

    public String getMsg() {
        return msg;
    }
}
