package hs.modle;


import hs.service.SessionManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author zzx
 * @version 1.0
 * @date 2020/2/18 9:46
 */

@Component("messageBus")
public class MessageBus {
    public static Logger logger = Logger.getLogger(MessageBus.class);
    final private LinkedBlockingQueue<Message> messagequeue=new LinkedBlockingQueue();

    public Message getMessage() throws Exception{
        return messagequeue.take();
    }

    public void putMessage(Message message){
        messagequeue.offer(message);
    }

}
