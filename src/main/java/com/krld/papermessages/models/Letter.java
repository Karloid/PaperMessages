package com.krld.papermessages.models;

import java.util.ArrayList;
import java.util.List;

public class Letter {
    public List<Message> messages;

    public static Letter create(Message msg) {
        Letter letter = new Letter();
        letter.messages = new ArrayList<>(1);
        letter.messages.add(msg);
        return letter;
    }
}
