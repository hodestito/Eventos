package br.com.fiap.g1.eventos.eventos.controller;

import br.com.fiap.g1.eventos.eventos.model.Evento;
import br.com.fiap.g1.eventos.eventos.repository.EventoRepository;
import br.com.fiap.g1.eventos.eventos.service.EventoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoRepository repository;

    private final EventoService service;

    @Autowired
    EventoController(EventoService eventoservice) {
        this.service = eventoservice;
    }

    @ApiOperation(value = "Retorna a lista de eventos cadastrados")
    @GetMapping(path = {""})
    public List<Evento> findAll() {
        return repository.findAll();
    }

    @ApiOperation(value = "Retorna eventos pelo ID")
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Evento> findById(@PathVariable long id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Cria um novo evento")
    @PostMapping
    public Evento create(@RequestBody Evento evento) {
        Evento newUsr = repository.save(evento);
        this.service.sendMessage(newUsr.toString());
        return newUsr;
    }

    @ApiOperation(value = "Atualiza um evento pelo ID")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Evento> update(@PathVariable("id") long id,
                                          @RequestBody Evento evento) {
        return repository.findById(id)
                .map(record -> {
                    record.setNome(evento.getNome());
                    record.setLocal(evento.getLocal());
                    record.setValor(evento.getValor());
                    Evento updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Apaga um evento pelo ID")
    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<?> delete(@PathVariable long id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}