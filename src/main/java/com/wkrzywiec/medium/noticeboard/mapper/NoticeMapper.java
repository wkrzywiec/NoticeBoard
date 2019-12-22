package com.wkrzywiec.medium.noticeboard.mapper;

import com.wkrzywiec.medium.noticeboard.controller.dto.NoticeDTO;
import com.wkrzywiec.medium.noticeboard.entity.Notice;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NoticeMapper {

    NoticeMapper INSTANCE = Mappers.getMapper( NoticeMapper.class);

    NoticeDTO mapToNoticeDTO(Notice notice);

    Notice mapToNotice(NoticeDTO noticeDTO);
}
