package com.mycompany.app.myapp.controller;

import com.mycompany.app.myapp.exception.ClientIsPresentException;
import com.mycompany.app.myapp.exception.ClientNotFoundException;
import com.mycompany.app.myapp.model.Cliente;
import com.mycompany.app.myapp.service.ClienteService;
import com.mycompany.app.myapp.utils.ValidadorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Api(tags = "Clientes")
@RestController
@RequestMapping(path = "/v1/cliente", produces = "application/hal+json;charset=utf8")
public class ClienteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    private ClienteService service;

    @PostMapping()
    public ResponseEntity cliente(
        @ApiParam(value = "Client object", required = true)
        @Valid
        @RequestBody Cliente cliente) {
        try {
            if (ValidadorUtils.ehCpfValido(cliente.getCpf())) {
                Cliente clienteResponse = service.create(cliente);

                URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(clienteResponse.getId()).toUri();
                return ResponseEntity.created(location).build();
            } else {
                throw new IllegalArgumentException("Cpf inválido");
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ClientIsPresentException(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity clientes() {
        List<Cliente> response = service.findAll();
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get client by CPF")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success. Return ex.: {\"cliente\": }"),
        @ApiResponse(code = 422, message = "Resource not found.")
    })
    @GetMapping("/{cpf}")
    public ResponseEntity clienteByCnpj(@PathVariable("cpf") String cpf)
        throws Exception {

        try {
            Cliente clientResponse = service.findByCpf(cpf);
            return ResponseEntity.ok(clientResponse);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ClientNotFoundException(e.getMessage());
        }
    }

    @PutMapping("/atualizaSaldo/{cpf}/{saldo}")
    public ResponseEntity atualizaSaldo(
        @ApiParam(value = "Client object", required = true)
        @Valid
        @PathVariable String cpf, @PathVariable BigDecimal saldo) {
        try {
            Cliente clientResponse = service.findByCpf(cpf);
            clientResponse.getConta().atualizaSaldo(saldo);
            return ResponseEntity.ok(service.save(clientResponse));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ClientNotFoundException(e.getMessage());
        }
    }
}
