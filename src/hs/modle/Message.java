package hs.modle;

/**
 * @author zzx
 * @version 1.0
 * @date 2020/2/18 9:58
 */
public class Message {
    private String sendTo;
    private byte[] context;

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public byte[] getContext() {
        return context;
    }

    public void setContext(byte[] context) {
        this.context = context;
    }
}
