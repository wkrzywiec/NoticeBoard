package com.wkrzywiec.medium.noticeboard.controller;

import com.wkrzywiec.medium.noticeboard.controller.dto.NoticeDTO;
import com.wkrzywiec.medium.noticeboard.service.NoticeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notices")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/")
    public List<NoticeDTO> getAllNotices() {
        return noticeService.findAll();
    }

    @GetMapping("/{id}")
    public NoticeDTO getNoticeById(@PathVariable Long id){
        return noticeService.findById(id);
    }

    @PostMapping("/")
    public NoticeDTO saveNotice(@RequestBody NoticeDTO notice) {
        return noticeService.save(notice);
    }
}
