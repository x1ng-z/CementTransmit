package hs;


import hs.configuration.SpringAnnotionConfigure;
import hs.dao.Packer;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zzx on 2020-2-10
 */
public class Bootstrap {
    public static void main(String[] args) {

        ApplicationContext cxt=new AnnotationConfigApplicationContext(SpringAnnotionConfigure.class);
        Packer packer=cxt.getBean(Packer.class);
        packer.getPackerConfigure();
        EnumOrdinalTypeHandler enumOrdinalTypeHandler;

        ExecutorService service=Executors.newCachedThreadPool();
        //test threadlocal
    }
}
