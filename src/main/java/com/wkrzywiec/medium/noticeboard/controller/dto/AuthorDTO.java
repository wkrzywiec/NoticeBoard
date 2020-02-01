package com.wkrzywiec.medium.noticeboard.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO extends BaseDTO {

    private String firstName;

    private String lastName;
}
