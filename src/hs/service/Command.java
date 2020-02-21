package hs.service;

import hs.modle.order.PackManulOrder;
import hs.utils.CodeHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zzx
 * @version 1.0
 * @date 2020/2/16 15:25
 */
public enum Command{

    SEND_ORDER("12","00","01"){
        @Override
        public Map<String,String> analye(String context) {
            logger.error(name()+" command no spport");
            throw new UnsupportedOperationException(name()+" command no spport");
        }
        @Override
        public byte[] build(Object object) {
            return super.build0(object);
        }
    },
    SEND_REPLENISH("12","01","01"){
        @Override
        public  Map<String,String> analye(String context){
            logger.error(name()+" command no spport");
            throw new UnsupportedOperationException(name()+" command no spport");
        }

        @Override
        public byte[] build(Object object){
            return super.build0(object);
        }
    },

    SEND_PAUSE("12","00","02"){
        @Override
        public Map<String,String> analye(String context) {
            logger.error(name()+" command no spport");
            throw new UnsupportedOperationException(name()+" command no spport");

        }

        @Override
        public byte[] build(Object object) {
            return super.build0(object);
        }
    },

    RECEIVE_KEYSURE("07",null,null){
        @Override
        public Map<String,String> analye(String context) {
            return null;
        }

        @Override
        public byte[] build(Object object) {
            logger.error(name()+" command no spport");
            throw new UnsupportedOperationException(name()+" command no spport");
        }
    },

    RECEIVE_WEIGHT("04",null,null){
        @Override
        public Map<String,String> analye(String context) {
            logger.info(context);
            Matcher matcher1 = Pattern.compile("(.*a5(.*)015a$)").matcher(context);
            if(matcher1.find()){
                Map<String,String> result=new HashMap<>();
                result.put("laneHardCode",context.substring(10,12));
                result.put("alreadLoad",CodeHelper.ASCIIToConvertString(context.substring(16,24)));
                return result;
            }else {
                return null;
            }
        }

        @Override
        public byte[] build(Object object) {
            logger.error(name()+" command no spport");
            throw new UnsupportedOperationException(name()+" command no spport");
        }
    },

    RECEIVE_COMPLE("06",null,null){
        @Override
        public Map<String,String> analye(String context) {
            return null;
        }

        @Override
        public byte[] build(Object object) {
            logger.error(name()+" command no spport");
            throw new UnsupportedOperationException(name()+" command no spport");
        }
    },

    RECEIVE_ACKNOWLEDGE("08",null,null){
        @Override
        public Map<String,String> analye(String context) {
            return null;
        }

        @Override
        public byte[] build(Object object) {
            logger.error(name()+" command no spport");
            throw new UnsupportedOperationException(name()+" command no spport");
        }
    },

    RECEIVE_NOORDER("09",null,null){
        @Override
        public Map<String,String> analye(String context) {
            return null;
        }

        @Override
        public byte[] build(Object object) {
            logger.error(name()+" command no spport");
            throw new UnsupportedOperationException(name()+" command no spport");
        }
    },

