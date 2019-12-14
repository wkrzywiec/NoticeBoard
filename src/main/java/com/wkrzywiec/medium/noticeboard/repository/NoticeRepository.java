package com.wkrzywiec.medium.noticeboard.repository;

import com.wkrzywiec.medium.noticeboard.entity.Notice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends CrudRepository<Notice, Long> {
}
