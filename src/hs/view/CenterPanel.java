package hs.view;

import hs.modle.Location;
import hs.modle.PackMachine;
import hs.modle.order.Order;
import hs.service.OrderOperateService;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.lang.Math.round;

public class CenterPanel extends JPanel {
    private static Logger logger = Logger.getLogger(CenterPanel.class);
    private JTextField typeName_F, COM_F, car_No_F, pre_load_F, car_lane_F, cement_type_F, deliver_No_F, code_con_F, real_load_F;
    //    private JLabel real_load_L;
    private PackMachine pack_machine;
    private String machine_name;
    private JTextField[] textFieldsArray;
    private AssignOrderTable assignOrderTable;
    private ButtonGroup classSelect;
    private String name;
    private OrderOperateService orderOperateService;
    private MainFrame mainFrame;
    public JButton state1_btn;
    public JButton state2_btn;

    public CenterPanel(PackMachine pack_machine, Double sizecoeH, Double sizecoeW, int line_No, AssignOrderTable assignOrderTable, MainFrame mainFrame,OrderOperateService orderOperateService) {
        this.pack_machine = pack_machine;
        this.assignOrderTable = assignOrderTable;
        this.name = pack_machine.getPackerConfigure().getCommentZh();
        this.orderOperateService=orderOperateService;
        this.mainFrame=mainFrame;

        Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        Border etchedBorderL = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Border raisedBevelBorder = BorderFactory.createRaisedBevelBorder();
        Border titleBorder = BorderFactory.createTitledBorder(etchedBorderL, "班组");
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEADING, (int) round(30 * sizecoeW), (int) round(0 * sizecoeH));
        Font font14 = new Font("宋体", Font.BOLD, (int) round(14 * sizecoeW));
        Font font18 = new Font("宋体", Font.BOLD, (int) round(18 * sizecoeW));
        Font font20 = new Font("宋体", Font.BOLD, (int) round(20 * sizecoeW));

        /*-------------firstP_p1---------------*/
        JPanel firstP_p1 = new JPanel();
        firstP_p1.setBounds((int) round(5 * sizecoeW), (int) round(5 * sizecoeH), (int) round(505 * sizecoeW), (int) round(40 * sizecoeH));
        firstP_p1.setBorder(etchedBorder);
        firstP_p1.setLayout(null);
        add(firstP_p1);

//        JButton oneline_stop = new JButton(line_No + "号线停止");
//        oneline_stop.setBounds((int) round(30 * sizecoeW), (int) round(5 * sizecoeH), (int) round(90 * sizecoeW), (int) round(30 * sizecoeH));
//        oneline_stop.setBorder(raisedBevelBorder);
//        oneline_stop.setBackground(new Color(238, 238, 238));

        JLabel oneline = new JLabel(pack_machine.getPackerConfigure().getCommentZh() , JLabel.CENTER);
        oneline.setFont(font20);
        oneline.setBounds((int) round(190 * sizecoeW), (int) round(5 * sizecoeH), (int) round(120 * sizecoeW), (int) round(30 * sizecoeH));

//        JButton oneline_start = new JButton(line_No + "号线启用");
//        oneline_start.setBounds((int) round(380 * sizecoeW), (int) round(5 * sizecoeH), (int) round(90 * sizecoeW), (int) round(30 * sizecoeH));
//        oneline_start.setBorder(raisedBevelBorder);
//        oneline_start.setBackground(new Color(238, 238, 238));

//        firstP_p1.add(oneline_stop);
        firstP_p1.add(oneline);
//        firstP_p1.add(oneline_start);


        /*-------------firstP_p2---------------*/
        JPanel firstP_p2 = new JPanel();
        firstP_p2.setBounds((int) round(5 * sizecoeW), (int) round(50 * sizecoeH), (int) round(505 * sizecoeW), (int) round(40 * sizecoeH));
        firstP_p2.setBorder(etchedBorder);
        firstP_p2.setLayout(null);
        add(firstP_p2);

