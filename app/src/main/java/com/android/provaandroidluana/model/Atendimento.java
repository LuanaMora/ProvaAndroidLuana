package com.android.provaandroidluana.model;


import java.io.Serializable;
import java.util.Date;

public class Atendimento implements Serializable {
    private String assunto;
    private String contato;
    private String telefone;
    private String email;
    private String tipoAtendimento;
    private String data;
    private String empresa;

    public Atendimento(String assunto, String contato, String telefone, String email, String tipoAtendimento, String data, String empresa) {
        this.assunto = assunto;
        this.contato = contato;
        this.telefone = telefone;
        this.email = email;
        this.tipoAtendimento = tipoAtendimento;
        this.data = data;
        this.empresa = empresa;
    }

    public Atendimento(){

    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipoAtendimento() {
        return tipoAtendimento;
    }

    public void setTipoAtendimento(String tipoAtendimento) {
        this.tipoAtendimento = tipoAtendimento;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    @Override
    public String toString() {
       return tipoAtendimento + " - " + assunto + " - " + data;
    }
}
