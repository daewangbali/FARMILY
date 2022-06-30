package org.kosta.myproject.config.chat;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.OnClose;

@Component
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {
	/*
	 * Socket 통신은
	 * 	한개의 서버에서 다수의 클라이언트가 접속 가능하며
	 *  이 상태에서 채팅이 가능한 환경이다.
	 *  
	 * 다만 Socket 통신을 하기 위해, server라는 "방"에 진입하기 전에
	 * 통신 메시지를 받아 중개/매개/처리하는 중간다리가 필요하다(middleware).
	 * 
	 * 각 client는 WebSocketSession의 객체가 되어 접속하고 통신한다.
	 * client가 보내는 메시지는 Text 기반의 채팅이다.
	 * 
	 * 채팅 text를 처리하기 까지 전 단계 과정은
	 * chat roomd의 javascript가 처리한다.
	 */
    private static List<WebSocketSession> list = new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    	/*
    	 * client가 보낸 메시지를 처리한다.
    	 */
        String payload = message.getPayload();
        log.info("payload : " + payload);

        for(WebSocketSession sess: list) {
            sess.sendMessage(message);
        }
    }

    /* Client가 접속 시 호출되는 메서드 */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	/*
    	 * client가 접속하면 Websocketsession 객체를 생성한다.
    	 * 해당 session 객체를 list에 저장한다.
    	 */
        list.add(session);
        log.info(session + " 클라이언트 접속");
    }

    /* Client가 접속 해제 시 호출되는 메서드드 */

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    	/*
    	 * client가 접속을 해제하면 websocketsession 객체를 제거한다.
    	 * 해당 session 객체를 list에서 제거한다.
    	 */
        log.info(session + " 클라이언트 접속 해제");
        list.remove(session);
    }
}