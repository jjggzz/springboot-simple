package com.springboot.simple.jdbc.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * jdbc配置
 * @author JGZ
 * CreateTime 2020/3/29 10:23
 */
@Component
@ConfigurationProperties(prefix = "jdbc")
public class JdbcProperties {

    private MasterProperties master;

    private List<SlaveProperties> slaveList;

    public MasterProperties getMaster() {
        return master;
    }

    public void setMaster(MasterProperties master) {
        this.master = master;
    }

    public List<SlaveProperties> getSlaveList() {
        return slaveList;
    }

    public void setSlaveList(List<SlaveProperties> slaveList) {
        this.slaveList = slaveList;
    }
}
