package com.wkrzywiec.medium.noticeboard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Notice extends BaseEntity {

    @Column
    private String title;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
}
