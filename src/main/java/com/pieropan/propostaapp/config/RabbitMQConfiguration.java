package com.pieropan.propostaapp.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
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

  @Bean
  public RabbitAdmin createRabbitAdmin(ConnectionFactory connectionFactory) {
    return new RabbitAdmin(connectionFactory);
  }

  @Bean
  public ApplicationListener<ApplicationReadyEvent> initializeAdmin(RabbitAdmin rabbitAdmin) {
    return event -> rabbitAdmin.initialize();
  }

}

// QueueBuilder para criar quatro filas (queues) duráveis no RabbitMQ.
// As filas duráveis são persistentes, ou seja, elas não são perdidas se o RabbitMQ for reiniciado.

//Os dois novos métodos adicionados à configuração do RabbitMQ têm o objetivo de garantir que as
// filas sejam corretamente declaradas no RabbitMQ assim que a aplicação estiver pronta para uso.
//
//createRabbitAdmin:
//
//Esse método cria um bean de RabbitAdmin, que é uma classe do Spring AMQP responsável por gerenciar
// automaticamente as declarações de filas, exchanges e bindings (ligações) no RabbitMQ.
// O RabbitAdmin usa a ConnectionFactory (fábrica de conexões) para se conectar ao RabbitMQ e
// realizar essas operações administrativas.
//Resumindo, ele é o administrador das filas e outras configurações no RabbitMQ. Sem ele, as filas
// que você definiu com QueueBuilder não seriam automaticamente declaradas no broker RabbitMQ.
//
// initializeAdmin:
//
//Esse método cria um listener que é disparado quando a aplicação está pronta para ser executada (evento ApplicationReadyEvent).
// Quando esse evento ocorre, o listener chama o método rabbitAdmin.initialize(),
// que força a inicialização do RabbitAdmin, garantindo que todas as filas configuradas sejam declaradas no RabbitMQ.
//O objetivo é garantir que o RabbitAdmin inicialize no momento correto, após a aplicação estar completamente preparada, evitando erros ou problemas relacionados à conexão com o broker RabbitMQ.