package com.dkd.manage.domain.vo;

import com.dkd.manage.domain.Region;

public class RegionVo extends Region {
    //点位数量
    private Integer nodeCount;

    public Integer getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(Integer nodeCount) {
        this.nodeCount = nodeCount;
    }

    @Override
    public String toString() {
        return "RegionVo{" +
                "nodeCount=" + nodeCount +
                '}';
    }
}