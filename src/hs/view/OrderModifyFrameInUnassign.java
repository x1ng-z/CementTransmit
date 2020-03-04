package hs.view;


import hs.modle.order.PackManulOrder;
import hs.service.OrderOperateService;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import static java.lang.Math.round;

public class OrderModifyFrameInUnassign extends JDialog {
    private int edit_row;
    private OrderOperateService orderOperateService;
    private double sizecoeH;
    private double sizecoeW;

    public OrderModifyFrameInUnassign(Double sizecoeH, Double sizecoeW, String[] carmessge, UnassignOrderTable carline_Table, int edit_row, OrderOperateService orderOperateService,MainFrame mainFrame){
        this.edit_row=edit_row;
        this. orderOperateService= orderOperateService;
        this.sizecoeW=sizecoeW;
        this.sizecoeH=sizecoeH;


        setTitle("车辆信息录入");
        setAlwaysOnTop(true);
        setSize((int)round(515*sizecoeW),(int)round(280*sizecoeH));
        setLayout(null);

        Font font14= new Font("宋体",Font.BOLD,14);
        Font font16= new Font("宋体",Font.BOLD,16);
        Font font18= new Font("宋体",Font.BOLD,18);
        Font font20= new Font("宋体",Font.BOLD,20);
        Font font22= new Font("宋体",Font.BOLD,22);

        Border etchedborder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        Border etchedBorderL = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Border raisedBevelBorder =BorderFactory.createRaisedBevelBorder();
        Border titleBorderL =BorderFactory.createTitledBorder(etchedBorderL,"待分配列表",TitledBorder.LEFT,TitledBorder.TOP,font18);
        Border titleBorderR =BorderFactory.createTitledBorder(etchedBorderL,"车辆信息录入",TitledBorder.LEFT,TitledBorder.TOP,font18);
        GridLayout gridLayout1 = new GridLayout(1,3,(int)round(10*sizecoeW),(int)round(0*sizecoeH));
        GridLayout gridLayout2 = new GridLayout(4,2,(int)round(20*sizecoeW),(int)round(5*sizecoeH));

        JPanel bottom_rightP = new JPanel();
        bottom_rightP.setBorder(titleBorderR);
        bottom_rightP.setBounds((int)round(0*sizecoeW),(int)round(0*sizecoeH),(int)round(515*sizecoeW),(int)round(240*sizecoeH));
        bottom_rightP.setLayout(null);
        add(bottom_rightP);

        JLabel deliver_No = new JLabel("发货单号");
        deliver_No.setFont(font14);
        deliver_No.setBounds((int)round(20*sizecoeW),(int)round(25*sizecoeH),(int)round(90*sizecoeW),(int)round(30*sizecoeH));
        JTextField deliver_No_F = new JTextField();
        deliver_No_F.setFont(font14);
        deliver_No_F.setBounds((int)round(110*sizecoeW),(int)round(25*sizecoeH),(int)round(180*sizecoeW),(int)round(30*sizecoeH));

        bottom_rightP.add(deliver_No);
        bottom_rightP.add(deliver_No_F);

        JLabel car_No = new JLabel("车牌号");
        car_No.setFont(font14);
        car_No.setBounds((int)round(20*sizecoeW),(int)round(60*sizecoeH),(int)round(90*sizecoeW),(int)round(30*sizecoeH));
        JTextField car_No_F = new JTextField();
        car_No_F.setFont(font14);
        car_No_F.setBounds((int)round(110*sizecoeW),(int)round(60*sizecoeH),(int)round(180*sizecoeW),(int)round(30*sizecoeH));

        bottom_rightP.add(car_No);
        bottom_rightP.add(car_No_F);

        JLabel pre_load = new JLabel("应装（吨）");
        pre_load.setFont(font14);
        pre_load.setBounds((int)round(20*sizecoeW),(int)round(95*sizecoeH),(int)round(85*sizecoeW),(int)round(30*sizecoeH));
        JTextField pre_load_F = new JTextField();
        pre_load_F.setFont(font14);
        pre_load_F.setBounds((int)round(110*sizecoeW),(int)round(95*sizecoeH),(int)round(70*sizecoeW),(int)round(30*sizecoeH));
        JLabel divLabel = new JLabel("/");
        divLabel.setFont(font18);
        divLabel.setBounds((int)round(190*sizecoeW),(int)round(95*sizecoeH),(int)round(25*sizecoeW),(int)round(30*sizecoeH));
        JTextField pre_load_F1 = new JTextField();
        pre_load_F1.setFont(font14);
        pre_load_F1.setBounds((int)round(210*sizecoeW),(int)round(95*sizecoeH),(int)round(80*sizecoeW),(int)round(30*sizecoeH));
        JLabel baoLabel = new JLabel("包");
        baoLabel.setFont(font18);
        baoLabel.setBounds((int)round(295*sizecoeW),(int)round(95*sizecoeH),(int)round(30*sizecoeW),(int)round(30*sizecoeH));

        bottom_rightP.add(pre_load);
        bottom_rightP.add(pre_load_F);
        bottom_rightP.add(divLabel);
        bottom_rightP.add(pre_load_F1);
        bottom_rightP.add(baoLabel);

        /*---------------装车吨数和包数关联-----------*/
        pre_load_F.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                double pre_load_Fnu = Double.parseDouble(pre_load_F.getText());
                double preload_bao = pre_load_Fnu*20.0;
                int preloadbao  = (int) preload_bao ;
                pre_load_F1.setText(String.valueOf(preloadbao));
            }
        });

        pre_load_F1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                double pre_load_F1nu = Double.parseDouble(pre_load_F1.getText());
                double preload_dun = pre_load_F1nu/20.0;
                pre_load_F.setText(String.valueOf(preload_dun));
            }
        });

        JLabel cement_type = new JLabel("水泥品种");
        cement_type.setFont(font14);
        cement_type.setBounds((int)round(20*sizecoeW),(int)round(130*sizecoeH),(int)round(80*sizecoeW),(int)round(30*sizecoeH));
        JTextField cement_type_F = new JTextField();
        cement_type_F.setFont(font14);
        cement_type_F.setBounds((int)round(110*sizecoeW),(int)round(130*sizecoeH),(int)round(180*sizecoeW),(int)round(30*sizecoeH));

        bottom_rightP.add(cement_type);
        bottom_rightP.add(cement_type_F);

        JLabel custom_No = new JLabel("客户代码");
        custom_No.setFont(font14);
        custom_No.setBounds((int)round(20*sizecoeW),(int)round(165*sizecoeH),(int)round(80*sizecoeW),(int)round(30*sizecoeH));
        JTextField custom_No_F = new JTextField();
        custom_No_F.setFont(font14);
        custom_No_F.setBounds((int)round(110*sizecoeW),(int)round(165*sizecoeH),(int)round(180*sizecoeW),(int)round(30*sizecoeH));

        bottom_rightP.add(custom_No);
        bottom_rightP.add(custom_No_F);

        JLabel cement_batch = new JLabel("水泥批号");
        cement_batch.setFont(font14);
        cement_batch.setBounds((int)round(20*sizecoeW),(int)round(200*sizecoeH),(int)round(80*sizecoeW),(int)round(30*sizecoeH));
        JTextField cement_batch_F = new JTextField();
        cement_batch_F.setFont(font14);
        cement_batch_F.setBounds((int)round(110*sizecoeW),(int)round(200*sizecoeH),(int)round(180*sizecoeW),(int)round(30*sizecoeH));

        bottom_rightP.add(cement_batch);
        bottom_rightP.add(cement_batch_F);

        car_No_F.setText(carmessge[0]);
        cement_type_F.setText(carmessge[1]);
        pre_load_F1.setText(carmessge[2]);
        custom_No_F.setText(carmessge[3]);
        cement_batch_F.setText(carmessge[4]);
        deliver_No_F.setText(carmessge[5]);

        int pre_load_f1 = Integer.parseInt(carmessge[2]);
        int pre_load_f = pre_load_f1/20;
        pre_load_F.setText( String.valueOf(pre_load_f));

        JButton Ok_Btn = new JButton("确定");
        Ok_Btn.setBorder(raisedBevelBorder);
        Ok_Btn.setBackground(new Color(238,238,238));
        Ok_Btn.setFont(font22);
        Ok_Btn.setBounds((int)round(350*sizecoeW),(int)round(25*sizecoeH),(int)round(100*sizecoeW),(int)round(35*sizecoeH));
        bottom_rightP.add(Ok_Btn);

        JButton cancel_Btn = new JButton("取消");
        cancel_Btn.setBorder(raisedBevelBorder);
        cancel_Btn.setBackground(new Color(238,238,238));
        cancel_Btn.setFont(font22);
        cancel_Btn.setBounds((int)round(350*sizecoeW),(int)round(65*sizecoeH),(int)round(100*sizecoeW),(int)round(35*sizecoeH));
        bottom_rightP.add(cancel_Btn);

        JPanel carbtnPanel = new JPanel();
        carbtnPanel.setLayout(gridLayout2);
        carbtnPanel.setBounds((int)round(350*sizecoeW),(int)round(100*sizecoeH),(int)round(100*sizecoeW),(int)round(130*sizecoeH));
        bottom_rightP.add(carbtnPanel);

        JButton zhe_Btn = new JButton("浙");
        JButton gan_Btn = new JButton("赣");
        JButton xiang_Btn = new JButton("湘");
        JButton yue_Btn = new JButton("粤");
        JButton yu_Btn = new JButton("豫");
        JButton wan_Btn = new JButton("皖");
        JButton e_Btn = new JButton("鄂");
        JButton gui_Btn = new JButton("桂");

        JButton[] carbtnArray = new JButton[]{zhe_Btn,gan_Btn,xiang_Btn,yue_Btn,yu_Btn,wan_Btn,e_Btn,gui_Btn};

        java.util.List<String> configConnvechel=orderOperateService.getConVechel();
        int intermVech=0;
        for(String vchel:configConnvechel){
            carbtnArray[intermVech++].setText(vchel);
        }

        for(JButton car_button : carbtnArray){
            car_button.setForeground(Color.BLUE);
            car_button.setBorder(raisedBevelBorder);
            car_button.setBackground(new Color(238,238,238));
            car_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String btn_text = car_button.getText();
                    car_No_F.setText(btn_text);
                }
            });
        }
        carbtnPanel.add(zhe_Btn);
        carbtnPanel.add(gan_Btn);
        carbtnPanel.add(xiang_Btn);
        carbtnPanel.add(yue_Btn);
        carbtnPanel.add(yu_Btn);
        carbtnPanel.add(wan_Btn);
        carbtnPanel.add(e_Btn);
        carbtnPanel.add(gui_Btn);

        Ok_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    String carmessge0 = car_No_F.getText();
                    String carmessge2 = cement_type_F.getText();
                    String carmessge3 = pre_load_F1.getText();
                    String carmessge5 = custom_No_F.getText();
                    String carmessge6 = cement_batch_F.getText();
                    String carmessge7 = deliver_No_F.getText();

    //                carline_Table.setValueAt(carmessge0,edit_row,0);
    //                carline_Table.setValueAt(carmessge2,edit_row,2);
    //                carline_Table.setValueAt(carmessge3,edit_row,3);
    //                carline_Table.setValueAt(carmessge5,edit_row,5);
    //                carline_Table.setValueAt(carmessge6,edit_row,6);
    //                carline_Table.setValueAt(carmessge7,edit_row,7);
                PackManulOrder new_order=new PackManulOrder();
                new_order.setVehicleno(carmessge0);
                new_order.setMaterial(carmessge2);
                new_order.setTotal_amount(Integer.parseInt(carmessge3));
