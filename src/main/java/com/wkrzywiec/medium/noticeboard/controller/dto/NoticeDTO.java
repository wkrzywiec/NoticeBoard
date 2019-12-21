package com.wkrzywiec.medium.noticeboard.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDTO extends BaseDTO {

    private String title;

    private String description;

    private BoardDTO board;

    private PersonDTO person;
}
