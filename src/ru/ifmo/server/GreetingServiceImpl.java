package ru.ifmo.server;

import ru.ifmo.client.GreetingService;
import ru.ifmo.client.Message;
import ru.ifmo.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.ArrayList;
import java.util.Vector;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
        GreetingService {
    private Vector<Message> history = new Vector<>();

    public Message[] greetServer(Message newMsg) throws IllegalArgumentException {
        if (newMsg != null){
            history.add(newMsg);
        }
        return history.toArray(new Message[]{});
    }

    /**
     * Escape an html string. Escaping data received from the client helps to
     * prevent cross-site script vulnerabilities.
     *
     * @param html the html string to escape
     * @return the escaped string
     */
    private String escapeHtml(String html) {
        if (html == null) {
            return null;
        }
        return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
                ">", "&gt;");
    }
}
