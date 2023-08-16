package com.be.spring.device;

import jakarta.websocket.server.ServerEndpoint;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class MqttProperties {

    @Value("${mqtt.url}")
    private String url;

    @Value("${mqtt.port}")
    private int port;

    @Value("${mqtt.qos}")
    private int qos;

    @Value("${mqtt.topic}")
    private String topic;

    @Value("${mqtt.username}")
    private String username;

    @Value("${mqtt.password}")
    private String password;
}
