package com.dkd.manage.domain.vo;

import com.dkd.manage.domain.Channel;
import com.dkd.manage.domain.Sku;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelVo extends Channel {
    // 商品
    private Sku sku;
    // 销量字段
    private Long sales;

    @Override
    public String toString() {
        return "ChannelVo{" +
                "sku=" + sku +
                ", sales=" + sales +
                '}';
    }
}