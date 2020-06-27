package hs.modle.order;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class PackManulOrder implements Order {

    /**
     *
     * 车牌 物料 吨位 磅单号 线别 水泥批次 班组 客户别名 客户号 最后一个pk
     * */
    private Integer id;
    private String vehicleno;
    private String material;
    private String billcode;
    private String batch_no;
    private String class_no;
    private String consumer_alias;
    private String consumer_code;
    private String pk_delivery;
    private Integer laneno_alias;
    private Integer laneno;
    private int already_amount;
    private int total_amount;
    private double pro_weight;
    private String cardno;

    private int productLineIndex;
    private int packMachineIndex;
    private int carLaneIndex;
    private String carlanecomment;

    private String carLaneHardCode;

    private Instant assign_time;
    private Instant create_time = Instant.now();


    @Override
    public String getPk_delivery() {
        return pk_delivery;
    }

    @Override
    public void setPk_delivery(String pk_delivery) {
        this.pk_delivery = pk_delivery;
    }



    public Instant getCreate_time() {
        return create_time;
    }


    public String getCreate_time_form_mmdd() {
        ZoneId systemDefault = ZoneId.systemDefault();
        LocalDateTime date=LocalDateTime.ofInstant(create_time,systemDefault);

        DateTimeFormatter pattern =
                DateTimeFormatter.ofPattern("MMdd");


        return pattern.format(date);
    }

    public void setCreate_time(Instant create_time) {
        this.create_time = create_time;
    }




    public String getVehicleno() {
        return vehicleno;
    }

    public void setVehicleno(String vehicleno) {
        this.vehicleno = vehicleno;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }



    public String getBillcode() {
        return billcode;
    }

    public void setBillcode(String billcode) {
        this.billcode = billcode;
    }


    public String getBatch_no() {
        return batch_no;
    }

    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }

    public String getClass_no() {
        return class_no;
    }

    public void setClass_no(String class_no) {
        this.class_no = class_no;
    }

    public String getConsumer_alias() {
        return consumer_alias;
    }

    public void setConsumer_alias(String consumer_alias) {
        this.consumer_alias = consumer_alias;
    }

    public String getConsumer_code() {
        return consumer_code;
    }

    public void setConsumer_code(String consumer_code) {
        this.consumer_code = consumer_code;
    }


    public Integer getLaneno_alias() {
        return laneno_alias;
    }

    public void setLaneno_alias(Integer laneno_alias) {
        this.laneno_alias = laneno_alias;
    }

    public Integer getLaneno() {
        return laneno;
    }

    public void setLaneno(Integer laneno) {
        this.laneno = laneno;
    }

    public int getAlready_amount() {
        return already_amount;
    }

    public void setAlready_amount(int already_amount) {
        this.already_amount = already_amount;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public Instant getAssign_time() {
        return assign_time;
    }

    public void setAssign_time(Instant assign_time) {
        this.assign_time = assign_time;
    }

    @Override
    public int compareTo(Object o) {
        Order autoOrder=(Order)o;

        if(assign_time.isAfter(autoOrder.getAssign_time())){
            return 1;
        }else if(assign_time.compareTo(autoOrder.getAssign_time())==0){
            return 0;
        }
        return -1;
    }

    @Override
    public double getPro_weight() {
        return pro_weight;
    }

    @Override
    public void setPro_weight(double pro_weight) {
        this.pro_weight = pro_weight;
    }

    @Override
    public void updateOrder(Order neworder) {
        setAlready_amount(neworder.getAlready_amount());
        setAssign_time(neworder.getAssign_time());
        setBatch_no(neworder.getBatch_no());
        setBillcode(neworder.getBillcode());
        setCardno(neworder.getCardno());
        setClass_no(neworder.getClass_no());
        setConsumer_alias(neworder.getConsumer_alias());
        setConsumer_code(neworder.getConsumer_code());
        setCreate_time(neworder.getCreate_time());
        setLaneno(neworder.getLaneno());
        setLaneno_alias(neworder.getLaneno_alias());
        setMaterial(neworder.getMaterial());
        setPk_delivery(neworder.getPk_delivery());
        setPro_weight(neworder.getPro_weight());
        setProductLineIndex(neworder.getProductLineIndex());
        setTotal_amount(neworder.getTotal_amount());
        setVehicleno(neworder.getVehicleno());
    }

    @Override
    public String getCardno() {
        return cardno;
    }

    @Override
    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public void setProductLineIndex(int productLineIndex) {
        this.productLineIndex = productLineIndex;
    }

    public int getPackMachineIndex() {
        return packMachineIndex;
    }

    public void setPackMachineIndex(int packMachineIndex) {
        this.packMachineIndex = packMachineIndex;
    }

    public int getCarLaneIndex() {
        return carLaneIndex;
    }

    public void setCarLaneIndex(int carLaneIndex) {
        this.carLaneIndex = carLaneIndex;
    }


    public int getProductLineIndex() {
        return productLineIndex;
    }

    public String getCarLaneHardCode() {
        return carLaneHardCode;
    }

    public void setCarLaneHardCode(String carLaneHardCode) {
        this.carLaneHardCode = carLaneHardCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarlanecomment() {
        return carlanecomment;
    }

    public void setCarlanecomment(String carlanecomment) {
        this.carlanecomment = carlanecomment;
    }
}
