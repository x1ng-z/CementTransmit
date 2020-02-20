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
        ByteBuf buf =ctx.alloc().buffer().writeBytes((byte[])msg);
        ctx.writeAndFlush(buf).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    logger.debug("回写成功");
                } else {
                    logger.debug("回写失败");

                }
                ReferenceCountUtil.release(msg);
            }
        });
    }
}
