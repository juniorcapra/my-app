package com.mycompany.app.myapp.service;

import com.mycompany.app.myapp.model.Cliente;
import java.util.List;

public interface ClienteService {

    Cliente create(Cliente cliente);
    List<Cliente> findAll();
    Cliente findByCpf(String cpf);
    Cliente save(Cliente cliente);

}
