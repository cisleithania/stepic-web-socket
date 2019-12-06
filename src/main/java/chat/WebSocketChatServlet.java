package chat;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "WebSocketChatServlet", urlPatterns = {"/chat"})
public class WebSocketChatServlet extends WebSocketServlet{
    private final static int LOGOUT_TIME = 10 * 60 * 1000;
    private final ChatService chatService;

    public WebSocketChatServlet() {
        this.chatService = new ChatService();
    }

    // WebSocketServletFactory производящая сокеты
    @Override
    public void configure(WebSocketServletFactory factory) {
        // через какое время прекратить работу с пользователем, если он не производит действий
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        // при установлении обращения со стороны пользователя, создается сокет ChatWebSocket
        factory.setCreator((req, resp) -> new ChatWebSocket(chatService));
    }



}
