package com.wkrzywiec.medium.noticeboard.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoticeDTO extends BaseDTO {

    private String title;

    private String description;

    private BoardDTO board;

    private PersonDTO person;
}
