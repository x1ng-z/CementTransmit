package hs.view;


import hs.modle.MaterialName;
import hs.modle.order.Order;
import hs.service.OrderOperateService;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
        Border titleBorder =BorderFactory.createTitledBorder(etchedBorderL,"查询条件",TitledBorder.LEFT,TitledBorder.TOP,font16);

/*---------------top_Panel上层面板------------------*/
        JPanel top_Panel = new JPanel();
        top_Panel.setBounds((int) round(5 * sizecoeW), (int) round(5 * sizecoeH), (int) round(1530 * sizecoeW), (int) round(120 * sizecoeH));
        top_Panel.setBorder(titleBorder);
        top_Panel.setLayout(null);
        add(top_Panel);

        JPanel top_p1 = new JPanel();
        top_p1.setBounds((int) round(10 * sizecoeW), (int) round(20 * sizecoeH), (int) round(1400 * sizecoeW), (int) round(45 * sizecoeH));
        top_p1.setLayout(null);
        top_Panel.add(top_p1);

        /**产线选择*/
        JCheckBox line_No = new JCheckBox("生产线：");
        line_No.setBounds((int) round(20 * sizecoeW), (int) round(5 * sizecoeH), (int) round(120 * sizecoeW), (int) round(40 * sizecoeH));
        line_No.setFont(font18);
        top_p1.add(line_No);

        Set<Integer> plIndexs=orderOperateService.getPackMAchineGroup().getProductLineMaps().keySet();
        String[] line_list =plIndexs.size()==0?null:new String[plIndexs.size()];
        int pliterm=0;
        for(Integer plindex:plIndexs){
            line_list[pliterm++]=plindex.toString();
        }
        JComboBox<String> line_No_box = new JComboBox<String>(line_list);
        line_No_box.setBounds((int) round(140 * sizecoeW), (int) round(10 * sizecoeH), (int) round(150 * sizecoeW), (int) round(35 * sizecoeH));
        line_No_box.setFont(font14);
        top_p1.add(line_No_box);

        /**包装机选择*/
        Set<Integer> allpackindex=orderOperateService.getAllPackmachineOrder();
        String[] tmp_packname= allpackindex.size()==0?null:new String[allpackindex.size()];
        int iterpack=0;
        for(Integer packIndex:allpackindex){
            tmp_packname[iterpack++]=packIndex.toString();
        }
        JCheckBox bag_No = new JCheckBox("包机号：");
        bag_No.setBounds((int) round(320 * sizecoeW), (int) round(5 * sizecoeH), (int) round(120 * sizecoeW), (int) round(40 * sizecoeH));
        bag_No.setFont(font18);
        top_p1.add(bag_No);
        JComboBox<String> bag_No_box = new JComboBox<String>(tmp_packname);
        bag_No_box.setBounds((int) round(440 * sizecoeW), (int) round(10 * sizecoeH), (int) round(150 * sizecoeW), (int) round(35 * sizecoeH));
        bag_No_box.setFont(font14);
        top_p1.add(bag_No_box);

        JLabel start_Time = new JLabel("开始日期：",JLabel.CENTER);
        start_Time.setBounds((int) round(620 * sizecoeW), (int) round(5 * sizecoeH), (int) round(120 * sizecoeW), (int) round(40 * sizecoeH));
        start_Time.setFont(font18);
        top_p1.add(start_Time);

        JButton start_datebtn = new DateSelector();
        start_datebtn.setBounds((int)round(740*sizecoeW),(int)round(10*sizecoeH),(int)round(250*sizecoeW),(int)round(35*sizecoeH));
        start_datebtn.setFont(font14);
        start_datebtn.setBackground(new Color(139, 137, 137));
        start_datebtn.setForeground(Color.white);
        top_p1.add(start_datebtn);

        JButton findBtn = new JButton("查询");
        findBtn.setFont(font16);
        findBtn.setBorder(raisedBevelBorder);
        findBtn.setBackground(new Color(238,238,238));
        findBtn.setBounds((int)round(1020*sizecoeW),(int)round(10*sizecoeH),(int)round(80*sizecoeW),(int)round(35*sizecoeH));
        top_p1.add(findBtn);

        JPanel top_p2 = new JPanel();
        top_p2.setBounds((int) round(10 * sizecoeW), (int) round(65 * sizecoeH), (int) round(1400 * sizecoeW), (int) round(45 * sizecoeH));
        top_p2.setLayout(null);
        top_Panel.add(top_p2);

        JCheckBox cement_Type = new JCheckBox("水泥品种：");
        cement_Type.setBounds((int) round(320 * sizecoeW), (int) round(5 * sizecoeH), (int) round(120 * sizecoeW), (int) round(50 * sizecoeH));
        cement_Type.setFont(font18);
        top_p2.add(cement_Type);

        /**品种选择*/

        List<MaterialName> materialNames=orderOperateService.getAllMaterialNames();
        String[] cement_list = materialNames.size()==0?null:new String[materialNames.size()];

        for(int i=0;i<materialNames.size();++i){
            cement_list[i]=materialNames.get(i).getMaterialName();
        }

        JComboBox<String> cement_Type_box = new JComboBox<String>(cement_list);
        cement_Type_box.setBounds((int) round(440 * sizecoeW), (int) round(10 * sizecoeH), (int) round(150 * sizecoeW), (int) round(35 * sizecoeH));
        cement_Type_box.setFont(font14);
        top_p2.add(cement_Type_box);

        JLabel end_Time = new JLabel("结束日期：", JLabel.CENTER);
        end_Time.setBounds((int) round(620 * sizecoeW), (int) round(5 * sizecoeH), (int) round(120 * sizecoeW), (int) round(50 * sizecoeH));
        end_Time.setFont(font18);
        top_p2.add(end_Time);

        JButton end_datebtn = new DateSelector();
        end_datebtn.setBounds((int)round(740*sizecoeW),(int)round(10*sizecoeH),(int)round(250*sizecoeW),(int)round(35*sizecoeH));
        end_datebtn.setFont(font14);
        end_datebtn.setBackground(new Color(139, 137, 137));
        end_datebtn.setForeground(Color.white);
        top_p2.add(end_datebtn);

        JCheckBox dun20_box = new JCheckBox("小于");
        dun20_box.setFont(font18);
        dun20_box.setBounds((int)round(1020*sizecoeW),(int)round(10*sizecoeH),(int)round(80*sizecoeW),(int)round(35*sizecoeH));
        top_p2.add(dun20_box);

        JTextField number_box = new JTextField();
        number_box.setFont(font18);
        number_box.setText("20");
        number_box.setBounds((int)round(1105*sizecoeW),(int)round(10*sizecoeH),(int)round(40*sizecoeW),(int)round(35*sizecoeH));
        top_p2.add(number_box);

        JLabel dun_L = new JLabel("吨");
        dun_L.setFont(font18);
        dun_L.setBounds((int)round(1145*sizecoeW),(int)round(10*sizecoeH),(int)round(40*sizecoeW),(int)round(35*sizecoeH));
        top_p2.add(dun_L);




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

        // 查询按钮
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
                String pl_no_te=null;

                if(line_No.isSelected()){
                    pl_no_te=line_No_box.getSelectedItem().toString();
                }

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
                Instant start_time=null;
                Instant end_time=null;
                try {
                    start_time= table_format.parse(start_date).toInstant();
                    end_time=table_format.parse(end_date).toInstant();
                } catch (ParseException e1) {
                    logger.error(e1);
                }
                // 历史订单查询

                List<Order> historyOrders=orderOperateService.findOrders(
                        pl_no_te==null?null:Integer.valueOf(pl_no_te),
                        bag_No_te==null?null:Integer.valueOf(bag_No_te),
                        cement_Type_te,
                        start_time,
                        end_time,
                        dun_te==null?null:Double.parseDouble(dun_te)
                        );
//                orderOperateService.findOrders();
//                java.util.List<Order> results=Pack_DB_Record.find_pack_history_order(bag_No_te,cement_Type_te,db_format.format(start_time),db_format.format(end_time),dun_te==null?null:Double.parseDouble(dun_te));
                ZoneId systemDefault = ZoneId.systemDefault();
                for(Order order:historyOrders){
                    historyTable.defaultModel.insertRow(0, new Object[]{order.getVehicleno(),order.getMaterial(),order.getBillcode(),order.getPro_weight(),order.getTotal_amount(), LocalDateTime.ofInstant(order.getCreate_time(), systemDefault).toString()});
                }

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

                BigDecimal b   =   new   BigDecimal(total_dun_d);
                double     f1 =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
                total_dun_L.setText(f1+"吨");
                total_bao_L.setText(total_bao_d+"包");

            }
        });

    }
}
