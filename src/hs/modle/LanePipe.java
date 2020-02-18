package hs.modle;


/**
 * @author zzx
 * @version 1.0
 * @date 2020/2/17 0:10
 */
public class LanePipe {
    final DefaultLaneContext  head;
    final DefaultLaneContext tail;


    public LanePipe() {
        this.head = new DefaultLaneContext("head",null);
        this.tail = new DefaultLaneContext("tail",null);

        head.next=tail;
        head.prev=null;
        tail.prev=head;
        tail.next=null;
    }
    public void addLast(String name,CarLane carLane){
        synchronized (this) {
            addLast0(new DefaultLaneContext(name,carLane));
        }
    }

    private void addLast0(DefaultLaneContext newcxt){
        DefaultLaneContext point=(DefaultLaneContext)tail.prev;
        newcxt.prev=point;
        newcxt.next=tail;
        point.next=newcxt;
        tail.prev=newcxt;
    }


}
