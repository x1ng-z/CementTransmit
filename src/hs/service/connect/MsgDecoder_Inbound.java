package hs.service.connect;

import hs.service.Command;
import hs.service.SessionManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@ChannelHandler.Sharable
@Component
public class MsgDecoder_Inbound extends ChannelInboundHandlerAdapter {
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MsgDecoder_Inbound.class);
    public MsgDecoder_Inbound() {
        super();
    }

    private SessionManager sessionManager;

    @Autowired
    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
//    @Autowired
//    public VehicleMapper vehicleMapper;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = ipSocket.getAddress().getHostAddress();
        Integer port=ipSocket.getPort();
        logger.info("come in "+clientIp+":"+port);
        if (sessionManager.isValideDevice(clientIp)){
            sessionManager.putHandleContext(clientIp,ctx);
            sessionManager.getPackMachineMapByIp().get(clientIp).setDevicdConnect(true);
        }else {
            ctx.close();
        }

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = ipSocket.getAddress().getHostAddress();
        Integer port=ipSocket.getPort();
        logger.info("come out"+clientIp+":"+port);
        if(sessionManager.isValideDevice(clientIp)){
            sessionManager.getPackMachineMapByIp().get(clientIp).setDevicdConnect(false);
            sessionManager.removeHandleContext(clientIp);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf wait_for_read = (ByteBuf) msg;
        String getinfo=null;
        getinfo = ByteBufUtil.hexDump(wait_for_read.readBytes(wait_for_read.readableBytes()));
        switch (getinfo.substring(8,10)){
            case "06":
                Command.RECEIVE_COMPLE.analye(getinfo);
                break;
            case "07":
                Command.RECEIVE_KEYSURE.analye(getinfo);
                break;
            case "04":
                Command.RECEIVE_WEIGHT.analye(getinfo);
                //TODO sessionManager设置当前包装机的正在进行订单
                break;
            case "08":
                Command.RECEIVE_ACKNOWLEDGE.analye(getinfo);
                break;
            case "15":
                Command.RECEIVE_HEARTBEAT.analye(getinfo);
                break;
            case "09":
                Command.RECEIVE_NOORDER.analye(getinfo);
                break;
            case "41":
                Command.RECEIVE_RADIATIONWEIGHT.analye(getinfo);
                break;
            default:
                logger.error("command ="+getinfo.substring(8,10));
                break;

        }
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        logger.error(cause);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent) evt;

            InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
            String clientIp = ipSocket.getAddress().getHostAddress();
            IdleStateEvent stateEvent = (IdleStateEvent) evt;

            switch (stateEvent.state()) {
                case READER_IDLE:
                    logger.info(clientIp + "Read Idle");
                    break;
                case WRITER_IDLE:
                    logger.info(clientIp + "Read Idle");
                    break;
                case ALL_IDLE:
                    logger.info(clientIp + "Read Idle");
                    break;
                default:
                    break;
            }
        }
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

}
