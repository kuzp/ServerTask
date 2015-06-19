package ru.ifmo.client;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.Date;

/**
 * Created by Hascee on 19.06.2015.
 */
public class Message implements IsSerializable{
    private String autor;
    private String msg;
    private Date posted;

    public Message() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (autor != null ? !autor.equals(message.autor) : message.autor != null) return false;
        if (msg != null ? !msg.equals(message.msg) : message.msg != null) return false;
        return posted.equals(message.posted);

    }

    @Override
    public int hashCode() {
        int result = autor != null ? autor.hashCode() : 0;
        result = 31 * result + (msg != null ? msg.hashCode() : 0);
        result = 31 * result + posted.hashCode();
        return result;
    }

    public String getAutor() {
        return autor;
    }

    public String getMsg() {
        return msg;
    }

    public Date getPosted() {
        return posted;
    }

    public Message(String autor, String msg, Date posted) {
        this.autor = autor;
        this.msg = msg;
        this.posted = posted;
    }

}
