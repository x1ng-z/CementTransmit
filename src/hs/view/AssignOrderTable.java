package hs.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

import static java.lang.Math.round;

public class AssignOrderTable extends JTable {

    private boolean DEBUG=true;
    private String[] TitleName={"车牌号","车道","水泥品种","应装包数","已装包数","客户代码","水泥批号","发货单号","pk_delivery"};
    private Object[][] data=null;//new Object[][];

    public DefaultTableModel defaultModel=new DefaultTableModel(data,TitleName) {
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    };

    public AssignOrderTable(){
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;

        String sw = String.valueOf(screenWidth);
        Double swidth = Double.parseDouble(sw);
        Double sizecoeW = swidth/1600.0;

        setModel(defaultModel);
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        setAutoCreateRowSorter(false);
        setRowHeight(25);
        setFont(new Font("宋体", Font.PLAIN, (int) round(14.0*sizecoeW)));
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setFont(new Font("宋体", Font.PLAIN, (int) round(14.0*sizecoeW)));

        TableColumn column = null;
        for (int i = 0; i < TitleName.length; i++) {
            column = this.getColumnModel().getColumn(i);
            switch(i){
                case 0:  //"车牌号"
                    column.setPreferredWidth(100);
                    break;
                case 1:  //"道口"
                    column.setPreferredWidth(100);
                    break;
                case 2:  //"水泥品种"
                    column.setPreferredWidth(100);
                    break;
                case 3:  //"应装包数"
                    column.setPreferredWidth(100);
                    break;
                case 4:  //"已装包数"
                    column.setPreferredWidth(100);
                    break;
                case 5:  //"客户代码"
                    column.setPreferredWidth(100);
                    break;
                case 6:  //"水泥批号"
                    column.setPreferredWidth(100);
                    break;
                case 7:  //"发货单号"
                    column.setPreferredWidth(100);
                    break;
                default:
                    break;
            }
        }
//        defaultModel.insertRow(0, new Object[]{"豫P2B369",partState,partTime});
    }

}

