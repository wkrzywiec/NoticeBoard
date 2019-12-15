package com.wkrzywiec.medium.noticeboard.controller.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class PersonDTO extends BaseDTO {

    private String firstName;

    private String lastName;

    private List<NoticeDTO> noticeList;
}
