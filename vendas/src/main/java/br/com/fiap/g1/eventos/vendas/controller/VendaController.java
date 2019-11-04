package br.com.fiap.g1.eventos.vendas.controller;

import br.com.fiap.g1.eventos.vendas.model.Venda;
import br.com.fiap.g1.eventos.vendas.repository.VendaRepository;
import br.com.fiap.g1.eventos.vendas.service.VendaService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaRepository repository;

    private final VendaService service;

    @Autowired
    VendaController(VendaService vendaservice) {
        this.service = vendaservice;
    }

    @ApiOperation(value = "Retorna a lista completa de vendas")
    @GetMapping(path = {""})
    public List<Venda> findAll() {
        return repository.findAll();
    }

    @ApiOperation(value = "Retorna vendas pelo ID")
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Venda> findById(@PathVariable long id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    /** POST ORIGINAL
    @ApiOperation(value = "Cria uma nova venda")
    @PostMapping
    public Venda create(@RequestBody Venda venda) {
        Venda newVenda = repository.save(venda);
        this.service.sendMessage(newVenda.toString());
        return newVenda;
    }
    */

    @ApiOperation(value = "Cria uma nova venda")
    @PostMapping
    public Venda create(@RequestBody Venda venda) {
        Venda newVenda = repository.save(venda);
        this.service.sendMessage(newVenda.toString());
        return newVenda;
    }

    @ApiOperation(value = "Atualiza uma venda pelo ID")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Venda> update(@PathVariable("id") long id,
                                          @RequestBody Venda venda) {
        return repository.findById(id)
                .map(record -> {
                    record.setIdEvento(venda.getIdEvento());
                    record.setIdUsuario(venda.getIdUsuario());
                    record.setDataHora(venda.getDataHora());
                    record.setMeioPagamento(venda.getMeioPagamento());
                    Venda updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Apaga uma venda pelo ID")
    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<?> delete(@PathVariable long id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}