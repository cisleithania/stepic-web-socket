package chat;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class ChatWebSocket {
    private ChatService chatService;
    private Session session;

    // создается веб-сокет
    public ChatWebSocket(ChatService chatService) {
        this.chatService = chatService;
    }

    // установка соединения
    @OnWebSocketConnect
    public void onOpen(Session session) {
        chatService.add(this);
        this.session = session;
    }

    // получение сообщения
    @OnWebSocketMessage
    public void onMessage(String data) {
        chatService.sendMessage(data);
    }

    // закрытие сокета
    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        chatService.remove(this);
    }

    // отправление данных (сообщения) клиенту
    public void sendString(String data) {
        try {
            session.getRemote().sendString(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
