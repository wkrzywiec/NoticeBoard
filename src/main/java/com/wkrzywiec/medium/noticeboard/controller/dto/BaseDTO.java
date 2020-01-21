package com.wkrzywiec.medium.noticeboard.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseDTO {

    @ApiModelProperty(value = "The id of the object")
    private Long id;

    @ApiModelProperty(value = "The date when the object was created")
    private Date creationDate;
}
