package com.nobug.service;

import com.nobug.entity.Message;

public interface MessageService {

    void receiveMsg (Message message);

    void sendMsg (Message message);
}
