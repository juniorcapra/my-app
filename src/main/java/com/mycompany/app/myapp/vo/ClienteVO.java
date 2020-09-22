package com.mycompany.app.myapp.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class ClienteVO {

    @NotEmpty
    @Valid
    @JsonProperty("nome")
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @NotBlank
    @JsonProperty(value = "cpf")
    private String cpf;

    public ClienteVO(
        @NotEmpty @Valid String nome, @NotBlank String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }


}
