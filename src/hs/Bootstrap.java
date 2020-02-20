package hs;


import hs.configuration.SpringAnnotionConfigure;
import hs.dao.Packer;
import hs.modle.PackMAchineGroup;
import hs.modle.PackerConfigure;
import hs.service.OrderOperateService;
import hs.view.MainFrame;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zzx on 2020-2-10
 */
public class Bootstrap {
    public static void main(String[] args) {
        ApplicationContext cxt=new AnnotationConfigApplicationContext(SpringAnnotionConfigure.class);;
        OrderOperateService orderOperateService=cxt.getBean(OrderOperateService.class);
        MainFrame mainFrame =cxt.getBean(MainFrame.class);
        mainFrame.setTitle("超峰包装发货系统");
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "确定要退出系统吗？", "退出系统", JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        System.out.println(1);
        while (true);
    }
}
