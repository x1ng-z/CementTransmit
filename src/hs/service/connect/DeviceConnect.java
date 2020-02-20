package hs.service.connect;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zzx
 * @version 1.0
 * @date 2020/2/10 8:53
 */
public class DeviceConnect implements Runnable{

    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DeviceConnect.class);
    private EventLoopGroup bossGroup =null;
    private EventLoopGroup workerGroup =null;
    //private Channel channel=null;
    private int port;
    @Autowired
    ChannelInitializer nettyServerInitializer;
    public DeviceConnect(int port) {
        this.port = port;
    }
    @Override
    public void run() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(nettyServerInitializer)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            /*Bind and start to accept incoming connections.*/
            ChannelFuture f = b.bind(port).sync(); // (7)
            /*Wait until the server socket is closed.
            In this example, this does not happen, but you can do that to gracefully
            shut down your server.*/
            f.channel().closeFuture().sync();
            //channel = f.channel();
        } catch (Exception e){
            logger.error(e);
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public void destory() {
        logger.warn("destroy server resources");
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
