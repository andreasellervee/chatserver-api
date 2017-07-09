package chatserver.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer {

    public static final String CHATSERVER_WEBSOCKET = "/chatserver-websocket";
    public static final String USER_DESTINATION = "/user";
    public static final String ALLOWED_ORIGINS_ANY = "*";

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(CHATSERVER_WEBSOCKET).setAllowedOrigins(ALLOWED_ORIGINS_ANY).withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(USER_DESTINATION);
    }
}
