package com.wkrzywiec.medium.noticeboard.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class PersonDTO extends BaseDTO {

    private String firstName;

    private String lastName;

    private List<NoticeDTO> noticeList;
}
