package hs.modle;

import hs.modle.order.Order;

/**
 * @author zzx
 * @version 1.0
 * @date 2020/2/17 15:25
 */
public class Location {
    private int productionLine;
    private int packmachineIndex;
    private int carLaneIndex;
    private Order value;

    public int getProductionLine() {
        return productionLine;
    }

    public void setProductionLine(int productionLine) {
        this.productionLine = productionLine;
    }

    public int getPackmachineIndex() {
        return packmachineIndex;
    }

    public void setPackmachineIndex(int packmachineIndex) {
        this.packmachineIndex = packmachineIndex;
    }

    public int getCarLaneIndex() {
        return carLaneIndex;
    }

    public void setCarLaneIndex(int carLaneIndex) {
        this.carLaneIndex = carLaneIndex;
    }

    public Order getValue() {
        return value;
    }

    public void setValue(Order value) {
        this.value = value;
    }
}