        JLabel typeName_L = new JLabel("品种牌号");
        typeName_L.setFont(font18);
        typeName_L.setBounds((int) round(30 * sizecoeW), (int) round(5 * sizecoeH), (int) round(90 * sizecoeW), (int) round(30 * sizecoeH));
        typeName_F = new JTextField();
        typeName_F.setFont(font18);
        typeName_F.setBounds((int) round(120 * sizecoeW), (int) round(5 * sizecoeH), (int) round(180 * sizecoeW), (int) round(30 * sizecoeH));

        COM_F = new JTextField();
        COM_F.setFont(font18);
        COM_F.setBounds((int) round(310 * sizecoeW), (int) round(5 * sizecoeH), (int) round(60 * sizecoeW), (int) round(30 * sizecoeH));

        state1_btn = new JButton();

        state1_btn.setBounds((int) round(390 * sizecoeW), (int) round(8 * sizecoeH), (int) round(24 * sizecoeW), (int) round(24 * sizecoeH));

        state2_btn = new JButton();

        state2_btn.setBounds((int) round(430 * sizecoeW), (int) round(8 * sizecoeH), (int) round(24 * sizecoeW), (int) round(24 * sizecoeH));


        if(pack_machine.isDevicdConnect()){
            state1_btn.setBackground(Color.green);
            state2_btn.setBackground(Color.green);
        }else {
            state1_btn.setBackground(Color.red);
            state2_btn.setBackground(Color.red);
        }
        firstP_p2.add(typeName_L);
        firstP_p2.add(typeName_F);
        firstP_p2.add(COM_F);
        firstP_p2.add(state1_btn);
        firstP_p2.add(state2_btn);

        /*-------------firstP_p3---------------*/
        JPanel firstP_p3 = new JPanel();
        firstP_p3.setBounds((int) round(5 * sizecoeW), (int) round(90 * sizecoeH), (int) round(505 * sizecoeW), (int) round(150 * sizecoeH));
        firstP_p3.setBorder(etchedBorder);
        firstP_p3.setLayout(null);
        add(firstP_p3);

        JLabel car_No = new JLabel("车牌号");
        car_No.setFont(font14);
        car_No.setBounds((int) round(15 * sizecoeW), (int) round(10 * sizecoeH), (int) round(70 * sizecoeW), (int) round(30 * sizecoeH));
        car_No_F = new JTextField();
        car_No_F.setFont(font14);
        car_No_F.setBounds((int) round(100 * sizecoeW), (int) round(10 * sizecoeH), (int) round(120 * sizecoeW), (int) round(30 * sizecoeH));

        firstP_p3.add(car_No);
        firstP_p3.add(car_No_F);

        JLabel pre_load = new JLabel("应装（包）");
        pre_load.setFont(font14);
        pre_load.setBounds((int) round(15 * sizecoeW), (int) round(45 * sizecoeH), (int) round(80 * sizecoeW), (int) round(30 * sizecoeH));
        pre_load_F = new JTextField();
        pre_load_F.setFont(font14);
        pre_load_F.setBounds((int) round(100 * sizecoeW), (int) round(45 * sizecoeH), (int) round(80 * sizecoeW), (int) round(30 * sizecoeH));

        JLabel real_load = new JLabel("已装");
        real_load.setFont(font14);
        real_load.setBounds((int) round(195 * sizecoeW), (int) round(45 * sizecoeH), (int) round(50 * sizecoeW), (int) round(30 * sizecoeH));
        real_load_F = new JTextField();
        real_load_F.setText("0");
        real_load_F.setBorder(etchedBorderL);
        real_load_F.setFont(font18);
        real_load_F.setForeground(Color.red);
        real_load_F.setBounds((int) round(240 * sizecoeW), (int) round(45 * sizecoeH), (int) round(80 * sizecoeW), (int) round(30 * sizecoeH));

