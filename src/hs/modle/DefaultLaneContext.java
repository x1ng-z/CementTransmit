package hs.modle;

/**
 * @author zzx
 * @version 1.0
 * @date 2020/2/17 0:16
 */
public class DefaultLaneContext implements LaneContext {
    volatile DefaultLaneContext next;
    volatile DefaultLaneContext prev;
    private String name;
    private CarLane carLane;

    @Override
    public CarLane getLane() {
        return carLane;
    }

    public DefaultLaneContext(String name, CarLane carLane) {
        this.name = name;
        this.carLane=carLane;
    }


    public String getName() {
        return name;
    }
}
