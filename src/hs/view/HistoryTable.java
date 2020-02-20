package hs.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

import static java.lang.Math.round;

public class HistoryTable extends JTable {

    private boolean DEBUG=true;
    private String[] TitleName={"车牌号","品名","水泥批号","装车吨数","装车包数","装车开始时间","装车结束时间"};
    private Object[][] data=null;//new Object[][];
    private JScrollPane Scroll_componet;

    public DefaultTableModel defaultModel=new DefaultTableModel(data,TitleName) {

        public boolean isCellEditable(int row, int col) {

            return false;
        }
    };

    public HistoryTable(){
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;

        String sw = String.valueOf(screenWidth);
        Double swidth = Double.parseDouble(sw);
        Double sizecoeW = swidth/1600.0;

        setModel(defaultModel);
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        Scroll_componet=new JScrollPane(this);
        setAutoCreateRowSorter(false);
        setRowHeight(45);
        setFont(new Font("宋体", Font.PLAIN, (int) round(14.0*sizecoeW)));
        getTableHeader().setFont(new Font("宋体", Font.PLAIN, (int) round(18.0*sizecoeW)));

        TableColumn column = null;
        for (int i = 0; i < TitleName.length; i++) {
            column = this.getColumnModel().getColumn(i);
            switch(i){
                case 0:  //"车牌号"
                    column.setPreferredWidth(200);
                    break;
                case 1:  //"品名"
                    column.setPreferredWidth(250);
                    break;
                case 2:  //"水泥批号"
                    column.setPreferredWidth(250);
                    break;
                case 3:  //"装车吨数"
                    column.setPreferredWidth(200);
                    break;
                case 4:  //"装车包数"
                    column.setPreferredWidth(200);
                    break;
                case 5:  //"装车开始时间"
                    column.setPreferredWidth(200);
                    break;
                case 6:  //"装车结束时间"
                    column.setPreferredWidth(200);
                    break;
                default:
                    break;
            }
        }
        //todo 插入
//        defaultModel.insertRow(0, new Object[]{"豫P2B369","超峰P.C32.5","超峰P.C32.5","4","80","2019-08-24","2019-08-24"});
//        defaultModel.insertRow(0, new Object[]{"豫P2B369","超峰P.C32.5","超峰P.C32.5","4","80","2019-08-24","2019-08-24"});
//        defaultModel.insertRow(0, new Object[]{"豫P2B369","超峰P.C32.5","超峰P.C32.5","4","80","2019-08-24","2019-08-24"});
//        defaultModel.insertRow(0, new Object[]{"豫P2B369","超峰P.C32.5","超峰P.C32.5","4","80","2019-08-24","2019-08-24"});
//        defaultModel.insertRow(0, new Object[]{"豫P2B369","超峰P.C32.5","超峰P.C32.5","4","80","2019-08-24","2019-08-24"});
//        defaultModel.insertRow(0, new Object[]{"豫P2B369","超峰P.C32.5","超峰P.C32.5","4","80","2019-08-24","2019-08-24"});
//        defaultModel.insertRow(0, new Object[]{"豫P2B369","超峰P.C32.5","超峰P.C32.5","4","80","2019-08-24","2019-08-24"});
//        defaultModel.insertRow(0, new Object[]{"豫P2B369","超峰P.C32.5","超峰P.C32.5","4","80","2019-08-24","2019-08-24"});
//        defaultModel.insertRow(0, new Object[]{"豫P2B369","超峰P.C32.5","超峰P.C32.5","4","80","2019-08-24","2019-08-24"});
//        defaultModel.insertRow(0, new Object[]{"豫P2B369","超峰P.C32.5","超峰P.C32.5","4","80","2019-08-24","2019-08-24"});
//        defaultModel.insertRow(0, new Object[]{"豫P2B369","超峰P.C32.5","超峰P.C32.5","4","80","2019-08-24","2019-08-24"});
//        defaultModel.insertRow(0, new Object[]{"豫P2B369","超峰P.C32.5","超峰P.C32.5","4","80","2019-08-24","2019-08-24"});
//        //获取行
//        defaultModel.getRowCount();
    }
    public JScrollPane get_JScrollPane(){
        return Scroll_componet;
    }


}

