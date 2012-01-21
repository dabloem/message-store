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
public interface NoMessageStore {

    List<NoUserMessage> fetch(Recipient recipient);

    boolean add(NoUserMessage userMessage);

    boolean read(NoUserMessage userMessage);

    boolean remove(NoUserMessage userMessage);
    
}
