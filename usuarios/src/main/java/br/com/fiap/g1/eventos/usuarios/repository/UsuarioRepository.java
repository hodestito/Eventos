package br.com.fiap.g1.eventos.usuarios.repository;

import br.com.fiap.g1.eventos.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}