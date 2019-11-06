package br.com.fiap.g1.eventos.relacionamento.service;

import br.com.fiap.g1.eventos.relacionamento.pojo.Usuario;
import br.com.fiap.g1.eventos.relacionamento.utils.SlackMessage;
import br.com.fiap.g1.eventos.relacionamento.utils.SlackUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class RelacionamentoService {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy HH:ss");
    StringBuilder sb = new StringBuilder();
    Usuario usuario;
    String msg;

    @Autowired
    private JavaMailSender javaMailSender;

    @KafkaListener(topics = "${usuarios.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String message) {
        System.out.println("Received message: " + message);

        try {
            usuario = new ObjectMapper().readValue(message, Usuario.class);
            sb.append("Prezado cliente " + usuario.getNome());
            sb.append("\n\nO seu cadastro foi efetuado com sucesso. \n\n\n");
            sb.append("São Paulo, " + sdf.format(new Date(System.currentTimeMillis())));
            msg = sb.toString();
            sendMail("${spring.mail.username}", usuario.getEmail(), "Confirmação de cadastro", msg);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("Email enviado para: " + usuario.getEmail() + " com a mensagem: " + msg);
    }

    public void sendMail(String from, String to, String subject, String body) {

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setFrom(from);
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(body);

        javaMailSender.send(mail);
    }

    public void faleConoso(String message) {
        sendToSlack(message);
    }

    public void sendToSlack(String mensagem) {


        SlackMessage slackMessage = SlackMessage.builder()
                .channel("#microservices")
                .username("Locallee")
                .text(mensagem)
                .icon_emoji(":scream:")
                .build();
        SlackUtils.sendMessage(slackMessage);

        System.out.println("Mensagem enviada ao slack:" + mensagem);

    }
}
