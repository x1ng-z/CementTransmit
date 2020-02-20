package hs.modle;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author zzx
 * @version 1.0
 * @date 2020/2/18 9:46
 */

@Component()
public class MessageBus {
    public static Logger logger = Logger.getLogger(MessageBus.class);
    final LinkedBlockingQueue<Message> messagequeue=new LinkedBlockingQueue();

    Message getMessage() throws Exception{
        return messagequeue.take();
    }

    void putMessage(Message message){
        messagequeue.offer(message);
    }

    void selfinit(){
        Thread queueThread=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Message bus service setup");
                while (!Thread.currentThread().isInterrupted()){
                    try {
                        Message message=messagequeue.take();
                        System.out.println("Send To: "+message.getSendTo());
                        for(int i=0;i<message.getContext().length;++i) {
                            System.out.println("message context: "+i+""+message.getContext()[i]);
                        }
                    } catch (InterruptedException e) {
                        logger.error(e);
                    }
                }

            }
        });
        queueThread.setDaemon(true);
        queueThread.start();
    }
}
