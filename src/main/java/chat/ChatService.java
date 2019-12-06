package chat;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChatService {

    // список подсоединившихся пользователей
    private Set<ChatWebSocket> webSockets;

    public ChatService() {
        // создание потокобезопасного Set-а
        this.webSockets = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    // каждому пользователю отправляется сообщение (у всех вызывается sendString)
    public void sendMessage(String data) {
        for (ChatWebSocket user : webSockets) {
            try {
                user.sendString(data);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void add(ChatWebSocket webSocket) {
        webSockets.add(webSocket);
    }

    public void remove(ChatWebSocket webSocket) {
        webSockets.remove(webSocket);
    }

}
