package hs.service.connect;

import hs.modle.CarLane;
import hs.modle.PackMachine;
import hs.service.Command;
import hs.service.SessionManager;
import hs.view.MainFrame;
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
import java.util.Map;

@ChannelHandler.Sharable
@Component
public class MsgDecoder_Inbound extends ChannelInboundHandlerAdapter {
    private MainFrame mainFrame;

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
        Integer port = ipSocket.getPort();
        logger.info("come in " + clientIp + ":" + port);
        if (sessionManager.isValideDevice(clientIp)) {
            sessionManager.putHandleContext(clientIp, ctx);
            sessionManager.getPackMachineMapByIp().get(clientIp).setDevicdConnect(true);
        } else {
            ctx.close();
        }

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = ipSocket.getAddress().getHostAddress();
        Integer port = ipSocket.getPort();
        logger.info("come out" + clientIp + ":" + port);
        if (sessionManager.isValideDevice(clientIp)) {
            sessionManager.getPackMachineMapByIp().get(clientIp).setDevicdConnect(false);
            sessionManager.removeHandleContext(clientIp);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = ipSocket.getAddress().getHostAddress();
        ByteBuf wait_for_read = (ByteBuf) msg;
        String getinfo = null;
        Map<String, String> results = null;
        getinfo = ByteBufUtil.hexDump(wait_for_read.readBytes(wait_for_read.readableBytes()));
        logger.info(getinfo);
        switch (getinfo.substring(8, 10)) {
            case "06":
                results = Command.RECEIVE_COMPLE.analye(getinfo);
                //移除订单
                if (results != null) {
                    PackMachine packMachine = sessionManager.getPackMachineMapByIp().get(clientIp);
                    for (CarLane carLane : packMachine.getPackerConfigure().getCarLanes()) {
                        if (Integer.parseInt(carLane.getHardCode(), 16) == (Integer.parseInt(results.get("laneHardCode"), 16))) {
                            if (carLane.getWaitExecuteOfOrders().size() != 0) {
                                /**
                                 * 1、设置当前包数
                                 * 2、重置包装机当前订单
                                 * 3、删除车道订单
                                 * 4、刷新分配列表
                                 * */
                                carLane.getWaitExecuteOfOrders().get(0).setAlready_amount(Integer.valueOf(results.get("alreadLoad").trim()));
                                packMachine.setCurrentExecuteOrder(null);
                                carLane.deleteOrderByIndex(0);
                                mainFrame.flushAssignOrderTable();

                            }
                        }
                    }

                }
                break;
            case "07":
                Command.RECEIVE_KEYSURE.analye(getinfo);
                break;
            case "04"://881840100405a50c3337000000000000015a
                results = Command.RECEIVE_WEIGHT.analye(getinfo);
                if (results != null) {
                    PackMachine packMachine = sessionManager.getPackMachineMapByIp().get(clientIp);
                    for (CarLane carLane : packMachine.getPackerConfigure().getCarLanes()) {
                        if (Integer.parseInt(carLane.getHardCode(), 16) == (Integer.parseInt(results.get("laneHardCode"), 16))) {
                            if (carLane.getWaitExecuteOfOrders().size() != 0) {
                                carLane.getWaitExecuteOfOrders().get(0).setAlready_amount(Integer.valueOf(results.get("alreadLoad").trim()));
                                packMachine.setCurrentExecuteOrder(carLane.getWaitExecuteOfOrders().get(0));
                            }
                        }
                    }

                }
                break;
            case "08":
                results = Command.RECEIVE_ACKNOWLEDGE.analye(getinfo);
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
                logger.error("command =" + getinfo.substring(8, 10));
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

    @Autowired
    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

}
