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
public class BoardDTO extends BaseDTO {

    private String title;

    private List<NoticeDTO> noticeList;
}
