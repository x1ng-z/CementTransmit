package hs.dao;

import hs.modle.MaterialName;
import hs.modle.PackerConfigure;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zzx
 * @version 1.0
 * @date 2020/2/13 12:22
 */
public interface Packer {
    @MapKey("deviceOrder")
    java.util.Map<Integer,PackerConfigure> getPackerConfigure();
    List<MaterialName> getMaterialName();
    void addPackConfigure(@Param("packerConfigure") PackerConfigure packerConfigure);
    void updatePackConfigure(@Param("packerConfigure") PackerConfigure packerConfigure);
    void delectPackConfigure(@Param("id") int id);


}