//                new_order.setAlready_amount(old_order.getAlready_amount());
                new_order.setConsumer_code(carmessge5);
                new_order.setBatch_no(carmessge6);
                new_order.setBillcode(carmessge7);
//
                boolean isOperatesuccess=orderOperateService.modifyOrderInUnassignList(edit_row,new_order);
                if(!isOperatesuccess){
                    failedModifyDialog();
                }
                mainFrame.flushAssignOrderTable();
                dispose();
            }
        });

        cancel_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void failedModifyDialog() {
        Border raisedBevelBorder = BorderFactory.createRaisedBevelBorder();
        JDialog dialog = new JDialog();
        dialog.setTitle("修改订单");
        dialog.setSize((int) round(300 * sizecoeW), (int) round(160 * sizecoeH));
        dialog.setLocationRelativeTo(this.getContentPane());
        dialog.setLayout(null);
        JLabel message_L = new JLabel("已装数量大于修改后的订单总数，修改失败！", JLabel.CENTER);
        message_L.setBounds((int) round(0 * sizecoeW), (int) round(20 * sizecoeH), (int) round(300 * sizecoeW), (int) round(50 * sizecoeH));
        JButton OK_Btn = new JButton("确定");
        OK_Btn.setBorder(raisedBevelBorder);
        OK_Btn.setBackground(new Color(238, 238, 238));
        OK_Btn.setBounds((int) round(120 * sizecoeW), (int) round(70 * sizecoeH), (int) round(60 * sizecoeW), (int) round(40 * sizecoeH));
        dialog.add(message_L);
        dialog.add(OK_Btn);
        OK_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
        dialog.setAlwaysOnTop(true);
    }
}
