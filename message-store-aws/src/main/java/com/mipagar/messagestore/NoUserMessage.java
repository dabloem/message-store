/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mipagar.messagestore;

import java.util.Date;

/**
 *
 * @author Duncan Bloem <dabloem@gmail.com>
 */
public class NoUserMessage {
    
    private String id;
    private Date date;
    private Sender sender;
    private Recipient recipient;
    private String title;
    private String message;
    private boolean read;

    public NoUserMessage(Sender sender, Recipient recipient, String title, String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.title = title;
        this.message = message;
    }

    public NoUserMessage() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    @Override
    public String toString() {
        return "UserMessage{" + "id=" + id + ", sender=" + sender + ", recipient=" + recipient + ", message=" + message + ", read=" + read + '}';
    }
      
}
