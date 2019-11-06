package br.com.fiap.g1.eventos.relacionamento.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuario {
   private Long id;
   private String nome;
   private String email;
   private String telefone;
   private String senha;
}