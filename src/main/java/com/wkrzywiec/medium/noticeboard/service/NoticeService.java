package com.wkrzywiec.medium.noticeboard.service;

import com.wkrzywiec.medium.noticeboard.controller.dto.NoticeDTO;
import com.wkrzywiec.medium.noticeboard.entity.Notice;
import com.wkrzywiec.medium.noticeboard.mapper.NoticeMapper;
import com.wkrzywiec.medium.noticeboard.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public List<NoticeDTO> findAll() {
        List<NoticeDTO> noticeDTOList = new ArrayList<>();
        noticeRepository.findAll().forEach(notice -> {
            noticeDTOList.add(NoticeMapper.INSTANCE.noticeToNoticeDTO(notice));
        });
        return noticeDTOList;
    }

    public NoticeDTO findById(Long id) {
        Optional<Notice> noticeOptional = noticeRepository.findById(id);
        if (noticeOptional.isPresent()){
         return NoticeMapper.INSTANCE.noticeToNoticeDTO(noticeOptional.get());
        } else {
            return null;
        }

    }

}
