package com.dkd.manage.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AbnormalDeviceDto implements Serializable {
    private LocalDateTime updateTime;
    private String innerCode;
    private String addr;
}
