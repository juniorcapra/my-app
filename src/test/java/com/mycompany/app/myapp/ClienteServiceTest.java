package com.mycompany.app.myapp;

import static org.mockito.Mockito.when;

import com.mycompany.app.myapp.model.Cliente;
import com.mycompany.app.myapp.model.Conta;
import com.mycompany.app.myapp.repository.ClienteRepository;
import com.mycompany.app.myapp.service.ClienteService;
import com.mycompany.app.myapp.service.ClienteServiceImpl;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {ClienteServiceImpl.class})
@RunWith(SpringRunner.class)
public class ClienteServiceTest {

    @Autowired
    ClienteService services;

    @MockBean
    ClienteRepository repository;

    private Cliente cliente;
    private Conta conta;

    @Before
    public void setup() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("José");
        cliente.setCpf("12365875841");
        conta = new Conta();
        conta.setId(1L);
        conta.setNumero("123");
        cliente.setConta(conta);

    }


    @Test
    public void testValidaBuscaPorCpf() {
        Cliente clienteRetorno = new Cliente();
        clienteRetorno.setId(1L);
        clienteRetorno.setCpf("12365875841");

        when(repository.findByCpf(cliente.getCpf()))
            .thenReturn(Optional.of(Optional.ofNullable(clienteRetorno).get()));

        Cliente clientePorCpf = services.findByCpf(clienteRetorno.getCpf());
        Assert.assertEquals("12365875841",clientePorCpf.getCpf());
    }

}
