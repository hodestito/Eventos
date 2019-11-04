package br.com.fiap.g1.eventos.vendas.model;

public enum MeioPagamento {

    CARTAO(1),
    DINHEIRO(2),
    BOLETO(3),
    DEBITO(4);

    private int valor;

    MeioPagamento(int valorPagamento){
        valor = valorPagamento;
    }

    public int getValor(){
        return valor;
    }

}
