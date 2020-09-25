package com.mycompany.app.myapp.controller;

import com.mycompany.app.myapp.exception.ClientIsPresentException;
import com.mycompany.app.myapp.exception.ClientNotFoundException;
import com.mycompany.app.myapp.model.Cliente;
import com.mycompany.app.myapp.model.Transacao;
import com.mycompany.app.myapp.service.ClienteService;
import com.mycompany.app.myapp.utils.ValidadorUtils;
import com.mycompany.app.myapp.vo.ClienteVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.math.BigDecimal;
import java.net.URI;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Api(tags = "Saldos")
@RestController
@RequestMapping(path = "/v1/saldo", produces = "application/hal+json;charset=utf8")
public class SaldoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaldoController.class);

    @Autowired
    private ClienteService clienteService;


    @PostMapping("/addSaldo/{cpf}/{valor}")
    public ResponseEntity atualizaSaldo(
        @ApiParam(value = "Saldo", required = true)
        @Valid
        @PathVariable String cpf, @PathVariable BigDecimal valor) {
        try {
            Cliente clientResponse = clienteService.findByCpf(cpf);
            clientResponse.getConta().atualizaSaldo(valor);
            return ResponseEntity.ok(clienteService.save(clientResponse));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ClientNotFoundException(e.getMessage());
        }
    }

    @ApiOperation("Get saldo by CPF")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success. Return ex.: {\"saldo\": }"),
        @ApiResponse(code = 422, message = "Resource not found.")
    })
    @GetMapping("/{cpf}")
    public ResponseEntity informacoesBancarias(@PathVariable("cpf") String cpf)
        throws Exception {

        try {
            Cliente clientResponse = clienteService.findByCpf(cpf);
            ClienteVO clienteVO = new ClienteVO(clientResponse.getNome(),clientResponse.getConta().getSaldo(),clientResponse.getConta().getHistoricoSaldo());
            return ResponseEntity.ok(clienteVO);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ClientNotFoundException(e.getMessage());
        }
    }


    @PostMapping("/transfer")
    public ResponseEntity transferencia(
        @ApiParam(value = "Client object", required = true)
        @Valid
        @RequestBody Transacao transacao) {
        try {
            if (ValidadorUtils.ehCpfValido(transacao.getCpfOrigem()) && ValidadorUtils.ehCpfValido(transacao.getCpfDestino())
            && transacao.getValor().compareTo(BigDecimal.ZERO) > 0){
                processaTransferencia(transacao);

                URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(transacao.getId()).toUri();
                return ResponseEntity.created(location).build();
            } else {
                throw new IllegalArgumentException("Cpf inválido");
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ClientIsPresentException(e.getMessage());
        }
    }

    private void processaTransferencia(Transacao transacao) {
        Cliente origem = clienteService.findByCpf(transacao.getCpfOrigem());
        Cliente destino = clienteService.findByCpf(transacao.getCpfDestino());

        origem.getConta()
            .atualizaSaldo(transacao.getValor().negate());
        clienteService.save(origem);
        destino.getConta()
            .atualizaSaldo(transacao.getValor());
        clienteService.save(destino);
    }

}
