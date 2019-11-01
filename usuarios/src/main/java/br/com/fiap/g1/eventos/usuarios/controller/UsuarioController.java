package br.com.fiap.g1.eventos.usuarios.controller;

import br.com.fiap.g1.eventos.usuarios.model.Usuario;
import br.com.fiap.g1.eventos.usuarios.repository.UsuarioRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @ApiOperation(value = "Retorna a lista de usuarios cadastrados")
    @GetMapping(path = {""})
    public List<Usuario> findAll(){
        return repository.findAll();
    }

    @ApiOperation(value = "Retorna usuarios pelo ID")
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Usuario> findById(@PathVariable long id){
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Cria um novo usuario")
    @PostMapping
    public Usuario create(@RequestBody Usuario usuario){
        return repository.save(usuario);
    }

    @ApiOperation(value = "Atualiza um usuário pelo ID")
    @PutMapping(value="/{id}")
    public ResponseEntity<Usuario> update(@PathVariable("id") long id,
                                 @RequestBody Usuario usuario) {
        return repository.findById(id)
                .map(record -> {
                    record.setNome(usuario.getNome());
                    record.setEmail(usuario.getEmail());
                    record.setTelefone(usuario.getTelefone());
                    Usuario updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Apaga um usuário pelo ID")
    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity<?> delete(@PathVariable long id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}