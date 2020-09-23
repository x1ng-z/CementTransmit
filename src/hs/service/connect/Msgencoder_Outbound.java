package hs.service.connect;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.util.ReferenceCountUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@ChannelHandler.Sharable
@Component
public class Msgencoder_Outbound extends ChannelOutboundHandlerAdapter {
    private Logger logger = Logger.getLogger(Msgencoder_Outbound.class);
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ByteBuf buf =ctx.alloc().buffer(((byte[])msg).length);
        buf.writeBytes((byte[])msg);
        //logger.info("outcontext: "+msg);
        byte[] msgs =(byte[])msg;
        for(byte m:msgs){
            System.out.print(Integer.toHexString(m&0xff).length()==2?Integer.toHexString(m&0xff):"0"+Integer.toHexString(m&0xff));
        }
        ctx.writeAndFlush(buf).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    logger.debug("send success");
                } else {
                    logger.debug("send failed");

                }
//                ReferenceCountUtil.release(msg);
            }
        });
    }
}
