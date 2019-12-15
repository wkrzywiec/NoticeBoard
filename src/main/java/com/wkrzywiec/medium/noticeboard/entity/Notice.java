package com.wkrzywiec.medium.noticeboard.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@SuperBuilder
public class Notice extends BaseEntity {

    @Column
    private String title;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
}
