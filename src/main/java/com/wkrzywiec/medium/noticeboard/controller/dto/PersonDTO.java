package com.wkrzywiec.medium.noticeboard.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO extends BaseDTO {

    private String firstName;

    private String lastName;

    private List<NoticeDTO> noticeList;
}
