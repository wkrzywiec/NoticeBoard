package com.wkrzywiec.medium.noticeboard.service;

import com.wkrzywiec.medium.noticeboard.controller.dto.NoticeDTO;
import com.wkrzywiec.medium.noticeboard.entity.Notice;
import com.wkrzywiec.medium.noticeboard.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.wkrzywiec.medium.noticeboard.mapper.NoticeMapper.*;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public List<NoticeDTO> findAll() {
        List<NoticeDTO> noticeDTOList = new ArrayList<>();
        noticeRepository.findAll().forEach(notice -> noticeDTOList.add(INSTANCE.mapToNoticeDTO(notice)));
        return noticeDTOList;
    }

    public Optional<NoticeDTO> findById(Long id) {
        Optional<Notice> noticeOptional = noticeRepository.findById(id);
        return noticeOptional.map(INSTANCE::mapToNoticeDTO);
    }

    @Transactional
    public NoticeDTO save(NoticeDTO noticeDTO) {
        Notice notice = INSTANCE.mapToNotice(noticeDTO);
        return INSTANCE.mapToNoticeDTO(noticeRepository.save(notice));
    }

    @Transactional
    public void delete(Long id){
        noticeRepository.deleteById(id);
    }

    @Transactional
    public NoticeDTO update(Long id, NoticeDTO noticeDTO){
        Notice savedNotice = noticeRepository.findById(id).orElseThrow();
        Notice noticeToUpdate = INSTANCE.mapToNotice(noticeDTO);

        savedNotice.setTitle(noticeToUpdate.getTitle());
        savedNotice.setDescription(noticeToUpdate.getDescription());

        return INSTANCE.mapToNoticeDTO(noticeRepository.save(savedNotice));
    }

}
