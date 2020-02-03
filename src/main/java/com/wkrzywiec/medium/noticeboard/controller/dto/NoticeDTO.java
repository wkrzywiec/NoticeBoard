package com.wkrzywiec.medium.noticeboard.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Notice")
public class NoticeDTO extends BaseDTO {

    @ApiModelProperty(value = "Notice title")
    private String title;

    @ApiModelProperty(value = "Notice detailed description")
    private String description;

    @ApiModelProperty(value = "A Person who created this Notice")
    private AuthorDTO author;
}
