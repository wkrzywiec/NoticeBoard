package com.wkrzywiec.medium.noticeboard.controller;

import com.wkrzywiec.medium.noticeboard.controller.dto.NoticeDTO;
import com.wkrzywiec.medium.noticeboard.service.NoticeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notices")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<NoticeDTO>> getAllNotices() {
        return new ResponseEntity<>(noticeService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNoticeById(@PathVariable Long id){
        Optional<NoticeDTO> noticeOpt = noticeService.findById(id);

        return noticeOpt.map(notice ->
                new ResponseEntity<>(notice, HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping("/")
    public ResponseEntity<NoticeDTO> saveNotice(@RequestBody NoticeDTO notice) {
        return new ResponseEntity<>(noticeService.save(notice), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Optional<NoticeDTO> noticeOpt = noticeService.findById(id);

        return noticeOpt.map(notice ->
                new ResponseEntity<>("Notice with id " + id + " was deleted.", HttpStatus.NO_CONTENT))
                .orElse(new ResponseEntity<>("Notice with id: " + id + " was not found", HttpStatus.NOT_FOUND));
    }
}
