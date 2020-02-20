package hs.view;


import hs.modle.CarLane;
import hs.modle.PackMAchineGroup;
import hs.modle.PackMachine;
import hs.modle.order.Order;
import hs.modle.order.PackManulOrder;
import hs.service.OrderOperateService;
import hs.utils.CodeHelper;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.Instant;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Math.round;

public class MainFrame extends JFrame {
    public static Logger logger = Logger.getLogger(MainFrame.class);

    private OrderOperateService orderOperateService;

    private Double sizecoeW, sizecoeH;
    private JButton lineP_L1_btn1; //lineP_L1_btn2, lineP_L2_btn3, lineP_L2_btn4, lineP_L3_btn5, lineP_L3_btn6;
    private String car_No_text, car_lane_text, cement_type_text, pre_load_text, real_load_text, custom_No_text, cement_batch_text, deliver_No_text;
    private UnassignOrderTable unassignOrderTable;
//    private AssignOrderTable car_Table1, car_Table2, car_Table3;

    private HistoryTable historyTable;
//    private CenterPanel first_Panel, second_Panel, third_Panel;
    private java.util.List<CenterPanel> centerPanels;
    private CurrentTimeLabel currentTimeLabel;
    JPanel centerPanel;
    JScrollPane wappercenterPanel;
    private int gproductline =1;

//    private static volatile MainFrame mainFrame=null;
//    public synchronized static MainFrame getinstance(){
//        if(mainFrame==null){
//            mainFrame= new MainFrame();
//        }
//        return mainFrame;
//    }


    void build(){
        centerPanel.removeAll();
        int countOfPackMachine=orderOperateService.getPackMAchineGroup().getProductLineMaps().get(gproductline).getDeviceMaps().size();
        GridLayout gridLayout1 = new GridLayout(1, 3, (int) round(10 * sizecoeW), (int) round(0 * sizecoeH));
//        FlowLayout gridLayout1=new FlowLayout();
        centerPanel.setLayout(gridLayout1);
        centerPanels=new java.util.ArrayList<CenterPanel>();
//        car_Table1 = new AssignOrderTable();
//        car_Table2 = new AssignOrderTable();
//        car_Table3 = new AssignOrderTable();
        Border etchedborder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        Border etchedBorderL = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Map<Integer,PackMachine> deviceMaps=orderOperateService.getPackMAchineGroup().getProductLineMaps().get(getGproductline()).getDeviceMaps();
        for(PackMachine packMachine:deviceMaps.values()){
            AssignOrderTable assignOrderTable=new AssignOrderTable();
            CenterPanel panel=new CenterPanel(packMachine,sizecoeH, sizecoeW, gproductline,assignOrderTable, this,orderOperateService);
            panel.setBorder(etchedborder);
            panel.setLayout(null);

            centerPanels.add(panel);
            centerPanel.add(panel);
        }





//        first_Panel = new CenterPanel(machine_1, sizecoeH, sizecoeW, "1", car_Table1, unassignOrderTable, machineIfo_1.getName());
//        first_Panel.setBorder(etchedborder);
//        first_Panel.setLayout(null);
//        centerPanel.add(first_Panel);
//
//        second_Panel = new CenterPanel(machine_2, sizecoeH, sizecoeW, "2", car_Table2, unassignOrderTable, machineIfo_2.getName());
//        second_Panel.setBorder(etchedborder);
//        second_Panel.setLayout(null);
//        centerPanel.add(second_Panel);
//
//        third_Panel = new CenterPanel(machine_3, sizecoeH, sizecoeW, "3", car_Table3, unassignOrderTable, machineIfo_3.getName());
//        third_Panel.setBorder(etchedborder);
//        third_Panel.setLayout(null);
//        centerPanel.add(third_Panel);


    }

//    private Map<String, Pack_Machine> pack_machines = Machine_Manger.getInstance().getPack_machines();





