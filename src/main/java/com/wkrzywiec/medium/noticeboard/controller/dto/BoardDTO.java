package com.wkrzywiec.medium.noticeboard.controller.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class BoardDTO extends BaseDTO {

    private String name;

    private List<NoticeDTO> noticeList;
}