    RECEIVE_HEARTBEAT("15",null,null){
        @Override
        public Map<String,String> analye(String context) {
            return null;
        }

        @Override
        public byte[] build(Object object) {
            logger.error(name()+" command no spport");
            throw new UnsupportedOperationException(name()+" command no spport");
        }
    },
    RECEIVE_RADIATIONWEIGHT("41",null,null){
        @Override
        public Map<String,String> analye(String context) {
            return null;
        }

        @Override
        public byte[] build(Object object) {
            logger.error(name()+" command no spport");
            throw new UnsupportedOperationException(name()+" command no spport");
        }
    };

    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Command.class);
    private String main_command;
    private String sub_command1;
    private String sub_command2;
    private Command(String main_command, String sub_command1,String sub_command2) {
        this.main_command = main_command;
        this.sub_command1 = sub_command1;
        this.sub_command2=sub_command2;
    }
    /**Function description: analyze receive msg
     * @param context msg
     * @return cardno
     * */
    abstract public java.util.Map<String,String> analye(String context);


    private String analye0(String context){

        System.out.println(context.substring(8,10));
        String findcard=context.substring(10);
        System.out.println(findcard);
        Matcher matcher1 = Pattern.compile("(.*a5(.*)5a$)").matcher(findcard);
        if(matcher1.find()){
            String primercard=(matcher1.group().substring(8,16));
            StringBuilder cardbuild=new StringBuilder();
            for(int loop=0;loop<4;++loop){
                cardbuild.append(primercard.substring(6-2*loop,8-2*loop));
            }
            return Long.valueOf(cardbuild.toString(),16).toString();
        }
        return null;
    }

    /**
     * Function description: build send byte msg
     * @param object msg bean
     * @return byte[] msg byte array
     * */
    abstract public byte[] build(Object object);

    private byte[] build0(Object object){
        if(object instanceof PackManulOrder){
            PackManulOrder orderinfo=(PackManulOrder) object;
            java.util.List<Byte> tmp_buf=new ArrayList<Byte>();

            /**
             * protocol_header
             * */
            tmp_buf.add(Integer.valueOf(0x88).byteValue());
            tmp_buf.add(Integer.valueOf(0x10).byteValue());
            tmp_buf.add(Integer.valueOf(0x40).byteValue());
            tmp_buf.add(Integer.valueOf(0x10).byteValue());
            /**
             * main_command
             * */
            tmp_buf.add(Byte.parseByte(getMain_command(),16));
            /**
             *terminal
             * */
            tmp_buf.add(Byte.parseByte(orderinfo.getCarLaneHardCode(),16));
            /**
             *start
             * */
            tmp_buf.add(Integer.valueOf(0xa5).byteValue());
            /**
             *sub_command_isrepair
             * */
            tmp_buf.add(Integer.valueOf(getSub_command1()).byteValue());
            /**
             *content_length
             * */
            tmp_buf.add(Integer.valueOf(0x00).byteValue());
            /**
             * sub_command_load_pause
             * */
            tmp_buf.add(Byte.parseByte(getSub_command2(),16));
            /**
             *vehicle
             * */
            byte[] vehicleByetype=CodeHelper.GB2313ToByte(orderinfo.getVehicleno());
            if(vehicleByetype!=null){
                for(int i=0;i<10;i++){
                    if(i<vehicleByetype.length){
                        tmp_buf.add(vehicleByetype[i]);
                    }else {
                        tmp_buf.add(Byte.parseByte("0",16));
                    }

                }
            }


            /**
             *meterial
             * */
            byte[] vmeterial = CodeHelper.material_GB2313ToByte(orderinfo.getMaterial());

            for (int i = 0; i < 20; ++i) {
                if (i < vmeterial.length) {
                    tmp_buf.add(vmeterial[i]);
                } else {
                    tmp_buf.add(Byte.parseByte("0",16));
                }
            }

            /**
             * pre_load
             * */
            tmp_buf.addAll(CodeHelper.StringConvertToASCII(Integer.valueOf(Double.valueOf(orderinfo.getTotal_amount()).intValue()).toString(), 5));
            /**
             * code
             * */
            String code=orderinfo.getConsumer_code() + orderinfo.getCreate_time_form_mmdd() + "0"+orderinfo.getClass_no() + orderinfo.getProductLineIndex() + orderinfo.getBatch_no();
            tmp_buf.addAll(CodeHelper.StringConvertToASCII(code,30));

            /**
             *max_load
             * */
            tmp_buf.addAll(CodeHelper.StringConvertToASCII("0",5));

            /**
             *already_load
             * */
            tmp_buf.addAll(CodeHelper.StringConvertToASCII(Integer.valueOf(orderinfo.getAlready_amount()).toString(),5));
            /**
             * reserved
             * */
            for(int i=0;i<11;i++){
                tmp_buf.add(Byte.parseByte("0",16));
            }
            /**
             *crc
             * */
            for(int i=0;i<2;i++){
                tmp_buf.add(Byte.parseByte("0",16));
            }
            /**
             *end[0] 主机标识符 00厂区收发或系统，01本软件
             * */
            tmp_buf.add(Byte.parseByte("01",16));
            tmp_buf.add(Byte.parseByte("5a",16));

            byte[] result=new byte[tmp_buf.size()];
            for(int i=0;i<tmp_buf.size();++i){
                result[i]=tmp_buf.get(i);
            }
            return result;
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString()+main_command;
    }

    public String getMain_command() {
        return main_command;
    }

    public String getSub_command1() {
        return sub_command1;
    }

    public String getSub_command2() {
        return sub_command2;
    }



}
