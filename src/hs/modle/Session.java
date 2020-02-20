package hs.modle;

import io.netty.channel.ChannelHandlerContext;


/**
 * @author zzx
 * @version 1.0
 * @date 2020/2/20 15:41
 */
public class Session {
    String machineIp;
    ChannelHandlerContext ctx;
    boolean isHeartBeat;
    Integer maxBeatInterval;

    public String getMachineIp() {
        return machineIp;
    }

    public void setMachineIp(String machineIp) {
        this.machineIp = machineIp;
    }

    public ChannelHandlerContext getctx() {
        return ctx;
    }

    public void setctx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public boolean isHeartBeat() {
        return isHeartBeat;
    }

    public void setHeartBeat(boolean heartBeat) {
        isHeartBeat = heartBeat;
    }

    public Integer getMaxBeatInterval() {
        return maxBeatInterval;
    }

    public void setMaxBeatInterval(Integer maxBeatInterval) {
        this.maxBeatInterval = maxBeatInterval;
    }
}
