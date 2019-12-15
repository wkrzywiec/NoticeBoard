package com.wkrzywiec.medium.noticeboard.controller.dto;

import lombok.experimental.SuperBuilder;

import java.util.Date;

@SuperBuilder
public abstract class BaseDTO {

    public Date creationDate;
}