        JLabel car_lane = new JLabel("道口");
        car_lane.setFont(font14);
        car_lane.setBounds((int) round(370 * sizecoeW), (int) round(45 * sizecoeH), (int) round(50 * sizecoeW), (int) round(30 * sizecoeH));
        car_lane_F = new JTextField();
        car_lane_F.setFont(font14);
        car_lane_F.setBounds((int) round(420 * sizecoeW), (int) round(45 * sizecoeH), (int) round(60 * sizecoeW), (int) round(30 * sizecoeH));

        firstP_p3.add(pre_load);
        firstP_p3.add(pre_load_F);
        firstP_p3.add(real_load);
        firstP_p3.add(real_load_F);
        firstP_p3.add(car_lane);
        firstP_p3.add(car_lane_F);

        JLabel cement_type = new JLabel("水泥型号");
        cement_type.setFont(font14);
        cement_type.setBounds((int) round(15 * sizecoeW), (int) round(80 * sizecoeH), (int) round(80 * sizecoeW), (int) round(30 * sizecoeH));
        cement_type_F = new JTextField();
        cement_type_F.setFont(font14);
        cement_type_F.setBounds((int) round(100 * sizecoeW), (int) round(80 * sizecoeH), (int) round(140 * sizecoeW), (int) round(30 * sizecoeH));

        JLabel deliver_No = new JLabel("发货单号");
        deliver_No.setFont(font14);
        deliver_No.setBounds((int) round(250 * sizecoeW), (int) round(80 * sizecoeH), (int) round(80 * sizecoeW), (int) round(30 * sizecoeH));
        deliver_No_F = new JTextField();
        deliver_No_F.setFont(font14);
        deliver_No_F.setBounds((int) round(340 * sizecoeW), (int) round(80 * sizecoeH), (int) round(140 * sizecoeW), (int) round(30 * sizecoeH));

        firstP_p3.add(cement_type);
        firstP_p3.add(cement_type_F);
        firstP_p3.add(deliver_No);
        firstP_p3.add(deliver_No_F);

        JLabel code_con = new JLabel("喷码内容");
        code_con.setFont(font14);
        code_con.setBounds((int) round(15 * sizecoeW), (int) round(115 * sizecoeH), (int) round(80 * sizecoeW), (int) round(30 * sizecoeH));
        code_con_F = new JTextField();
        code_con_F.setFont(font14);
        code_con_F.setBounds((int) round(100 * sizecoeW), (int) round(115 * sizecoeH), (int) round(240 * sizecoeW), (int) round(30 * sizecoeH));
//        JButton suspend_btn = new JButton("暂停装车");
//        suspend_btn.setFont(font14);
//        suspend_btn.setBounds((int) round(380 * sizecoeW), (int) round(115 * sizecoeH), (int) round(100 * sizecoeW), (int) round(30 * sizecoeH));
//        suspend_btn.setBorder(raisedBevelBorder);
//        suspend_btn.setBackground(new Color(238, 238, 238));

        firstP_p3.add(code_con);
        firstP_p3.add(code_con_F);
//        firstP_p3.add(suspend_btn);

        /*------------暂停装车按钮功能-----------*/
//        suspend_btn.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        });

        textFieldsArray = new JTextField[]{typeName_F, COM_F, car_No_F, pre_load_F, car_lane_F, cement_type_F, deliver_No_F, code_con_F, real_load_F};
        for (JTextField textFields : textFieldsArray) {
            textFields.setEditable(false);
        }


        /*-------------班组P_p4---------------*/
        JPanel firstP_p4 = new JPanel();
        firstP_p4.setBounds((int) round(5 * sizecoeW), (int) round(240 * sizecoeH), (int) round(505 * sizecoeW), (int) round(50 * sizecoeH));
        firstP_p4.setBorder(titleBorder);
        firstP_p4.setLayout(flowLayout);
        add(firstP_p4);

