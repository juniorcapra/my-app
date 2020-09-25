package com.mycompany.app.myapp;

import com.mycompany.app.myapp.model.Cliente;
import com.mycompany.app.myapp.utils.ValidadorUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {Cliente.class})
@RunWith(SpringRunner.class)
public class ClienteModelTest {

    private Cliente cliente;

    @Before
    public void setup() {
        cliente = new Cliente();
        cliente.setNome("José");
    }

    @Test
    public void testValidaCpfInvalido() {
        cliente.setCpf("13257987XZA");
        Assert.assertFalse(ValidadorUtils.ehCpfValido(cliente.getCpf()));
    }

    @Test
    public void testValidaCpfValido() {
        cliente.setCpf("13256467987");
        Assert.assertTrue(ValidadorUtils.ehCpfValido(cliente.getCpf()));
    }
}
