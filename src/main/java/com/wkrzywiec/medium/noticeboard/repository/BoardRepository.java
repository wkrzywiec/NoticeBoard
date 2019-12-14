package com.wkrzywiec.medium.noticeboard.repository;

import com.wkrzywiec.medium.noticeboard.entity.Board;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends CrudRepository<Board, Long> {
}
