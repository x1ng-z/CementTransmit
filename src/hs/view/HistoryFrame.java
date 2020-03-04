package hs.view;


import hs.service.OrderOperateService;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.Math.round;

public class HistoryFrame extends JFrame {
    public static Logger logger = Logger.getLogger(HistoryFrame.class);
    double total_dun_d = 0;
    double total_bao_d = 0;
    private OrderOperateService orderOperateService;
    public HistoryFrame(Double sizecoeW, Double sizecoeH,OrderOperateService orderOperateService){
        this.orderOperateService=orderOperateService;
        setLayout(null);
        Font font14= new Font("宋体",Font.BOLD,(int)round(14*sizecoeW));
        Font font16= new Font("宋体",Font.BOLD,(int)round(16*sizecoeW));
        Font font18= new Font("宋体",Font.BOLD,(int)round(18*sizecoeW));
        Font font20= new Font("宋体",Font.BOLD,(int)round(20*sizecoeW));
        Border raisedBevelBorder =BorderFactory.createRaisedBevelBorder();
        Border etchedBorderL = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Border titleBorder =BorderFactory.createTitledBorder(etchedBorderL,"查询条件",TitledBorder.LEFT,TitledBorder.TOP,font18);

/*---------------top_Panel上层面板------------------*/
        JPanel top_Panel = new JPanel();
        top_Panel.setBounds((int) round(5 * sizecoeW), (int) round(5 * sizecoeH), (int) round(1530 * sizecoeW), (int) round(120 * sizecoeH));
        top_Panel.setBorder(titleBorder);
        top_Panel.setLayout(null);
        add(top_Panel);

        JPanel top_p1 = new JPanel();
        top_p1.setBounds((int) round(80 * sizecoeW), (int) round(15 * sizecoeH), (int) round(1400 * sizecoeW), (int) round(45 * sizecoeH));
        top_p1.setLayout(null);
        top_Panel.add(top_p1);

        /**包装机选择*/
        JCheckBox bag_No = new JCheckBox("包机号：");
        bag_No.setBounds((int) round(10 * sizecoeW), (int) round(5 * sizecoeH), (int) round(120 * sizecoeW), (int) round(40 * sizecoeH));
        bag_No.setFont(font18);
        top_p1.add(bag_No);
        java.util.List<String> tmp_packname=new ArrayList();
        tmp_packname.add("");
        //TODO
//        for(Pack_Machine pack_machine:Machine_Manger.getInstance().getPack_machines().values()){
//            tmp_packname.add(pack_machine.getPackage_machine_name());
//        }

        String[] bag_list=new String[0];
        bag_list=tmp_packname.toArray(bag_list);

        JComboBox<String> bag_No_box = new JComboBox<String>(bag_list);
        bag_No_box.setBounds((int) round(150 * sizecoeW), (int) round(10 * sizecoeH), (int) round(180 * sizecoeW), (int) round(35 * sizecoeH));
        bag_No_box.setFont(font14);
        top_p1.add(bag_No_box);

        JLabel start_Time = new JLabel("开始日期：",JLabel.CENTER);
        start_Time.setBounds((int) round(440 * sizecoeW), (int) round(5 * sizecoeH), (int) round(120 * sizecoeW), (int) round(40 * sizecoeH));
        start_Time.setFont(font18);
        top_p1.add(start_Time);

        JButton start_datebtn = new DateSelector();
        start_datebtn.setBounds((int)round(540*sizecoeW),(int)round(10*sizecoeH),(int)round(250*sizecoeW),(int)round(35*sizecoeH));
        start_datebtn.setFont(font14);
        start_datebtn.setBackground(new Color(139, 137, 137));
        start_datebtn.setForeground(Color.white);
        top_p1.add(start_datebtn);

        JButton findBtn = new JButton("查询");
        findBtn.setFont(font16);
        findBtn.setBorder(raisedBevelBorder);
        findBtn.setBackground(new Color(238,238,238));
        findBtn.setBounds((int)round(900*sizecoeW),(int)round(10*sizecoeH),(int)round(80*sizecoeW),(int)round(35*sizecoeH));
        top_p1.add(findBtn);

        JPanel top_p2 = new JPanel();
        top_p2.setBounds((int) round(80 * sizecoeW), (int) round(60 * sizecoeH), (int) round(1400 * sizecoeW), (int) round(45 * sizecoeH));
        top_p2.setLayout(null);
        top_Panel.add(top_p2);

        JCheckBox cement_Type = new JCheckBox("水泥品种：");
        cement_Type.setBounds((int) round(10 * sizecoeW), (int) round(5 * sizecoeH), (int) round(140 * sizecoeW), (int) round(50 * sizecoeH));
        cement_Type.setFont(font18);
        top_p2.add(cement_Type);

        /**品种选择*/
        String[] cement_list = new String[]{
                "","超丰P.O42.5包装", "超丰P.O42.5纸袋", "虎丰P.O42.5包装", "之江P.O42.5包装",
                "超丰P.C32.5R包装", "超丰P.C32.5R纸袋", "虎丰P.C32.5R包装", "之江P.C32.5R包装","虎丰M32.5包装"};

        JComboBox<String> cement_Type_box = new JComboBox<String>(cement_list);
        cement_Type_box.setBounds((int) round(150 * sizecoeW), (int) round(10 * sizecoeH), (int) round(180 * sizecoeW), (int) round(35 * sizecoeH));
        cement_Type_box.setFont(font14);
        top_p2.add(cement_Type_box);

        JLabel end_Time = new JLabel("结束日期：", JLabel.CENTER);
        end_Time.setBounds((int) round(440 * sizecoeW), (int) round(5 * sizecoeH), (int) round(120 * sizecoeW), (int) round(50 * sizecoeH));
        end_Time.setFont(font18);
        top_p2.add(end_Time);

        JCheckBox dun20_box = new JCheckBox("小于");
        dun20_box.setFont(font18);
        dun20_box.setBounds((int)round(900*sizecoeW),(int)round(10*sizecoeH),(int)round(80*sizecoeW),(int)round(35*sizecoeH));
        top_p2.add(dun20_box);

        JTextField number_box = new JTextField();
        number_box.setFont(font18);
        number_box.setText("20");
        number_box.setBounds((int)round(985*sizecoeW),(int)round(10*sizecoeH),(int)round(40*sizecoeW),(int)round(35*sizecoeH));
        top_p2.add(number_box);

        JLabel dun_L = new JLabel("吨");
        dun_L.setFont(font18);
        dun_L.setBounds((int)round(1025*sizecoeW),(int)round(10*sizecoeH),(int)round(40*sizecoeW),(int)round(35*sizecoeH));
        top_p2.add(dun_L);


        JButton end_datebtn = new DateSelector();
        end_datebtn.setBounds((int)round(540*sizecoeW),(int)round(10*sizecoeH),(int)round(250*sizecoeW),(int)round(35*sizecoeH));
        end_datebtn.setFont(font14);
        end_datebtn.setBackground(new Color(139, 137, 137));
        end_datebtn.setForeground(Color.white);
        top_p2.add(end_datebtn);

/*---------------center_Panel中间面板------------------*/
        JPanel center_Panel = new JPanel();
        center_Panel.setBounds((int) round(5 * sizecoeW), (int) round(125 * sizecoeH), (int) round(1530 * sizecoeW), (int) round(630 * sizecoeH));
        center_Panel.setBorder(etchedBorderL);
        center_Panel.setLayout(null);
        add(center_Panel);

        HistoryTable historyTable = new HistoryTable();
        JScrollPane history_Pane = historyTable.get_JScrollPane();
        history_Pane.setBounds((int) round(0 * sizecoeW), (int) round(5 * sizecoeH), (int) round(1530 * sizecoeW), (int) round(620 * sizecoeH));
        center_Panel.add(history_Pane);

/*---------------center_Panel下层内容------------------*/
        JPanel bottom_Panel = new JPanel();
        bottom_Panel.setBounds((int) round(5 * sizecoeW), (int) round(755 * sizecoeH), (int) round(1530 * sizecoeW), (int) round(40 * sizecoeH));
        bottom_Panel.setBorder(etchedBorderL);
        bottom_Panel.setLayout(null);
        add(bottom_Panel);




        String total_dun = String.valueOf(total_dun_d);
        String total_bao =  String.valueOf(total_bao_d);

        JLabel total_L = new JLabel("总计：");
        total_L.setFont(font16);
        total_L.setBounds((int) round(10 * sizecoeW), (int) round(5 * sizecoeH), (int) round(100 * sizecoeW), (int) round(30 * sizecoeH));
        bottom_Panel.add(total_L);

        JLabel total_dun_L = new JLabel(total_dun+" 吨");
        total_dun_L.setFont(font16);
        total_dun_L.setBounds((int) round(750 * sizecoeW), (int) round(5 * sizecoeH), (int) round(150 * sizecoeW), (int) round(30 * sizecoeH));
        bottom_Panel.add(total_dun_L);

        JLabel total_bao_L = new JLabel(total_bao+" 包");
        total_bao_L.setFont(font16);
        total_bao_L.setBounds((int) round(950 * sizecoeW), (int) round(5 * sizecoeH), (int) round(150 * sizecoeW), (int) round(30 * sizecoeH));
        bottom_Panel.add(total_bao_L);

        //todo 查询按钮
        findBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int loop=0;
                int row=historyTable.defaultModel.getRowCount();
                for(loop=0;loop<row;loop++){
                    historyTable.defaultModel.removeRow(0);
                }


                String bag_No_te = null;
                String cement_Type_te = null;
                String dun_te = null;

                if(bag_No.isSelected()){
                    bag_No_te = bag_No_box.getSelectedItem().toString();
                }
                if(cement_Type.isSelected()){
                    cement_Type_te = cement_Type_box.getSelectedItem().toString();
                }
                if(dun20_box.isSelected()){
                    dun_te = number_box.getText();
                }

                String start_date = start_datebtn.getText();
                String end_date = end_datebtn.getText();


                SimpleDateFormat table_format =new SimpleDateFormat("yyyy年MM月dd日 hh时mm分ss秒");
                SimpleDateFormat db_format =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date start_time=null;
                Date end_time=null;
                try {
                    start_time= table_format.parse(start_date);
                    end_time=table_format.parse(end_date);
                } catch (ParseException e1) {
                    logger.error(e1);
                }
                //TODO 历史订单查询

//                orderOperateService.findOrders();
//                java.util.List<Order> results=Pack_DB_Record.find_pack_history_order(bag_No_te,cement_Type_te,db_format.format(start_time),db_format.format(end_time),dun_te==null?null:Double.parseDouble(dun_te));
//                ZoneId systemDefault = ZoneId.systemDefault();
//                for(Order order:results){
//                    historyTable.defaultModel.insertRow(0, new Object[]{order.getVehicleno(),order.getMaterial(),order.getBillcode(),order.getPro_weight(),order.getTotal_amount(), LocalDateTime.ofInstant(order.getCreate_time(), systemDefault).toString()});
//                    //        defaultModel.insertRow();
//
//                }

                 total_dun_d = 0;
                 total_bao_d = 0;

                int his_rows = historyTable.defaultModel.getRowCount();
                for(int i=0;i<his_rows;i++){
                    String order_dun_s = historyTable.getValueAt(i,3).toString();
                    String order_bao_s = historyTable.getValueAt(i,4).toString();

                    double order_dun_d = Double.parseDouble(order_dun_s);
                    double order_bao_d = Double.parseDouble(order_bao_s);

                    total_dun_d += order_dun_d;
                    total_bao_d += order_bao_d;
                }

                total_dun_L.setText(total_dun_d+"吨");
                total_bao_L.setText(total_bao_d+"包");



            }
        });

    }
}
