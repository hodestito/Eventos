package br.com.fiap.g1.eventos.relacionamento.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
  
  @Component
  public class SlackUtils {

      @Value("${slack.webhook}")
      private String slackString;

      @Value("${slack.final}")
      private String slackFinal;

      private String slackWebhookUrl = "https://hooks.slack.com/services/";

      public void sendMessage(SlackMessage message) {

          slackWebhookUrl += slackString + slackFinal;
          System.out.println("Slack webhook utilizado: " + slackWebhookUrl);

          CloseableHttpClient client = HttpClients.createDefault();
          HttpPost httpPost = new HttpPost(slackWebhookUrl);

          try {
              ObjectMapper objectMapper = new ObjectMapper();
              String json = objectMapper.writeValueAsString(message);
  
              StringEntity entity = new StringEntity(json);
              httpPost.setEntity(entity);
              httpPost.setHeader("Accept", "application/json");
              httpPost.setHeader("Content-type", "application/json");
  
              client.execute(httpPost);
              client.close();
          } catch (IOException e) {
             e.printStackTrace();
          }
      }
  }
