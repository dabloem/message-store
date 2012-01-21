/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mipagar.messagestore;

import java.util.List;

/**
 *
 * @author Duncan Bloem <dabloem@gmail.com>
 */
public interface MessageStore {

    UserMessage add(UserMessage message);

    UserMessage archive(String messsageId);

    List<UserMessage> fetch(Recipient recipient);

    UserMessage get(String id);

    void read(String messsageId);

    void remove(UserMessage message);

    void update(UserMessage message);
    
}
