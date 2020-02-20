package hs.service;

import hs.dao.Packer;
import hs.modle.*;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {
    private static final Logger logger=Logger.getLogger(SessionManager.class);
    private Map<String, Session> sessionIdMapByIP=new ConcurrentHashMap<>();

    private Map<String,PackMachine> packMachineMapByIp=new ConcurrentHashMap<>();

    private Packer packer;

    private PackMAchineGroup packMAchineGroup;

    private MessageBus messageBus;

    @Autowired
    public void setMessageBus(MessageBus messageBus) {
        this.messageBus = messageBus;
    }


    @Autowired
    public void setPackMAchineGroup(PackMAchineGroup packMAchineGroup) {
        this.packMAchineGroup = packMAchineGroup;
    }


    @Autowired
    public void setPacker(Packer packer) {
        this.packer = packer;
    }

    public  void selfinit(){
        for(PackerConfigure packerConfigure:packer.getPackerConfigure().values()){
            Session session=new Session();
            session.setHeartBeat(packerConfigure.isHeartbeat());
            session.setMachineIp(packerConfigure.getDeviceIp());
            session.setMaxBeatInterval(packerConfigure.getOffLineSecond());
            sessionIdMapByIP.put(packerConfigure.getDeviceIp(),session);

            packMachineMapByIp.put(packerConfigure.getDeviceIp(),packMAchineGroup.getProductLineMaps().get(packerConfigure.getProductLine()).getDeviceMaps().get(packerConfigure.getDeviceOrder()));

        }


        Thread queueThread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()){
                    try {
                        Message message=messageBus.getMessage();
                        logger.debug("Send To: "+message.getSendTo());
                        for(int i=0;i<message.getContext().length;++i) {
                            logger.debug("message context: ["+i+"] "+Integer.toString(message.getContext()[i]&0xff,16));
                        }
                        ChannelHandlerContext channelHandlerContext=findSessionByIp(message.getSendTo()).getctx();
                        if(channelHandlerContext!=null){
                            channelHandlerContext.write(message.getContext()); ;//fireChannelRead();
                        }
                    } catch (InterruptedException e) {
                        logger.error(e);
                    }catch (Exception e){
                        logger.error(e);
                    }
                }

            }
        });
        queueThread.setDaemon(true);
        queueThread.start();


    }


    public Map<String, PackMachine> getPackMachineMapByIp() {
        return packMachineMapByIp;
    }


    public boolean isValideDevice(String ip) {
        return sessionIdMapByIP.containsKey(ip);
    }


    public Session findSessionByIp(String ip) {
        return sessionIdMapByIP.get(ip);
    }


    public  void putHandleContext(String ip, ChannelHandlerContext cxt) {
        Session session=sessionIdMapByIP.get(ip);
        if(session.getctx()!=null){
            try {
                logger.warn("the device ip="+ip+" have already connect link");
                session.getctx().close();
                session.setctx(null);
            } catch (Exception e) {
                logger.error(e);
            }
        }
        session.setctx(cxt);
    }


    public  void removeHandleContext(String ip) {
        Session session=sessionIdMapByIP.get(ip);
        if(session.getctx()!=null){
            try {
                logger.warn("the device ip="+ip+" have already connect link");
                //session.getctx().close();
                session.setctx(null);
            } catch (Exception e) {
                logger.error(e);
            }
        }
        session.setctx(null);
    }


    public  void forceCLoseChanel(String ip) {
        Session session=sessionIdMapByIP.get(ip);
        if(session.getctx()!=null){
            try {

                session.getctx().close();
                session.setctx(null);
                return;
            } catch (Exception e) {
                logger.error(e);
            }
        }
        logger.warn("the device ip="+ip+" have already close connect link");
    }



    public List<Session> toList() {

        List<Session> list = new ArrayList<>();
        Set<Map.Entry<String, Session>> entrySet = this.sessionIdMapByIP.entrySet();

        for (Map.Entry<String, Session> entry : entrySet) {
            Session session = entry.getValue();
            list.add(session);
        }
        return list;
    }


    public Packer getPacker() {
        return packer;
    }

    public PackMAchineGroup getPackMAchineGroup() {
        return packMAchineGroup;
    }


    public MessageBus getMessageBus() {
        return messageBus;
    }

    public void setPackMachineMapByIp(Map<String, PackMachine> packMachineMapByIp) {
        this.packMachineMapByIp = packMachineMapByIp;
    }



}
