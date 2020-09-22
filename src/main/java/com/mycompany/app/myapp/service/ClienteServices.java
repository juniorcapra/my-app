package com.mycompany.app.myapp.service;

import com.mycompany.app.myapp.model.Cliente;
import com.mycompany.app.myapp.repository.ClienteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServices {

    @Autowired
    ClienteRepository repository;

    public Cliente create(Cliente cliente) {
        return repository.save(cliente);
    }

    public List<Cliente> findAll(){
        return repository.findAll();
    }

}
