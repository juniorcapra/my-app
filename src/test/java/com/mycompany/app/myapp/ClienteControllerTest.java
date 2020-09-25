package com.mycompany.app.myapp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.app.myapp.controller.ClienteController;
import com.mycompany.app.myapp.model.Cliente;
import com.mycompany.app.myapp.model.Conta;
import com.mycompany.app.myapp.repository.ClienteRepository;
import com.mycompany.app.myapp.service.ClienteService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ClienteController.class)
public class ClienteControllerTest {

    @MockBean
    ClienteService services;


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String URI_PATH = "/v1/cliente";
    private static final String URI_PATH_ATUALIZA_SALDO = "/atualizaSaldo/12365875841/1000.00";

    private Conta conta;


    @Test
    public void testPostCliente() throws Exception {
        Cliente clienteResponse = new Cliente();
        clienteResponse.setId(1L);
        clienteResponse.setNome("José");
        clienteResponse.setCpf("12365875841");
        conta = new Conta();
        conta.setId(1L);
        conta.setNumero("123");
        clienteResponse.setConta(conta);
        clienteResponse.setCpf("12365875841");

        when(services.create(any(Cliente.class)))
            .thenReturn(clienteResponse);

        mockMvc.perform(
            post(URI_PATH)
                .content(objectMapper.writeValueAsString(clienteResponse))
                .contentType("application/json;charset=UTF-8"))
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @Test
    public void testRecuperaTodosOsClientes() throws Exception {
        Cliente cliente1 = new Cliente();
        Cliente cliente2 = new Cliente();
        Cliente cliente3 = new Cliente();
        List<Cliente> clientes = Arrays.asList(cliente1, cliente2, cliente3);

        when(services.findAll())
            .thenReturn(clientes);

        mockMvc.perform(
            get(URI_PATH))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void testAtualizaSaldo() throws Exception {
        Cliente clienteResponse = new Cliente();
        clienteResponse.setId(1L);
        clienteResponse.setNome("José");
        clienteResponse.setCpf("12365875841");
        conta = new Conta();
        conta.setId(1L);
        conta.setNumero("123");
        clienteResponse.setConta(conta);
        clienteResponse.setCpf("12365875841");

        when(services.findByCpf(clienteResponse.getCpf()))
            .thenReturn(clienteResponse);

        mockMvc.perform(put(URI_PATH + URI_PATH_ATUALIZA_SALDO)
            .content(objectMapper.writeValueAsString(clienteResponse))
            .contentType("application/json;charset=UTF-8"))
            .andDo(print())
            .andExpect(status().isOk());
    }

}
