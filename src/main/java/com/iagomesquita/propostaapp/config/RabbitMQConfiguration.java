package com.iagomesquita.propostaapp.config;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

  @Value("${rabbitmq.pending-proposal.exchange}")
  private String exchangePendingProposal;

  @Value("${rabbitmq.completed-proposal.exchange}")
  private String exchangeCompletedProposal;

  @Value("${rabbitmq.pending-proposal-dlx.exchange}")
  private String exchangePendingProposalDlx;

  // Filas
  @Bean
  public Queue createQueuePropostaPendenteMsAnaliseCredito() {
    return QueueBuilder
        .durable("proposta-pendente.ms-analise-credito")
        .deadLetterExchange(exchangePendingProposalDlx).build();
  }

  @Bean
  public Queue createQueuePropostaPendenteMsNotificacao() {
    return QueueBuilder.durable("proposta-pendente.ms-notificacao").build();
  }

  @Bean
  public Queue createQueuePropostaConcluidaMsProposta() {
    return QueueBuilder.durable("proposta-concluida.ms-proposta")
//        .maxLength(2)
//        .withArgument("x-dead-letter-exchange", "")
//        .withArgument("x-message-ttl", 1000)
//        .withArgument("x-dead-letter-routing-key", "proposta.concluida-dlq")
        .build();
  }

  @Bean
  public Queue createQueuePropostaConcluidaMsNotificacao() {
    return QueueBuilder.durable("proposta-concluida.ms-notificacao").build();
  }


  // Fila DLQ
  @Bean
  public  Queue createQueuePropostaPendenteDlq() {
    return QueueBuilder.durable("proposta-pendente.dlq").build();
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
    return ExchangeBuilder.fanoutExchange(exchangePendingProposal).build();
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

  @Bean
  public FanoutExchange createFanoutExchangePropostaConcluida() {
    return ExchangeBuilder.fanoutExchange(exchangeCompletedProposal).build();
  }

  @Bean
  public Binding createBindingPropostaConcluidaMSProposta() {
    return BindingBuilder.bind(createQueuePropostaConcluidaMsProposta())
        .to(createFanoutExchangePropostaConcluida());
  }

  @Bean
  public Binding createBidingPropostaConcluidaMSNotificacao() {
    return BindingBuilder.bind(createQueuePropostaConcluidaMsNotificacao())
        .to(createFanoutExchangePropostaConcluida());
  }


  // Exchange  e Bind Queue DLQ

  @Bean
  public FanoutExchange deadLetterExchange() {
    return ExchangeBuilder.fanoutExchange(exchangePendingProposalDlx).build();
  }

  @Bean
  public Binding createBindingDlq() {
    return BindingBuilder.bind(createQueuePropostaPendenteDlq())
        .to(deadLetterExchange());
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