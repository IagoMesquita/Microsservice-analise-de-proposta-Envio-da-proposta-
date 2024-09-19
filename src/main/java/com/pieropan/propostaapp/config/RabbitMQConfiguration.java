package com.pieropan.propostaapp.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

  public Queue createQueuePropostaPendenteMsAnaliseCredito() {
    return QueueBuilder.durable("proposta-pendente.ms-analise-credito").build();
  }

  public Queue createQueuePropostaPendenteMsNotificacao() {
    return QueueBuilder.durable("proposta-pendente.ms-notificacao").build();
  }

  public Queue createQueuePropostaConcluidaMsProposta() {
    return QueueBuilder.durable("proposta-concluida.ms-proposta").build();
  }

  public Queue createQueuePropostaConcluidaMsNotificacao() {
    return QueueBuilder.durable("proposta-concluida.ms-notificacao").build();
  }

}

// QueueBuilder para criar quatro filas (queues) duráveis no RabbitMQ.
// As filas duráveis são persistentes, ou seja, elas não são perdidas se o RabbitMQ for reiniciado.