    public MainFrame(OrderOperateService orderOperateService) {
        this.orderOperateService=orderOperateService;
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
//        Image logoimage = Toolkit.getDefaultToolkit().createImage(System.getProperty("user.dir")+"/resource/img/Icon.jpg");
//        setIconImage(logoimage);
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
        int bottomHeight = screenInsets.bottom;
        int screenHeight = screenSize.height - bottomHeight;
        int screenWidth = screenSize.width;
        setSize(screenWidth, screenHeight);
        setLocation(0, 0);
        setLayout(null);

        /*------------屏幕尺寸变化系数-----------*/
        String sw = String.valueOf(screenWidth);
        Double swidth = Double.parseDouble(sw);
        sizecoeW = swidth / 1600.0;
        String sh = String.valueOf(screenHeight);
        Double sheight = Double.parseDouble(sh);
        sizecoeH = sheight / 900.0;

        /*---------------软件界面字体及布局-------------*/
        Font font14 = new Font("宋体", Font.BOLD, 14);
        Font font16 = new Font("宋体", Font.BOLD, 16);
        Font font18 = new Font("宋体", Font.BOLD, 18);
        Font font20 = new Font("宋体", Font.BOLD, 20);
        Font font22 = new Font("宋体", Font.BOLD, 22);

        Border etchedborder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        Border etchedBorderL = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Border raisedBevelBorder = BorderFactory.createRaisedBevelBorder();
        Border titleBorderL = BorderFactory.createTitledBorder(etchedBorderL, "待分配列表", TitledBorder.LEFT, TitledBorder.TOP, font18);
        Border titleBorderR = BorderFactory.createTitledBorder(etchedBorderL, "车辆信息录入", TitledBorder.LEFT, TitledBorder.TOP, font18);
        GridLayout gridLayout2 = new GridLayout(4, 2, (int) round(20 * sizecoeW), (int) round(5 * sizecoeH));

        /*---------------软件菜单选项-------------*/
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("查询(V)");
        JMenu m2 = new JMenu("帮助(H)");

        JMenuItem m11 = new JMenuItem("历史查询");
        JMenuItem m21 = new JMenuItem("查看帮助");

        setJMenuBar(mb);
        mb.add(m1);
        mb.add(m2);
        m1.add(m11);
        m2.add(m21);

        m11.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HistoryFrame historyFrame = new HistoryFrame(sizecoeW,sizecoeH);
                historyFrame.setTitle("历史查询");
                historyFrame.setBounds((int) round(20 * sizecoeW), (int) round(50 * sizecoeH), (int) round(1560 * sizecoeW), (int) round(850 * sizecoeH));
                historyFrame.setVisible(true);
            }
        });


        JPanel backpane = new JPanel();
        setContentPane(backpane);
        backpane.setLayout(null);

        /*--------------顶部标题时间内容------------*/
        JPanel topPanel = new JPanel();
        topPanel.setBounds(0, 0, screenWidth, (int) round(60 * sizecoeH));
        topPanel.setBorder(etchedborder);
        topPanel.setLayout(null);
        add(topPanel);

        JLabel ouputLabel = new JLabel("产量统计");
        ouputLabel.setFont(font22);
        ouputLabel.setBounds((int) round(750 * sizecoeW), (int) round(10 * sizecoeH), (int) round(120 * sizecoeW), (int) round(40 * sizecoeH));
        topPanel.add(ouputLabel);

        JPanel modelPanel = new JPanel();
        modelPanel.setBounds((int) round(15 * sizecoeW), (int) round(4 * sizecoeH), (int) round(240 * sizecoeW), (int) round(52 * sizecoeH));
        modelPanel.setBorder(etchedBorderL);
        modelPanel.setLayout(null);
        topPanel.add(modelPanel);

        JLabel model_select = new JLabel("模式选择");
        model_select.setFont(font16);
        model_select.setBounds((int) round(5 * sizecoeW), (int) round(5 * sizecoeH), (int) round(100 * sizecoeW), (int) round(40 * sizecoeH));

        JRadioButton radioBtn1 = new JRadioButton("一卡通模式");
        radioBtn1.setBounds((int) round(100 * sizecoeW), (int) round(2 * sizecoeH), (int) round(130 * sizecoeW), (int) round(25 * sizecoeH));
        radioBtn1.setActionCommand("一卡通模式");
        JRadioButton radioBtn2 = new JRadioButton("手动模式");
        radioBtn2.setSelected(true);
        radioBtn2.setActionCommand("手动模式");
        radioBtn2.setBounds((int) round(100 * sizecoeW), (int) round(25 * sizecoeH), (int) round(130 * sizecoeW), (int) round(25 * sizecoeH));

        ButtonGroup radioBtn_group = new ButtonGroup();
        radioBtn_group.add(radioBtn1);
        radioBtn_group.add(radioBtn2);

        //todo 模式选择
        logger.info(radioBtn_group.getSelection().getActionCommand());

        modelPanel.add(model_select);
        modelPanel.add(radioBtn1);
        modelPanel.add(radioBtn2);

        String[] proline_list = new String[]{
                "一线","二线", "三线"};
        JComboBox<String> proline_box = new JComboBox<String>(proline_list);
        proline_box.setFont(font14);
        proline_box.setBounds((int) round(260 * sizecoeW), (int) round(4 * sizecoeH), (int) round(130 * sizecoeW), (int) round(30 * sizecoeH));
        topPanel.add(proline_box);

        currentTimeLabel = new CurrentTimeLabel();
        currentTimeLabel.setBounds((int) round(1260 * sizecoeW), (int) round(8 * sizecoeH), (int) round(290 * sizecoeW), (int) round(40 * sizecoeH));
        currentTimeLabel.setOpaque(true);
        currentTimeLabel.setBackground(Color.black);
        topPanel.add(currentTimeLabel);

        /*---------------中间面板内容-----------*/
        centerPanel = new JPanel();

        centerPanel.setPreferredSize(new Dimension(orderOperateService.getPackMAchineGroup().getProductLineMaps().get(gproductline).getDeviceMaps().size()*screenWidth/3 - (int) round(15 * sizecoeW), (int) round(490 * sizecoeH)));
