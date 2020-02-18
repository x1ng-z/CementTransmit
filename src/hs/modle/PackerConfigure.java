package hs.modle;

import java.util.List;

/**
 * @author zzx
 * @version 1.0
 * @date 2020/2/13 12:24
 */
public class PackerConfigure {
    int id;
    int deviceOrder;
    int productLine;
    String deviceIp;
    boolean isHeartbeat;
    int offLineSecond;
    String commentZh;
    String companyName;
    java.util.List<CarLane> carLanes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public List<CarLane> getCarLanes() {
        return carLanes;
    }

    public void setCarLanes(List<CarLane> carLanes) {
        this.carLanes = carLanes;
    }



    public int getDeviceOrder() {
        return deviceOrder;
    }

    public void setDeviceOrder(int deviceOrder) {
        this.deviceOrder = deviceOrder;
    }

    public int getProductLine() {
        return productLine;
    }

    public void setProductLine(int productLine) {
        this.productLine = productLine;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    public boolean isHeartbeat() {
        return isHeartbeat;
    }

    public void setHeartbeat(boolean heartbeat) {
        isHeartbeat = heartbeat;
    }

    public int getOffLineSecond() {
        return offLineSecond;
    }

    public void setOffLineSecond(int offLineSecond) {
        this.offLineSecond = offLineSecond;
    }

    public String getCommentZh() {
        return commentZh;
    }

    public void setCommentZh(String commentZh) {
        this.commentZh = commentZh;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
