package hs;


import hs.configuration.SpringAnnotionConfigure;
import hs.dao.Packer;
import hs.modle.PackMAchineGroup;
import hs.modle.PackerConfigure;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zzx on 2020-2-10
 */
public class Bootstrap {
    public static void main(String[] args) {
        ApplicationContext cxt=new AnnotationConfigApplicationContext(SpringAnnotionConfigure.class);
        Packer packer=cxt.getBean(Packer.class);
        Map<Integer,PackerConfigure> ss =packer.getPackerConfigure();
        PackerConfigure configure=ss.get(1);
        System.out.println(ss.get(1).getCommentZh());
        ExecutorService service=Executors.newCachedThreadPool();

        PackMAchineGroup packMAchineGroup=cxt.getBean(PackMAchineGroup.class);
        //test threadlocal
        System.out.println("end-zzx");

    }
}