        JRadioButton radioBtn1 = new JRadioButton("1");
        radioBtn1.setActionCommand("1");
        JRadioButton radioBtn2 = new JRadioButton("2");
        radioBtn2.setActionCommand("2");
        JRadioButton radioBtn3 = new JRadioButton("3");
        radioBtn3.setActionCommand("3");
        JRadioButton radioBtn4 = new JRadioButton("4");
        radioBtn4.setActionCommand("4");
        JRadioButton radioBtn5 = new JRadioButton("5");
        radioBtn5.setActionCommand("5");
        JRadioButton radioBtn6 = new JRadioButton("6");
        radioBtn6.setActionCommand("6");
//        radioBtn1.setSelected(true);
        classSelect = new ButtonGroup();
        classSelect.add(radioBtn1);
        classSelect.add(radioBtn2);
        classSelect.add(radioBtn3);
        classSelect.add(radioBtn4);
        classSelect.add(radioBtn5);
        classSelect.add(radioBtn6);

        switch (pack_machine.defaultClass) {
            case 1:
                classSelect.setSelected(radioBtn1.getModel(), true);
                break;
            case 2:
                classSelect.setSelected(radioBtn2.getModel(), true);
                break;
            case 3:
                classSelect.setSelected(radioBtn3.getModel(), true);
                break;
            case 4:
                classSelect.setSelected(radioBtn4.getModel(), true);
                break;
            case 5:
                classSelect.setSelected(radioBtn5.getModel(), true);
                break;
            case 6:
                classSelect.setSelected(radioBtn6.getModel(), true);
                break;
        }

        firstP_p4.add(radioBtn1);
        firstP_p4.add(radioBtn2);
        firstP_p4.add(radioBtn3);
        firstP_p4.add(radioBtn4);
        firstP_p4.add(radioBtn5);
        firstP_p4.add(radioBtn6);

        RadiobtnListener radiobtnListener6 = new RadiobtnListener("班组选择");
        radioBtn1.addActionListener(radiobtnListener6);
        radioBtn2.addActionListener(radiobtnListener6);
        radioBtn3.addActionListener(radiobtnListener6);
        radioBtn4.addActionListener(radiobtnListener6);
        radioBtn5.addActionListener(radiobtnListener6);
        radioBtn6.addActionListener(radiobtnListener6);



        /*-------------firstP_p5---------------*/
        JPanel firstP_p5 = new JPanel();
        firstP_p5.setBounds((int) round(5 * sizecoeW), (int) round(290 * sizecoeH), (int) round(505 * sizecoeW), (int) round(180 * sizecoeH));
        firstP_p5.setLayout(null);
        add(firstP_p5);

        JLabel carLine_L = new JLabel();
        carLine_L.setFont(font14);
        carLine_L.setText(line_No + "号线排队车辆");
        carLine_L.setBounds((int) round(0 * sizecoeW), (int) round(0 * sizecoeH), (int) round(505 * sizecoeW), (int) round(30 * sizecoeH));


        JScrollPane car_Pane = new JScrollPane(this.assignOrderTable);
        car_Pane.setBounds((int) round(0 * sizecoeW), (int) round(30 * sizecoeH), (int) round(505 * sizecoeW), (int) round(140 * sizecoeH));

        firstP_p5.add(carLine_L);
        firstP_p5.add(car_Pane);

        /*---------------CarlineTable_C右键功能菜单-----------*/
        JPopupMenu car_bagNo = new JPopupMenu();
        JMenuItem menuItem_cancel = new JMenuItem("取消分配");
        JMenuItem menuItem_edit = new JMenuItem("修改");
        JMenuItem menuItem_close = new JMenuItem("关闭订单");
        car_bagNo.add(menuItem_cancel);
        car_bagNo.add(menuItem_edit);
        car_bagNo.add(menuItem_close);

        assignOrderTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON3) {
                    car_bagNo.show(e.getComponent(), e.getX(), e.getY());
                    int row = assignOrderTable.getSelectedRow();
                    assignOrderTable.setRowSelectionInterval(row, row);
                }
            }
        });

        /*---------------右键功能菜单—取消分配-----------*/
        menuItem_cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    logger.info("--row="+assignOrderTable.getSelectedRow());

                    int selected_row = assignOrderTable.getSelectedRow();//;
                    Location location=new Location();
                    location.setPackmachineIndex(pack_machine.getPackerConfigure().getDeviceOrder());
                    location.setProductionLine(pack_machine.getPackerConfigure().getProductLine());
                    location.setCarLaneIndex(Integer.parseInt(assignOrderTable.getValueAt(selected_row, 1).toString()));
                    orderOperateService.unassignOrderInPackList(location,selected_row);
                    mainFrame.flushAssignOrderTable();

//                    String row_0 = assignOrderTable.getValueAt(selected_row, 0).toString();
//                    String row_1 = assignOrderTable.getValueAt(selected_row, 1).toString();
//                    String row_2 = assignOrderTable.getValueAt(selected_row, 2).toString();
//                    String row_3 = assignOrderTable.getValueAt(selected_row, 3).toString();
//                    String row_4 = assignOrderTable.getValueAt(selected_row, 4).toString();
//                    String row_5 = assignOrderTable.getValueAt(selected_row, 5).toString();
//                    String row_6 = assignOrderTable.getValueAt(selected_row, 6).toString();
//                    String row_7 = assignOrderTable.getValueAt(selected_row, 7).toString();
//                    String row_8 = assignOrderTable.getValueAt(selected_row, 8).toString();
////                    String[] row_Array = new String[]{row_0,row_1,row_2,row_3,row_4,row_5,row_6,row_7};
//
//
//                    if (pick != null) {
//
//                        boolean is_succss = pick.unassignOrder(new Integer(assignOrderTable.getValueAt(selected_row, 1).toString()), pack_manulOrder);
//                        if (is_succss) {
//                            carline_Table.defaultModel.insertRow(carline_Table.defaultModel.getRowCount(), new Object[]{row_0, "null", row_2, row_3, row_4, row_5, row_6, row_7,row_8});
//                            assignOrderTable.defaultModel.removeRow(selected_row);
//                        }
//                        MainFrame.getinstance().flushAssignOrderTable();
//
//                    }


                }
            }
        });

        /*---------------右键功能菜单—修改-----------*/
        menuItem_edit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    int selected_row = assignOrderTable.getSelectedRow();//rowAtPoint(e.getPoint());

                    String row_0 = assignOrderTable.getValueAt(selected_row, 0).toString();
                    String row_1 = assignOrderTable.getValueAt(selected_row, 2).toString();
                    String row_2 = assignOrderTable.getValueAt(selected_row, 3).toString();
                    String row_4 = assignOrderTable.getValueAt(selected_row, 5).toString();
                    String row_5 = assignOrderTable.getValueAt(selected_row, 6).toString();
                    String row_6 = assignOrderTable.getValueAt(selected_row, 7).toString();
                    String[] row_Array = new String[]{row_0, row_1, row_2, row_4, row_5, row_6};

                    Location location=new Location();
                    location.setProductionLine(pack_machine.getPackerConfigure().getProductLine());
                    location.setPackmachineIndex(pack_machine.getPackerConfigure().getDeviceOrder());
                    location.setCarLaneIndex(Integer.valueOf(assignOrderTable.getValueAt(selected_row, 1).toString()));
                    OrderModifyFrameInAssign carMessage_C = new OrderModifyFrameInAssign(sizecoeW, sizecoeH, row_Array, assignOrderTable, selected_row, getClassSelect(), location,orderOperateService,mainFrame);
                    carMessage_C.setLocationRelativeTo(e.getComponent());
                    carMessage_C.setVisible(true);
                }


            }

        });

        /*---------------右键功能菜单—关闭订单-----------*/
        menuItem_close.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    int selected_row = assignOrderTable.getSelectedRow();

                    Location location=new Location();
                    location.setProductionLine(pack_machine.getPackerConfigure().getProductLine());
                    location.setPackmachineIndex(pack_machine.getPackerConfigure().getDeviceOrder());
                    location.setCarLaneIndex(Integer.valueOf(assignOrderTable.getValueAt(selected_row, 1).toString()));
                    orderOperateService.delectOrderInPackList(location,selected_row);
                    mainFrame.flushAssignOrderTable();

                }
            }
        });


        JPopupMenu addbag = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem("补包");

        addbag.add(menuItem);

        pre_load_F.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON3) {
                    addbag.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

    }

    public void flush_assign_order() {
        int rows = assignOrderTable.getRowCount();
        for (int i = 0; i < rows; ++i) {
            assignOrderTable.defaultModel.removeRow(0);
        }
        java.util.List<Order> ssigned_queue = pack_machine.getAllSortOrder();
        for (int i = 0; i < ssigned_queue.size(); ++i) {
            Order order = ssigned_queue.get(i);
            assignOrderTable.defaultModel.insertRow(i, new Object[]{order.getVehicleno(), order.getCarLaneIndex() == 0 ? "null" : order.getCarLaneIndex()+"", order.getMaterial(),  Integer.valueOf(order.getTotal_amount()).toString(), Integer.valueOf(order.getAlready_amount()).toString(), order.getConsumer_code(), order.getBatch_no(), order.getBatch_no(),order.getPk_delivery()});
        }

    }

    public ButtonGroup getClassSelect() {
        return classSelect;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }


    private class RadiobtnListener implements ActionListener {
        private String team_no;

        public RadiobtnListener(String team) {
            this.team_no = team;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // 改变了班组，需要重新将车道内已分配的所有订单都修改班组，如存在当前正在包装的订单，则需要重新下发喷码
            logger.info("change class: " +  e.getActionCommand());
            for(Order order:pack_machine.getAllSortOrder()){
                order.setClass_no( e.getActionCommand());
            }
            pack_machine.defaultClass= Integer.parseInt(e.getActionCommand());
            mainFrame.flushAssignOrderTable();
        }
    }

    public void flushPackMahineExecuteOrder() {

        Order order=null;
        if(pack_machine.getCurrentExecuteOrder()!=null){
            order=pack_machine.getCurrentExecuteOrder();
        }else {
            if(pack_machine.getAllSortOrder().size()!=0){
                order=pack_machine.getAllSortOrder().get(0);
            }
        }

        if (order == null) {
            textFieldsArray[0].setText("");
            textFieldsArray[1].setText("");
            textFieldsArray[2].setText("");
            textFieldsArray[3].setText("");
            textFieldsArray[4].setText("");
            textFieldsArray[5].setText("");
            textFieldsArray[6].setText("");
            textFieldsArray[7].setText("");
            textFieldsArray[8].setText("0");

        } else {
            textFieldsArray[0].setText(order.getMaterial());
            textFieldsArray[1].setText("COM_F");
            textFieldsArray[2].setText(order.getVehicleno());
            textFieldsArray[3].setText(Integer.valueOf(order.getTotal_amount()).toString());
            textFieldsArray[4].setText(order.getCarLaneIndex()+"");
            textFieldsArray[5].setText(order.getMaterial());
            textFieldsArray[6].setText(order.getBillcode());
            //code
            textFieldsArray[7].setText(order.getConsumer_code() + order.getCreate_time_form_mmdd() + "0"+order.getClass_no() + order.getProductLineIndex() + order.getBatch_no());
            textFieldsArray[8].setText(Integer.valueOf(order.getAlready_amount()).toString());
        }

        if(pack_machine.isDevicdConnect()){
            state1_btn.setBackground(Color.green);
            state2_btn.setBackground(Color.green);
        }else {
            state1_btn.setBackground(Color.red);
            state2_btn.setBackground(Color.red);
        }

    }
}
