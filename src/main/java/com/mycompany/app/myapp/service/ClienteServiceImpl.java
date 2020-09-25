package com.mycompany.app.myapp.service;

import com.mycompany.app.myapp.exception.ClientIsPresentException;
import com.mycompany.app.myapp.exception.ClientNotFoundException;
import com.mycompany.app.myapp.model.Cliente;
import com.mycompany.app.myapp.repository.ClienteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository repository;

    public ClienteServiceImpl() {}

    public Cliente create(Cliente cliente) {
        Optional<Cliente> clienteOptional = repository.findByCpf(cliente.getCpf());
        if(clienteOptional.isPresent()){
            throw new ClientIsPresentException("Cliente já existe no cadastro");
        }
        return repository.save(cliente);


    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente findByCpf(String cpf) {
        Optional<Cliente> clienteOptional = repository.findByCpf(cpf);
        return clienteOptional
            .orElseThrow(() -> new ClientNotFoundException("Cliente não encontrado"));
    }

    public Cliente save(Cliente cliente) {
        Cliente clienteSaved = repository.save(cliente);
        return clienteSaved;
    }
}
