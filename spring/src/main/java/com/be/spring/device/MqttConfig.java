//package com.be.spring.device;
//
//import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.integration.annotation.ServiceActivator;
//import org.springframework.integration.channel.DirectChannel;
//import org.springframework.integration.core.MessageProducer;
//import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
//import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
//import org.springframework.integration.mqtt.support.MqttHeaders;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.MessageHandler;
//
//@Configuration
//public class MqttConfig {
//
//    private static final String BROKER_URL = "tcp://{165.246.116.141}:{1336}";
//    private static final String MQTT_CLIENT_ID = MqttAsyncClient.generateClientId();
//    private static final String TOPIC_FILTER = "parking_lot/0/#";
//
//    @Bean
//    public MessageChannel mqttInputChannel() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    public MessageProducer inboundChannel(){
//        MqttPahoMessageDrivenChannelAdapter adapter =
//                new MqttPahoMessageDrivenChannelAdapter(BROKER_URL, MQTT_CLIENT_ID, TOPIC_FILTER);
//        adapter.setCompletionTimeout(5000);
//        adapter.setConverter(new DefaultPahoMessageConverter());
//        adapter.setQos(1);
//        adapter.setOutputChannel(mqttInputChannel());
//        return adapter;
//    }
//
//    @Bean
//    @ServiceActivator(inputChannel = "mqttInputChannel")
//    public MessageHandler inboundMessageHandler() {
//        return message -> {
//            String topic = (String) message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC);
//            System.out.println("Topic" + topic);
//            System.out.println("Payload" + message.getPayload());
//        };
//    }
//}
