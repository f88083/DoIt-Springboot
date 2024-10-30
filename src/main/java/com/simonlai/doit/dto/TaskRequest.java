package com.simonlai.doit.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskRequest {

    private String title;

    private String description;

    private Integer status;

    private LocalDateTime dueDate;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;
}
