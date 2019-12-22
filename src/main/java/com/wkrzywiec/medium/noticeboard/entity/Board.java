package com.wkrzywiec.medium.noticeboard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseEntity {

    @Column
    private String title;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Notice> noticeList;
}
