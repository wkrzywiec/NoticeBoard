package com.wkrzywiec.medium.noticeboard.controller;

import com.wkrzywiec.medium.noticeboard.controller.dto.NoticeDTO;
import com.wkrzywiec.medium.noticeboard.service.CrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notices")
public class NoticeController extends CrudController<NoticeDTO> {

    public NoticeController(CrudService<NoticeDTO> crudService) {
        super(crudService);
    }
}
