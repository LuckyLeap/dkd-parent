package com.dkd.manage.domain.vo;

import com.dkd.manage.domain.Node;
import com.dkd.manage.domain.Partner;
import com.dkd.manage.domain.Region;

public class NodeVo extends Node {
    // 设备数量
    private int vmCount;

    // 区域
    private Region region;

    // 合作商
    private Partner partner;

    public int getVmCount() {
        return vmCount;
    }

    public void setVmCount(int vmCount) {
        this.vmCount = vmCount;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public String toString() {
        return "NodeVo{" +
                "vmCount=" + vmCount +
                ", region=" + region +
                ", partner=" + partner +
                '}';
    }
}