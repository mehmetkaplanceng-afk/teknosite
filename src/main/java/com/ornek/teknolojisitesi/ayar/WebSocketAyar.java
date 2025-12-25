package com.ornek.teknolojisitesi.ayar;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketAyar implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // İstemcinin abone olacağı prefix
        config.enableSimpleBroker("/topic");

        // İstemcinin mesaj gönderirken kullanacağı prefix
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Tarayıcı tarafında SockJS ile bağlanacağımız endpoint
        registry.addEndpoint("/ws-sohbet")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
