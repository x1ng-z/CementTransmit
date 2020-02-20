package hs.modle.order;

import java.time.Instant;

public interface Order extends Comparable {

    public String getVehicleno();

    public void setVehicleno(String vehicleno);

    public String getMaterial();

    public void setMaterial(String material);

    public String getBillcode();

    public void setBillcode(String billcode);



    public void setProductLineIndex(int productLineIndex);

    public int getPackMachineIndex() ;

    public void setPackMachineIndex(int packMachineIndex);

    public int getCarLaneIndex();

    public void setCarLaneIndex(int carLaneIndex);

    public int getProductLineIndex();


    public String getBatch_no();

    public void setBatch_no(String batch_no);
    public String getClass_no();

    public void setClass_no(String class_no);

    public String getConsumer_alias();

    public void setConsumer_alias(String consumer_alias);

    public String getConsumer_code();

    public void setConsumer_code(String consumer_code);

    public String getPk_delivery();

    public void setPk_delivery(String pk_delivery);



    public Instant getCreate_time();

    public String getCreate_time_form_mmdd() ;

    public void setCreate_time(Instant create_time);


    public int getAlready_amount() ;

    public void setAlready_amount(int already_amount) ;

    public int getTotal_amount() ;

    public void setTotal_amount(int total_amount) ;


    public Integer getLaneno();

    public void setLaneno(Integer laneno);

    public Integer getLaneno_alias();

    public void setLaneno_alias(Integer laneno_alias);

    public Instant getAssign_time() ;
    public void setAssign_time(Instant assign_time);

    public String getCardno();

    public void setCardno(String cardno);

    public double getPro_weight() ;

    public void setPro_weight(double pro_weight) ;

    void updateOrder(Order neworder);


    public String getCarLaneHardCode() ;

    public void setCarLaneHardCode(String carLaneHardCode) ;



}
