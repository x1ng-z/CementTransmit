package hs.service;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@ChannelHandler.Sharable
@Component
public class MsgDecoder_Inbound extends ChannelInboundHandlerAdapter {
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MsgDecoder_Inbound.class);
    public MsgDecoder_Inbound() {
        super();
    }
//    @Autowired
//    public VehicleMapper vehicleMapper;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = ipSocket.getAddress().getHostAddress();
        Integer port=ipSocket.getPort();
        logger.info("come in"+clientIp+":"+port);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = ipSocket.getAddress().getHostAddress();
        Integer port=ipSocket.getPort();
        logger.info("come out"+clientIp+":"+port);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf wait_for_read = (ByteBuf) msg;
        String getinfo=null;
        getinfo = ByteBufUtil.hexDump(wait_for_read.readBytes(wait_for_read.readableBytes()));
        logger.info(getinfo);
        //ctx.fireChannelRead(msg); test

       /* String carno=Command.RECEIVE_CARDNO.analye(getinfo);
        if(carno!=null){
            Vehicle_info vehicle_info =vehicleMapper.find_vehicle_info(carno);
            if(vehicle_info==null){
                logger.warn("find null vehicle_info");
                vehicle_info=new Vehicle_info();
            }
            vehicle_info.setTermial(getinfo.substring(8,10));
            ctx.writeAndFlush(vehicle_info);
        }*/
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

}
