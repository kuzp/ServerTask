package ru.ifmo.client;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

import java.util.Date;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Chat implements EntryPoint {

    private static final int REFRESH_INTERVAL = 2_000; //ms

    private VerticalPanel mainPanel = new VerticalPanel();
    private FlexTable postsTable = new FlexTable();
    private HorizontalPanel addPanel = new HorizontalPanel();
    private ScrollPanel scrolled = new ScrollPanel(postsTable);
    private TextArea messageBox = new TextArea();
    private Button addButton = new Button("Add");
    private Label lastUpdatedLabel = new Label();

    private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);


    public void onModuleLoad() {
        // Assemble Add Stock panel.
        addPanel.add(messageBox);
        addPanel.add(addButton);


        // Assemble Main panel.
        mainPanel.add(scrolled);
        mainPanel.add(addPanel);
        mainPanel.add(lastUpdatedLabel);

        postsTable.getColumnFormatter().addStyleName(0, "infoStyle");
        postsTable.getColumnFormatter().addStyleName(1, "messageStyle");

        RootPanel.get("chatContent").add(mainPanel);

        addButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                sendMessage();
            }
        });
        messageBox.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                if (event.isControlKeyDown() &&
                        (event.getNativeKeyCode() == KeyCodes.KEY_ENTER
                                || event.getNativeKeyCode() == KeyCodes.KEY_MAC_ENTER)) {
                    sendMessage();
                }
            }
        });

        messageBox.setFocus(true);
        messageBox.setCharacterWidth(100);
        postsTable.setWidth("700");
        scrolled.setSize("100", "400px");

        // Setup timer to refresh list automatically.
        Timer refreshTimer = new Timer() {
            @Override
            public void run() {
                justUpdate();
            }
        };
        refreshTimer.scheduleRepeating(REFRESH_INTERVAL);

    }

    private void sendMessage() {
        Message outgoing = new Message("me", messageBox.getText(), new Date());
        callRemote(outgoing);
        messageBox.setText("");
    }

    private void justUpdate(){
        callRemote(null);
    }

    private void callRemote(Message m){
        greetingService.greetServer(m, new AsyncCallback<Message[]>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Problem : " + caught.getMessage());
            }

            @Override
            public void onSuccess(Message[] result) {
                refreshMessages(result);
            }
        });
    }

    private void refreshMessages(Message[] msgs) {
        for (int i = 0; i < msgs.length; i++) {
            Message curr = msgs[i];
            postsTable.setWidget(i, 0, new HTML("Author : " + curr.getAutor() + "<br/>on " + curr.getPosted()));
            postsTable.setWidget(i, 1, new HTML(curr.getMsg().replace("\n", "<br/>")));
        }
    }
}

