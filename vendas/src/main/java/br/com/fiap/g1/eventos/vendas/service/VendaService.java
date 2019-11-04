package br.com.fiap.g1.eventos.vendas.service;

import br.com.fiap.g1.eventos.vendas.model.MeioPagamento;
import br.com.fiap.g1.eventos.vendas.model.Venda;
import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class VendaService {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Value("${vendas.topic}")
    private String TOPIC;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        logger.info(String.format("#### -> Producing message -> %s", message));
        this.kafkaTemplate.send(TOPIC, message);
    }

    /** Autoriza somente pagamentos que não sejam em dinheiro */
    public Boolean autorizaVenda(Venda venda) {
        return (venda.getMeioPagamento() == MeioPagamento.DINHEIRO ? false: true);
    }
}
