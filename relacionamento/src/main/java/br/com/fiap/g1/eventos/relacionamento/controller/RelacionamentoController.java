package br.com.fiap.g1.eventos.relacionamento.controller;

import br.com.fiap.g1.eventos.relacionamento.service.RelacionamentoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/relacionamento")
public class RelacionamentoController {

    private RelacionamentoService service;

    @Autowired
    RelacionamentoController(RelacionamentoService relacionamentoService) {
        this.service = relacionamentoService;
    }

    @ApiOperation(value = "Envia mensagem para o canal Fale conosco")
    @PostMapping
    public ResponseEntity<?> faleConosco(@RequestBody String mensagem){
        this.service.faleConoso(mensagem);
        return (ResponseEntity.ok().build());
    }


}