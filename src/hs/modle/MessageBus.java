package hs.modle;


import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author zzx
 * @version 1.0
 * @date 2020/2/18 9:46
 */

@Component
public class MessageBus {
    LinkedBlockingQueue<Message> messagequeue=new LinkedBlockingQueue();

    Message getMessage() throws Exception{
        return messagequeue.take();
    }

    void putMessage(Message message){
        messagequeue.offer(message);
    }
}
