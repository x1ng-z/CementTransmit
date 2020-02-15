package hs.dao;

import hs.modle.PackerConfigure;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

/**
 * @author zzx
 * @version 1.0
 * @date 2020/2/13 12:22
 */
public interface Packer {
    @MapKey("id")
    java.util.Map<Integer,PackerConfigure> getPackerConfigure();
    void addPackConfigure(@Param("packerConfigure") PackerConfigure packerConfigure);
    void updatePackConfigure(@Param("packerConfigure") PackerConfigure packerConfigure);
    void delectPackConfigure(@Param("id") int id);
}
