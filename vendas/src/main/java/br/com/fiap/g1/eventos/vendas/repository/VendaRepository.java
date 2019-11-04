package br.com.fiap.g1.eventos.vendas.repository;

import br.com.fiap.g1.eventos.vendas.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
}