package hs.service.connect;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@ChannelHandler.Sharable
@Component
public class Msgencoder_Outbound extends ChannelOutboundHandlerAdapter {
    private Logger logger = Logger.getLogger(Msgencoder_Outbound.class);
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
//        logger.info(((Vehicle_info)msg).getVehicleno());
//        Vehicle_info vehicle_info=(Vehicle_info) msg;
        byte[] tmpSendBuf=new byte[]{0x00,0x01,0x02};//Command.SEND_ALLOW.build(vehicle_info);
        ByteBuf buf =ctx.alloc().buffer().writeBytes(tmpSendBuf);
        ctx.writeAndFlush(buf).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    logger.info("回写成功");
                } else {
                    logger.error("回写失败");
                }
            }
        });
    }
}
