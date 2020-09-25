package com.mycompany.app.myapp.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ClienteVO {

    @NotEmpty
    @Valid
    @JsonProperty("nome")
    private String nome;

    @NotNull()
    @JsonProperty("saldo")
    private BigDecimal saldo;

    @JsonProperty("historico-movimentação")
    List<BigDecimal> historicoSaldo = new ArrayList<>();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public List<BigDecimal> getHistoricoSaldo() {
        return historicoSaldo;
    }

    public void setHistoricoSaldo(List<BigDecimal> historicoSaldo) {
        this.historicoSaldo = historicoSaldo;
    }


    public ClienteVO(String nome, BigDecimal saldo, List<BigDecimal> historicoSaldo) {
        this.nome = nome;
        this.saldo = saldo;
        this.historicoSaldo = historicoSaldo;
    }


}
