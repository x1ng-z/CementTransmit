package hs.service.test;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Component
@ChannelHandler.Sharable
public class Test_Msg_encoder_Outbound extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println((String)"bbbb");

        System.out.println("EchoServerOutHandler   write "+((ByteBuf)msg).toString(Charset.defaultCharset()));
        ctx.writeAndFlush(msg);//write(ctx, msg, promise);
        //flush(ctx);
//        ctx.write(msg, promise);

        /*
        *  UnixTime m = (UnixTime) msg;
        ByteBuf encoded = ctx.alloc().buffer(4);
        encoded.writeInt((int)m.value());
        ctx.write(encoded, promise); // (1)
        *
        * */
    }
}