//        centerPanel.setBounds((int) round(5 * sizecoeW), (int) round(60 * sizecoeH), 2*screenWidth - (int) round(15 * sizecoeW), (int) round(540 * sizecoeH));
        centerPanel.setBorder(etchedborder);

        wappercenterPanel=new JScrollPane(centerPanel);

        wappercenterPanel.setBounds((int) round(5 * sizecoeW), (int) round(60 * sizecoeH), screenWidth - (int) round(15 * sizecoeW), (int) round(490 * sizecoeH));
        wappercenterPanel.setBorder(etchedborder);

        add(wappercenterPanel);//modify by zzx

        /*未分配列表*/
        unassignOrderTable = new UnassignOrderTable();


        /*---------------底部面板内容-----------*/
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBounds((int) round(5 * sizecoeW), (int) round( 550 * sizecoeH), screenWidth - (int) round(15 * sizecoeW), (int) round(335 * sizecoeH));
        bottomPanel.setBorder(etchedborder);
        bottomPanel.setLayout(null);
        add(bottomPanel);

        /*---------------底部面板左侧内容-----------*/
        JPanel bottom_leftP = new JPanel();
        bottom_leftP.setBorder(titleBorderL);
        bottom_leftP.setBounds((int) round(5 * sizecoeW), (int) round(5 * sizecoeH), (int) round(1050 * sizecoeW), (int) round(280 * sizecoeH));
        bottom_leftP.setLayout(null);
        bottomPanel.add(bottom_leftP);


        JScrollPane carline_Pane = new JScrollPane(unassignOrderTable);
        carline_Pane.setBounds((int) round(5 * sizecoeW), (int) round(30 * sizecoeH), (int) round(1040 * sizecoeW), (int) round(225 * sizecoeH));
        bottom_leftP.add(carline_Pane);


        /*---------------CarlineTable右键功能菜单-----------*/
        JPopupMenu line_bagNo = new JPopupMenu();
        JMenuItem menuItem_edit = new JMenuItem("修改");
        JMenuItem menuItem_delete = new JMenuItem("删除");
        JMenuItem menuItem_distribute = new JMenuItem("分配");
        line_bagNo.add(menuItem_edit);
        line_bagNo.add(menuItem_delete);
        line_bagNo.add(menuItem_distribute);

        unassignOrderTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON3) {
                    line_bagNo.show(e.getComponent(), e.getX(), e.getY());
                    int row = unassignOrderTable.rowAtPoint(e.getPoint());
                    logger.info("--row: "+row);
                    unassignOrderTable.setRowSelectionInterval(row, row);
                }
            }
        });

        /*---------------右键功能菜单—修改-----------*/
        menuItem_edit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                AbstractButton abstractButton=(AbstractButton)e.getSource();
                System.out.println("haha"+abstractButton.getText());
                if (e.getButton() == MouseEvent.BUTTON1) {
                   logger.info("修改");

                    int selected_row = unassignOrderTable.getSelectedRow();//rowAtPoint(e.getPoint());
                    if(selected_row<0){
                        return;
                    }
                    logger.info("--selected_row: "+selected_row);
                    int selected_column = unassignOrderTable.columnAtPoint(e.getPoint());

                    String row_0 = unassignOrderTable.getValueAt(selected_row, 0).toString();
                    String row_1 = unassignOrderTable.getValueAt(selected_row, 2).toString();
                    String row_2 = unassignOrderTable.getValueAt(selected_row, 3).toString();
//                    String row_3 = unassignOrderTable.getValueAt(selected_row,4).toString();
                    String row_4 = unassignOrderTable.getValueAt(selected_row, 5).toString();
                    String row_5 = unassignOrderTable.getValueAt(selected_row, 6).toString();
                    String row_6 = unassignOrderTable.getValueAt(selected_row, 7).toString();

                    String[] row_Array = new String[]{row_0, row_1, row_2, row_4, row_5, row_6};



                    OrderModifyFrameInUnassign carMessage = new OrderModifyFrameInUnassign(sizecoeW, sizecoeH, row_Array, unassignOrderTable, selected_row,orderOperateService,MainFrame.this);
                    carMessage.setLocationRelativeTo(e.getComponent());
                    carMessage.setVisible(true);

                }
            }
        });

        /*---------------右键功能菜单—删除-----------*/
        menuItem_delete.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (e.getButton() == MouseEvent.BUTTON1) {
                   logger.info("删除");
                    int selected_row = unassignOrderTable.rowAtPoint(e.getPoint());
                    int selected_column = unassignOrderTable.columnAtPoint(e.getPoint());

                    orderOperateService.delectOrderInUnassignList(selected_column);
                    flushAssignOrderTable();
                }
            }
        });

        /*---------------右键功能菜单—分配-----------*/
        menuItem_distribute.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    logger.info("分配");
                    int selected_row = unassignOrderTable.rowAtPoint(e.getPoint());
                    String row_0 = unassignOrderTable.getValueAt(selected_row, 0).toString();
                    String row_1 = unassignOrderTable.getValueAt(selected_row, 1).toString();
                    String row_2 = unassignOrderTable.getValueAt(selected_row, 2).toString();
                    String row_3 = unassignOrderTable.getValueAt(selected_row, 3).toString();
                    String row_4 = unassignOrderTable.getValueAt(selected_row, 4).toString();
                    String row_5 = unassignOrderTable.getValueAt(selected_row, 5).toString();
                    String row_6 = unassignOrderTable.getValueAt(selected_row, 6).toString();
                    String row_7 = unassignOrderTable.getValueAt(selected_row, 7).toString();
                    String row_8 = unassignOrderTable.getValueAt(selected_row, 8).toString();

                    String[] row_Array = new String[]{row_0, row_1, row_2, row_3, row_4, row_5, row_6, row_7,row_8};
                    createDialog(row_Array, selected_row);
                }
            }
        });

        /*---------------底部面板右侧内容-----------*/
        JPanel bottom_rightP = new JPanel();
        bottom_rightP.setBorder(titleBorderR);
        bottom_rightP.setBounds((int) round(1060 * sizecoeW), (int) round(5 * sizecoeH), (int) round(515 * sizecoeW), (int) round(260 * sizecoeH));
        bottom_rightP.setLayout(null);
        bottomPanel.add(bottom_rightP);

        JLabel deliver_No = new JLabel("发货单号");
        deliver_No.setFont(font14);
        deliver_No.setBounds((int) round(20 * sizecoeW), (int) round(25 * sizecoeH), (int) round(90 * sizecoeW), (int) round(30 * sizecoeH));
        JTextField deliver_No_F = new JTextField();
        deliver_No_F.setFont(font14);
        deliver_No_F.setBounds((int) round(110 * sizecoeW), (int) round(25 * sizecoeH), (int) round(180 * sizecoeW), (int) round(30 * sizecoeH));


        deliver_No_F.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                String current_time = currentTimeLabel.getText();

                String deliver_year = current_time.substring(2,4);
                String deliver_month = current_time.substring(5,7);
                String deliver_day= current_time.substring(8,10);
                String deliver_time = deliver_year+deliver_month+deliver_day;

                deliver_No_F.setText(deliver_time);

            }
        });


        bottom_rightP.add(deliver_No);
        bottom_rightP.add(deliver_No_F);

        JLabel car_No = new JLabel("车牌号");
        car_No.setFont(font14);
        car_No.setBounds((int) round(20 * sizecoeW), (int) round(60 * sizecoeH), (int) round(90 * sizecoeW), (int) round(30 * sizecoeH));
        JTextField car_No_F = new JTextField();
        car_No_F.setFont(font14);
        car_No_F.setBounds((int) round(110 * sizecoeW), (int) round(60 * sizecoeH), (int) round(180 * sizecoeW), (int) round(30 * sizecoeH));

        bottom_rightP.add(car_No);
        bottom_rightP.add(car_No_F);

        JLabel pre_load = new JLabel("应装（吨）");
        pre_load.setFont(font14);
        pre_load.setBounds((int) round(20 * sizecoeW), (int) round(95 * sizecoeH), (int) round(85 * sizecoeW), (int) round(30 * sizecoeH));
        JTextField pre_load_F = new JTextField();   //吨数
        pre_load_F.setFont(font14);
        pre_load_F.setBounds((int) round(110 * sizecoeW), (int) round(95 * sizecoeH), (int) round(70 * sizecoeW), (int) round(30 * sizecoeH));
        JLabel divLabel = new JLabel("/");
        divLabel.setFont(font18);
        divLabel.setBounds((int) round(190 * sizecoeW), (int) round(95 * sizecoeH), (int) round(25 * sizecoeW), (int) round(30 * sizecoeH));
        JTextField pre_load_F1 = new JTextField(); //包数
        pre_load_F1.setFont(font14);
        pre_load_F1.setBounds((int) round(210 * sizecoeW), (int) round(95 * sizecoeH), (int) round(80 * sizecoeW), (int) round(30 * sizecoeH));
        JLabel baoLabel = new JLabel("包");
        baoLabel.setFont(font18);
        baoLabel.setBounds((int) round(295 * sizecoeW), (int) round(95 * sizecoeH), (int) round(30 * sizecoeW), (int) round(30 * sizecoeH));

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
                int preloadbao = (int) preload_bao;
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
        cement_type.setBounds((int) round(20 * sizecoeW), (int) round(130 * sizecoeH), (int) round(80 * sizecoeW), (int) round(30 * sizecoeH));
        bottom_rightP.add(cement_type);

        String[] cement_list = new String[]{
                " ","超丰P.O42.5包装", "超丰P.O42.5纸袋", "虎丰P.O42.5包装", "之江P.O42.5包装",
                "超丰P.C32.5R包装", "超丰P.C32.5R纸袋", "虎丰P.C32.5R包装", "之江P.C32.5R包装","虎丰M32.5包装"};
        JComboBox<String> cement_type_box = new JComboBox<String>(cement_list);
        cement_type_box.setFont(font14);
        cement_type_box.setBounds((int) round(110 * sizecoeW), (int) round(130 * sizecoeH), (int) round(180 * sizecoeW), (int) round(30 * sizecoeH));
        bottom_rightP.add(cement_type_box);


        cement_type_box.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // 只处理选中的状态
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println("选中: " + cement_type_box.getSelectedIndex() + " = " + cement_type_box.getSelectedItem());
                }
            }
        });


        JLabel custom_No = new JLabel("客户代码");
        custom_No.setFont(font14);
        custom_No.setBounds((int) round(20 * sizecoeW), (int) round(165 * sizecoeH), (int) round(80 * sizecoeW), (int) round(30 * sizecoeH));
        JTextField custom_No_F = new JTextField();
        custom_No_F.setFont(font14);
        custom_No_F.setBounds((int) round(110 * sizecoeW), (int) round(165 * sizecoeH), (int) round(180 * sizecoeW), (int) round(30 * sizecoeH));

        bottom_rightP.add(custom_No);
        bottom_rightP.add(custom_No_F);

        custom_No_F.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                System.out.println(custom_No_F.getText());
                if(custom_No_F.getText().equals("0")||custom_No_F.getText().equals("")){
                    custom_No_F.setText("0");
                }
            }
        });

        JLabel cement_batch = new JLabel("水泥批号");
        cement_batch.setFont(font14);
        cement_batch.setBounds((int) round(20 * sizecoeW), (int) round(200 * sizecoeH), (int) round(80 * sizecoeW), (int) round(30 * sizecoeH));
        JTextField cement_batch_F = new JTextField();
        cement_batch_F.setFont(font14);
        cement_batch_F.setBounds((int) round(110 * sizecoeW), (int) round(200 * sizecoeH), (int) round(180 * sizecoeW), (int) round(30 * sizecoeH));

        bottom_rightP.add(cement_batch);
        bottom_rightP.add(cement_batch_F);

        JButton add_Btn = new JButton("新增");
        add_Btn.setBorder(raisedBevelBorder);
        add_Btn.setBackground(new Color(238, 238, 238));
        add_Btn.setFont(font22);
        add_Btn.setBounds((int) round(350 * sizecoeW), (int) round(25 * sizecoeH), (int) round(100 * sizecoeW), (int) round(70 * sizecoeH));
        bottom_rightP.add(add_Btn);

        /*---------------新增按钮功能-----------*/
        add_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    car_No_text = car_No_F.getText();
                    car_lane_text = "null";
                    cement_type_text = cement_type_box.getSelectedItem().toString();
