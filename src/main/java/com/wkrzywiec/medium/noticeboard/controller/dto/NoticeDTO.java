package com.wkrzywiec.medium.noticeboard.controller.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class NoticeDTO extends BaseDTO {

    private String title;

    private String description;

    private BoardDTO board;

    private PersonDTO person;
}
