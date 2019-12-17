package com.wkrzywiec.medium.noticeboard.controller.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@SuperBuilder
@Data
public abstract class BaseDTO {

    private Long id;

    private Date creationDate;
}
