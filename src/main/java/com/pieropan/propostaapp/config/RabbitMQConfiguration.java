package com.pieropan.propostaapp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
  // Filas
  @Bean
  public Queue createQueuePropostaPendenteMsAnaliseCredito() {
    return QueueBuilder.durable("proposta-pendente.ms-analise-credito").build();
  }

  @Bean
  public Queue createQueuePropostaPendenteMsNotificacao() {
    return QueueBuilder.durable("proposta-pendente.ms-notificacao").build();
  }

  @Bean
  public Queue createQueuePropostaConcluidaMsProposta() {
    return QueueBuilder.durable("proposta-concluida.ms-proposta").build();
  }

  @Bean
  public Queue createQueuePropostaConcluidaMsNotificacao() {
    return QueueBuilder.durable("proposta-concluida.ms-notificacao").build();
  }

  // Admin de Filas e Exchanges
  @Bean
  public RabbitAdmin createRabbitAdmin(ConnectionFactory connectionFactory) {
    return new RabbitAdmin(connectionFactory);
  }

  @Bean
  public ApplicationListener<ApplicationReadyEvent> initializeAdmin(RabbitAdmin rabbitAdmin) {
    return event -> rabbitAdmin.initialize();
  }

  // Exchange Proposta e Binds para Queue de proposta-pendente e notificacao
  @Bean
  public FanoutExchange createFanoutExchangePropostaPendente() {
    return ExchangeBuilder.fanoutExchange("proposta-pendente.ex").build();
  }

  @Bean
  public Binding createBindingPropostaPendenteMSAnaliseCredito() {
    return BindingBuilder.bind(createQueuePropostaPendenteMsAnaliseCredito()).
        to(createFanoutExchangePropostaPendente());
  }

  @Bean
  public Binding createBindingPropostaPendenteToMSNotificacao() {
    return BindingBuilder.bind(createQueuePropostaPendenteMsNotificacao()).
        to(createFanoutExchangePropostaPendente());
  }

//  Configurando para o RabbitTamplate aceitar um objeto JSON

  @Bean
  public MessageConverter jackson2JsonMessageConverter( ) {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate();
    rabbitTemplate.setConnectionFactory(connectionFactory);
    rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());

    return rabbitTemplate;
  }

}


//Explicando os metodos com chatGPT:
//https://chatgpt.com/share/66edd819-2098-800a-b73d-3328edac4d1a