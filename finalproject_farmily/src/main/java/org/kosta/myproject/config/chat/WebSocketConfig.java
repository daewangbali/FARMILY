package org.kosta.myproject.config.chat;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	/*
	 * handler - client 접속시 websocket 객체 생성 및 메시지 처리
	 * 해당 handler를 활성화(객체 활성화 및 연결 경로(endpoint) 연결)
	 * 다른 서버에서도 접속이 가능하도록 setAllowedOrigins 설정("*").
	 */
    private final WebSocketHandler chatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    	// websocket가 접속하면 ws를 프로토콜로 하여 해당 서버에 접속한다.
    	// ws:// ~ / ws/chat(연결 도메인)을 설정한다.
    	// security 처리
        registry.addHandler(chatHandler, "/ws/chat").setAllowedOrigins("*");
    }
}