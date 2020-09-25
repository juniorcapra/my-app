package com.mycompany.app.myapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Conta {

    public List<BigDecimal> getHistoricoSaldo() {
        return historicoSaldo;
    }

    public void setHistoricoSaldo(List<BigDecimal> historicoSaldo) {
        this.historicoSaldo = historicoSaldo;
    }

    @ElementCollection(fetch = FetchType.LAZY)
    List<BigDecimal> historicoSaldo = new ArrayList<>();

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull()
    private BigDecimal saldo = new BigDecimal(0.0);

    @NotNull
    private String numero;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }


    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public BigDecimal atualizaSaldo(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) < 0 && saldo.subtract(valor.abs()).compareTo(BigDecimal.ZERO) >= 0) {
            saldo = this.saldo.subtract(valor.abs());
            historicoSaldo.add(valor);
        }else if(valor.compareTo(BigDecimal.ZERO) >= 0){
            saldo = this.saldo.add(valor);
            historicoSaldo.add(valor);
        }else {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        return valor;
    }



}
