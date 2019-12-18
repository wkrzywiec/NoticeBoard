package com.wkrzywiec.medium.noticeboard.service;

import com.wkrzywiec.medium.noticeboard.controller.dto.NoticeDTO;
import com.wkrzywiec.medium.noticeboard.entity.Notice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoticeServiceITCase {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    public void given3NoticesInDb_whenFindAll_thenGet3Notices() {
        //given
        entityManager.persist(singleNotice(1L));
        entityManager.persist(singleNotice(2L));
        entityManager.persist(singleNotice(3L));
        entityManager.flush();

        //when
        List<NoticeDTO> noticeList = noticeService.findAll();

        //then
        assertEquals(3, noticeList.size());
    }

    private Notice singleNotice(Long number){
        return Notice.builder()
                .title("Notice " + number)
                .description("Notice description " + number)
                .build();
    }

    private List<Notice> getNoticeResultListSavedInDb() {
        return entityManager
                .createNativeQuery("SELECT Notice.* FROM Notice", Notice.class)
                .getResultList();
    }
}
