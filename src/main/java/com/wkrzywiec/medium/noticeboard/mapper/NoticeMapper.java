package com.wkrzywiec.medium.noticeboard.mapper;

import com.wkrzywiec.medium.noticeboard.controller.dto.NoticeDTO;
import com.wkrzywiec.medium.noticeboard.entity.Board;
import com.wkrzywiec.medium.noticeboard.entity.Notice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper
public interface NoticeMapper {

    NoticeMapper INSTANCE = Mappers.getMapper( NoticeMapper.class);

    @Mapping(source = "board", target = "boardId", qualifiedByName = "boardId")
    NoticeDTO mapToNoticeDTO(Notice notice);

    Notice mapToNotice(NoticeDTO noticeDTO);

    @Named("boardId")
    default Long boardToId(Board board){
        Optional<Board> boardOpt = Optional.ofNullable(board);
        return boardOpt.map(Board::getId).orElse(0L);
    }
}
