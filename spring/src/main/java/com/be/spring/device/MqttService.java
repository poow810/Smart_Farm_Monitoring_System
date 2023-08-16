package com.be.spring.device;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MqttService {

    @Value("${mqtt.url}")
    private String mqttServer;

    private final MqttProperties mqttProperties;

    private MqttClient mqttClient;

    @Autowired
    public MqttService(MqttProperties mqttProperties) {
        this.mqttProperties = mqttProperties;
        try {
            mqttClient = new MqttClient(mqttServer, MqttClient.generateClientId(), new MemoryPersistence());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setUserName(mqttProperties.getUsername());
            options.setPassword(mqttProperties.getPassword().toCharArray());

            mqttClient.connect(options);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publish(String message) {
        try {
            mqttClient.publish(mqttProperties.getTopic(), new MqttMessage(message.getBytes()));
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void handleReceivedMessage(String topic, String payload) {
        // 여기에 메시지를 수신했을 때의 로직을 추가합니다.
        System.out.println("Topic: " + topic);
        System.out.println("Payload: " + payload);
    }

    public void disconnect() {
        try {
            mqttClient.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
