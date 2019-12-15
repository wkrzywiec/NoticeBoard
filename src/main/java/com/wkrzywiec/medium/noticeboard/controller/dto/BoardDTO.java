package com.wkrzywiec.medium.noticeboard.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class BoardDTO extends BaseDTO {

    private String name;

    private List<NoticeDTO> noticeList;
}
