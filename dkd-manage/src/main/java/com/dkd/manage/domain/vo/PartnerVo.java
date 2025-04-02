package com.dkd.manage.domain.vo;

import com.dkd.manage.domain.Partner;

public class PartnerVo extends Partner {
    // 区域点位数量
    private Integer nodeCount;

    public Integer getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(Integer nodeCount) {
        this.nodeCount = nodeCount;
    }

    @Override
    public String toString() {
        return "PartnerVo{" +
                "nodeCount=" + nodeCount +
                '}';
    }
}