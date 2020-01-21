package com.wkrzywiec.medium.noticeboard.controller;

import com.wkrzywiec.medium.noticeboard.controller.dto.NoticeDTO;
import com.wkrzywiec.medium.noticeboard.service.NoticeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notices")
public class NoticeController implements CrudController<NoticeDTO> {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Override
    public ResponseEntity<List<NoticeDTO>> getAll() {
        return new ResponseEntity<>(noticeService.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<NoticeDTO> getById(Long id) {
        Optional<NoticeDTO> noticeOpt = noticeService.findById(id);

        return noticeOpt.map(notice ->
                new ResponseEntity<>(notice, HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<NoticeDTO> save(NoticeDTO notice) {
        return new ResponseEntity<>(noticeService.save(notice), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        Optional<NoticeDTO> noticeOpt = noticeService.findById(id);

        return noticeOpt.map(notice ->
                new ResponseEntity<>("Notice with id " + id + " was deleted.", HttpStatus.NO_CONTENT))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<String> update(Long id, NoticeDTO notice) {
        Optional<NoticeDTO> noticeOpt = noticeService.findById(id);
        noticeOpt.ifPresent(n -> noticeService.update(id, notice));

        return noticeOpt.map(n ->
                new ResponseEntity<>("Notice with id " + id + " was updated.", HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND));
    }
}
