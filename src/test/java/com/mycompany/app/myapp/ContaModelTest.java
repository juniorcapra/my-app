package com.mycompany.app.myapp;

import com.mycompany.app.myapp.model.Cliente;
import com.mycompany.app.myapp.model.Conta;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {Conta.class})
@RunWith(SpringRunner.class)
public class ContaModelTest {

    private Cliente cliente;
    private Conta conta;

    @Before
    public void setup() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("José");
        cliente.setCpf("13257987125");
        conta = new Conta();
        conta.setNumero("123");
        cliente.setConta(conta);
    }

    @Test
    public void testValidaSaldoZerado() {
        Assert.assertEquals(BigDecimal.ZERO,conta.getSaldo());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAtualizaSaldoComValorNegativo() {
        conta.atualizaSaldo(new BigDecimal(10.0).negate());
    }

    @Test
    public void testValidaHistoricoTransacoes(){
        conta.atualizaSaldo(new BigDecimal(100));
        conta.atualizaSaldo(new BigDecimal(200));
        conta.atualizaSaldo(new BigDecimal(300));
        Assert.assertEquals(3, conta.getHistoricoSaldo().size());
    }





}
