package org.kosta.myproject.vo;

import java.awt.TrayIcon.MessageType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageVO {
	private String id;
    private String chatRoomId;
    private String sender;
    private String message;
    private MessageType messageType;
}
