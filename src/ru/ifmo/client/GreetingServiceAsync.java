package ru.ifmo.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
  void greetServer(Message newMes, AsyncCallback<Message[]> callback)
      throws IllegalArgumentException;
}
