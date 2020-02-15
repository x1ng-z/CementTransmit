package hs.service.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class Test_decoder_Inbound extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //super.channelRead(ctx, msg);
        System.out.println("2222");
        ByteBuf wait_for_read = (ByteBuf) msg;
        String getinfo=null;
        getinfo = ByteBufUtil.hexDump(wait_for_read.readBytes(wait_for_read.readableBytes()));
        //logger.info(getinfo);
        //ctx.write(getinfo);
//        ByteBuf buffer= ctx.channel().alloc().buffer();
//        byte a[] =new byte[5];
//        a[0]=0x30;
//        a[1]=0x31;
//        a[2]=0x31;
//        a[3]=0x31;
//        a[4]=0x00;
//        buffer.writeBytes(a);
//        ctx.channel().writeAndFlush(buffer);
        ctx.writeAndFlush(Unpooled.copiedBuffer("channelRead Netty rocks!", CharsetUtil.UTF_8));
        ctx.fireChannelRead(msg);
//        Channel channel = ctx.channel();
//        channel.writeAndFlush(Unpooled.copiedBuffer("netty in action", CharsetUtil.UTF_8));
//        channel.flush();

    }
}