//                cement_type_text = cement_type_box.getSelectedItem().toString();
                    pre_load_text = pre_load_F1.getText();
                    real_load_text = "0";
                    custom_No_text = custom_No_F.getText();
                    cement_batch_text = cement_batch_F.getText();
                    deliver_No_text = deliver_No_F.getText();
//                "豫P2B369","超峰P.C32.5","600","0","719","CCR93225","1908120094"
                    PackManulOrder order = new PackManulOrder();
                    logger.info("新增"+deliver_No_text);
                    order.setBillcode(deliver_No_text);
                    order.setVehicleno(car_No_text);
                    order.setTotal_amount(Integer.parseInt(pre_load_text));
                    order.setAlready_amount(0);
                    order.setMaterial(cement_type_text);
                    order.setConsumer_code(custom_No_text);
                    order.setBatch_no(cement_batch_text);
                    order.setLaneno(null);
                    order.setLaneno_alias(null);
                    order.setCreate_time(Instant.now());
                    order.setPk_delivery(CodeHelper.getUUID());

                    orderOperateService.addOrderToUnassignList(order);
                    MainFrame.this.flushAssignOrderTable();
                } catch (Exception e1) {
                    logger.error(e);
                }
//

            }
        });

        /*---------------车牌开头文字选择-----------*/
        JPanel carbtnPanel = new JPanel();
        carbtnPanel.setLayout(gridLayout2);
        carbtnPanel.setBounds((int) round(350 * sizecoeW), (int) round(100 * sizecoeH), (int) round(100 * sizecoeW), (int) round(130 * sizecoeH));
        bottom_rightP.add(carbtnPanel);

        JButton zhe_Btn = new JButton("浙");
        JButton gan_Btn = new JButton("赣");
        JButton xiang_Btn = new JButton("湘");
        JButton yue_Btn = new JButton("粤");
        JButton yu_Btn = new JButton("豫");
        JButton wan_Btn = new JButton("皖");
        JButton e_Btn = new JButton("鄂");
        JButton gui_Btn = new JButton("桂");

        JButton[] carbtnArray = new JButton[]{zhe_Btn, gan_Btn, xiang_Btn, yue_Btn, yu_Btn, wan_Btn, e_Btn, gui_Btn};
        for (JButton car_button : carbtnArray) {
            car_button.setForeground(Color.BLUE);
            car_button.setBorder(raisedBevelBorder);
            car_button.setBackground(new Color(238, 238, 238));
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


        build();
    }

    /*---------------创建分配包装线车道的对话框-----------*/
    private void createDialog(String[] message_Arr, int selected_row) {
        GridLayout gridLayout = new GridLayout(1, 3, (int) round(5 * sizecoeW), (int) round(0 * sizecoeH));
        Border raisedBevelBorder1 = BorderFactory.createRaisedBevelBorder();
        Border etchedBorderL1 = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Font font16 = new Font("宋体", Font.BOLD, 16);

        int countPackMahine=orderOperateService.getPackMAchineGroup().getProductLineMaps().get(gproductline).getDeviceMaps().size();
        JDialog dialog = new JDialog();
        dialog.setTitle("分配包装线车道");
        dialog.setSize((int) round(250*countPackMahine * sizecoeW), (int) round(140 * sizecoeH));
        dialog.setLocationRelativeTo(this.getContentPane());
        dialog.setLayout(gridLayout);


        PackMAchineGroup.ProductLine productLine =orderOperateService.getPackMAchineGroup().getProductLineMaps().get(gproductline);

        for(PackMachine packMachine:productLine.getDeviceMaps().values()){
            JLabel lineP_L1 = new JLabel(packMachine.getPackerConfigure().getProductLine()+"线"+packMachine.getPackerConfigure().getCommentZh(),JLabel.CENTER);
            lineP_L1.setFont(font16);
            lineP_L1.setBounds((int) round(0 * sizecoeW), (int) round(5 * sizecoeH), (int) round(250 * sizecoeW), (int) round(40 * sizecoeH));

            JPanel lineP_1 = new JPanel();
            lineP_1.setLayout(null);
            lineP_1.setPreferredSize(new Dimension((int)round(250*1 * sizecoeW),(int) round(140 * sizecoeH)));
            lineP_1.setBorder(etchedBorderL1);
            dialog.add(lineP_1);
            lineP_1.add(lineP_L1);

            int i=0;
            for(CarLane carLane:packMachine.getPackerConfigure().getCarLanes()){
                Carline_Btn carlineP_L1_btn1 = new Carline_Btn( message_Arr,packMachine.getPackerConfigure().getProductLine(),carLane.getLaneIndex(), packMachine.getPackerConfigure().getDeviceOrder(), selected_row, dialog);
                lineP_L1_btn1 = new JButton("车道口" + carLane.getLaneIndex());
                lineP_L1_btn1.setBounds((int) round(10 * sizecoeW)+100*(i++), (int) round(50 * sizecoeH), (int) round(85 * sizecoeW), (int) round(35 * sizecoeH));
                lineP_L1_btn1.addActionListener(carlineP_L1_btn1);
                lineP_1.add(lineP_L1_btn1);
                lineP_L1_btn1.setBorder(raisedBevelBorder1);
                lineP_L1_btn1.setBackground(new Color(238, 238, 238));
            }

        }


//        JLabel lineP_L2 = new JLabel(machineIfo_2.getName());
//        lineP_L2.setFont(font16);
//        lineP_L2.setBounds((int) round(55 * sizecoeW), (int) round(5 * sizecoeH), (int) round(90 * sizecoeW), (int) round(40 * sizecoeH));
//
//
//        Carline_Btn carlineP_L2_btn3 = new Carline_Btn(car_Table2, message_Arr, machineIfo_2.getName(), machineIfo_2.getLaneno1_alias(), selected_row, dialog);
//        lineP_L2_btn3 = new JButton("车道口"+machineIfo_2.getLaneno1_alias());
//        lineP_L2_btn3.setBounds((int) round(5 * sizecoeW), (int) round(50 * sizecoeH), (int) round(85 * sizecoeW), (int) round(35 * sizecoeH));
//        lineP_L2_btn3.addActionListener(carlineP_L2_btn3);
//
//        Carline_Btn carlineP_L2_btn4 = new Carline_Btn(car_Table2, message_Arr, machineIfo_2.getName(), machineIfo_2.getLaneno2_alias(), selected_row, dialog);
//        lineP_L2_btn4 = new JButton("车道口"+machineIfo_2.getLaneno2_alias());
//        lineP_L2_btn4.setBounds((int) round(95 * sizecoeW), (int) round(50 * sizecoeH), (int) round(85 * sizecoeW), (int) round(35 * sizecoeH));
//        lineP_L2_btn4.addActionListener(carlineP_L2_btn4);
//        lineP_2.add(lineP_L2);
//        lineP_2.add(lineP_L2_btn3);
//        lineP_2.add(lineP_L2_btn4);
//
//        JLabel lineP_L3 = new JLabel(machineIfo_3.getName());
//        lineP_L3.setFont(font16);
//        lineP_L3.setBounds((int) round(55 * sizecoeW), (int) round(5 * sizecoeH), (int) round(90 * sizecoeW), (int) round(40 * sizecoeH));
//
//        Carline_Btn carlineP_L3_btn5 = new Carline_Btn(car_Table3, message_Arr, machineIfo_3.getName(), machineIfo_3.getLaneno1_alias(), selected_row, dialog);
//        lineP_L3_btn5 = new JButton("车道口"+machineIfo_3.getLaneno1_alias());
//        lineP_L3_btn5.setBounds((int) round(5 * sizecoeW), (int) round(50 * sizecoeH), (int) round(85 * sizecoeW), (int) round(35 * sizecoeH));
//        lineP_L3_btn5.addActionListener(carlineP_L3_btn5);
//
//        Carline_Btn carlineP_L3_btn6 = new Carline_Btn(car_Table3, message_Arr, machineIfo_3.getName(), machineIfo_3.getLaneno2_alias(), selected_row, dialog);
//        lineP_L3_btn6 = new JButton("车道口"+machineIfo_3.getLaneno2_alias());
//        lineP_L3_btn6.setBounds((int) round(95 * sizecoeW), (int) round(50 * sizecoeH), (int) round(85 * sizecoeW), (int) round(35 * sizecoeH));
//        lineP_L3_btn6.addActionListener(carlineP_L3_btn6);
//        lineP_3.add(lineP_L3);
//        lineP_3.add(lineP_L3_btn5);
//        lineP_3.add(lineP_L3_btn6);

//        JButton[] lineP_btnArray = new JButton[]{lineP_L1_btn1, lineP_L1_btn2, lineP_L2_btn3, lineP_L2_btn4, lineP_L3_btn5, lineP_L3_btn6};
//        for (JButton lineP_btn : lineP_btnArray) {
//            lineP_btn.setBorder(raisedBevelBorder1);
//            lineP_btn.setBackground(new Color(238, 238, 238));
//        }
        dialog.setVisible(true);
        dialog.setAlwaysOnTop(true);

        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            public void run() {

                SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            MainFrame.this.flushPacksExecuteCondition();
                        }
                });


            }
        }, 1000, 2000);// 设定指定的时间time,此处为20000毫秒
    }

    public int getGproductline() {
        return gproductline;
    }

    public void setGproductline(int gproductline) {
        this.gproductline = gproductline;
    }

    private class Carline_Btn implements ActionListener {
//        private AssignOrderTable assignOrderTable;
        private String[] messagerow;
        private int carlaneindex;
        private int selectedrow;
        private JDialog dialog;
        int plindex;
        int packmachineIndex;

        public Carline_Btn( String[] message_row, int plindex, int carlaneindex, int packmachineIndex,int selectedrow, JDialog dialog) {
//            this.assignOrderTable = assignOrderTable;
            this.messagerow = message_row;
            this.carlaneindex = carlaneindex;//1 2

            this. plindex=plindex;
            this.packmachineIndex=packmachineIndex;
            this.selectedrow = selectedrow;
            this.dialog = dialog;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

//            Pack_ManulOrder order = new Pack_ManulOrder();
            logger.info("bag_name: "+ this.carlaneindex);

            orderOperateService.assignOrderInUnassignList(this.selectedrow,this.plindex,this.packmachineIndex,this.carlaneindex);
//            failedDialog(bag_name,carlaneindex);//分配报错
            flushAssignOrderTable();




//            for(Pack_Machine pack_machine:Machine_Manger.getInstance().getPack_machines().values()){
//
//                if(pack_machine.getPackage_machine_name().equals(bag_name)){
//                    unassign.setProduct_line_no(pack_machine.getProduct_line());
//                }
//            }


//            logger.info("alise" + unassign.getLaneno_alias());


//            CenterPanel[] center_list = new CenterPanel[]{first_Panel, second_Panel, third_Panel};
//            CenterPanel pick_center = null;
//
//            for (CenterPanel center : center_list) {
//                if (center.getName().equals(bag_name)) {
//                    pick_center = center;
//
//                }
//            }
//
//            if (pick_center != null) {
//                //
//                logger.info("classno"+pick_center.getClassSelect().getSelection().getActionCommand());
//                unassign.setClass_no(pick_center.getClassSelect().getSelection().getActionCommand());
//            }
//
//
//            boolean operation = false;
//            for (Pack_Machine pack_machine : pack_machines.values()) {
//
//                if (pack_machine.getPackage_machine_name().equals(bag_name)) {
//                    unassign.setProduct_line_no(pack_machine.getProduct_line());
//                    unassign.setAssign_time(Instant.now());
//                    operation = pack_machine.addOrder((carlaneindex), unassign);//手动订单数据库在次插入
//
//                }
//            }
//
//            if (operation) {
//                //
//                logger.info("assign order success");
//                assignOrderTable.defaultModel.insertRow(assignOrderTable.defaultModel.getRowCount(), new Object[]{messagerow[0], carlaneindex, messagerow[2], messagerow[3], messagerow[4], messagerow[5], messagerow[6], messagerow[7],messagerow[8]});
//                unassignOrderTable.defaultModel.removeRow(selectedrow);
//                MainFrame.getinstance().flushAssignOrderTable();
//            } else {
//                failedDialog(bag_name, carlaneindex);
//            }

//            int rows = assignOrderTable.getRowCount();
//            if(rows==0){
//                assignOrderTable.defaultModel.insertRow(0,new Object[]{messagerow[0],carlaneindex,messagerow[2],messagerow[3],messagerow[4],messagerow[5],messagerow[6],messagerow[7]});
//                unassignOrderTable.defaultModel.removeRow(selectedrow);
//            }
//            if(rows==1){
//                String rows_carlane = assignOrderTable.getValueAt(0,1).toString();
//                if(rows_carlane.equals(carlaneindex)){
//                    failedDialog(bag_name,carlaneindex);
//                }else {
//                    assignOrderTable.defaultModel.insertRow(0,new Object[]{messagerow[0],carlaneindex,messagerow[2],messagerow[3],messagerow[4],messagerow[5],messagerow[6],messagerow[7]});
//                    unassignOrderTable.defaultModel.removeRow(selectedrow);
//                }
//            }
//            if(rows==2){
//                failedDialog(bag_name,carlaneindex);
//            }
            dialog.dispose();
        }

    }

    private void failedDialog(String bagline, int carline) {
        Border raisedBevelBorder = BorderFactory.createRaisedBevelBorder();
        JDialog dialog = new JDialog();
        dialog.setTitle("分配包装线车道");
        dialog.setSize((int) round(300 * sizecoeW), (int) round(160 * sizecoeH));
        dialog.setLocationRelativeTo(this.getContentPane());
        dialog.setLayout(null);
        JLabel message_L = new JLabel(bagline + carline + "号车道已有排队车辆，分配失败！", JLabel.CENTER);
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





    public void flushUnasignOrder() {


        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                int rows = unassignOrderTable.getRowCount();
                for (int i = 0; i < rows; ++i) {
                    unassignOrderTable.defaultModel.removeRow(0);
                }
                java.util.List<Order> unssigned_queue = orderOperateService.getUnassignOrderList().getAllUnassignOrders();
                for (int i = 0; i < unssigned_queue.size(); ++i) {
                    Order order = unssigned_queue.get(i);
                    unassignOrderTable.defaultModel.insertRow(i, new Object[]{order.getVehicleno(), order.getLaneno_alias() == null ? "null" : order.getLaneno_alias().toString(), order.getMaterial(), Integer.valueOf(order.getTotal_amount()).toString(),Integer.valueOf(order.getAlready_amount()).toString(), order.getConsumer_code(), order.getBatch_no(), order.getBillcode(),order.getPk_delivery()});
                }
            }
        });



    }


    public  void flushAssignOrderTable(){

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                logger.info("flushAssignOrderTable");
                flushUnasignOrder();
                for(CenterPanel centerPanel:centerPanels){
                    centerPanel.flush_assign_order();
                }
            }
        });



    }

    public void flushPacksExecuteCondition() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                for(CenterPanel centerPanel:centerPanels){
                    centerPanel.flushPackMahineExecuteOrder();
                }
            }
        });


    }


    public static void main(String[] args) {
        String appdir = System.getProperty("user.dir");
        System.setProperty("OPC_LOG_HOME", appdir + "/log");
        PropertyConfigurator.configure(ClassLoader.getSystemResource("resource/log4j.properties"));

        ExecutorService executorService = Executors.newCachedThreadPool();

//        MainFrame mainFrame = MainFrame.getinstance();
//        mainFrame.setTitle("超峰包装发货系统");
//        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//        mainFrame.setVisible(true);
//        mainFrame.setResizable(false);
//        mainFrame.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                int i = JOptionPane.showConfirmDialog(null, "确定要退出系统吗？", "退出系统", JOptionPane.YES_NO_OPTION);
//                if (i == JOptionPane.YES_OPTION) {
//                    System.exit(0);
//                }
//            }
//        });
//
//        for (Pack_Machine pack_machine : Machine_Manger.getInstance().getPack_machines().values()) {
//            executorService.execute(new Runnable() {
//                @Override
//                public void run() {
//                    pack_machine.execute_send_msg();
//                }
//            });
//
//
////           pack_machine.execute_check_is_need_job();
//            executorService.execute(new Runnable() {
//                @Override
//                public void run() {
//                    pack_machine.execute_check_is_need_job();
//                }
//            });
//
//        }

//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                while (!Thread.currentThread().isInterrupted()){
//                    Pack_Operation_List_Manager.getinstance().check_timeout();
//                    try {
//                        TimeUnit.SECONDS.sleep(2);
//                    } catch (InterruptedException e) {
//                        logger.error(e);
//                    }
//                }
//
//            }
//        });


//        java.util.Timer timer = new java.util.Timer();
//        timer.schedule(new TimerTask() {
//            public void run() {
//
//                SwingUtilities.invokeLater(new Runnable() {
//                    public void run() {
//                        mainFrame.flushPacksExecuteCondition();
//                    }
//                });
//
//
//            }
//        }, 1000, 2000);// 设定指定的时间time,此处为20000毫秒


//        try {
//            new EchoServer(2000).start();
//        } catch (Exception e) {
//            logger.error(e);
//        }

    }

}