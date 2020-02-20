package hs.configuration;

import hs.dao.Packer;
import hs.modle.PackMAchineGroup;
import hs.modle.PackerConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zzx
 * @version 1.0
 * @date 2020/2/10 16:25
 */
@Configuration
@ImportResource("classpath:application.xml")
public class SpringAnnotionConfigure {
    @Autowired
    public Packer packer;

    /**
     * 创建包装机管理类，并且将包装机根据配置信息进行构建
     * @Link PackMAchineGroup 内部包含设备线别、线别中包含包装机设备；
     * */
    @Bean("packMAchineGroup")
    public PackMAchineGroup buildPackMachineGroup(){
        return PackMAchineGroup.build(packer.getPackerConfigure());
    }


}