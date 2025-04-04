package com.dkd.manage.domain.vo;

import com.dkd.manage.domain.Task;
import com.dkd.manage.domain.TaskType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskVo extends Task {
    // 工单类型
    private TaskType taskType;

    @Override
    public String toString() {
        return "TaskVo{" +
                "taskType=" + taskType +
                '}';
    }
}