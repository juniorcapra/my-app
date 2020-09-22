package com.mycompany.app.myapp.controller;

import com.mycompany.app.myapp.model.Cliente;
import com.mycompany.app.myapp.service.ClienteServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Clientes")
@RestController
@RequestMapping(path = "/v1/cliente", produces = "application/hal+json;charset=utf8")
public class ClienteController {

    @Autowired
    private ClienteServices service;

    @PostMapping("/generate-clients")
    public ResponseEntity clientes(
        @ApiParam(value = "Client object", required = true)
        @Valid
        @RequestBody Cliente cliente) {
        Cliente cliente1 = service.create(cliente);
        return ResponseEntity.ok(cliente1.getId());
    }

    @GetMapping("/")
    public ResponseEntity getAllClients() {
        List<Cliente> response = service.findAll();
        return ResponseEntity.ok(response);
    }



}
