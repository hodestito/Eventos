package br.com.fiap.g1.eventos.eventos.repository;

import br.com.fiap.g1.eventos.eventos.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
}