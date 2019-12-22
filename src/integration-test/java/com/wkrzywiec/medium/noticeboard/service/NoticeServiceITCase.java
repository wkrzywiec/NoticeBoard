package com.wkrzywiec.medium.noticeboard.service;

import com.wkrzywiec.medium.noticeboard.controller.dto.NoticeDTO;
import com.wkrzywiec.medium.noticeboard.entity.Notice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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

    @Test
    @Transactional
    public void given3NoticesInDb_whenFindById_thenGetSingleNotice() {
        //given
        entityManager.persist(singleNotice(1L));
        entityManager.persist(singleNotice(2L));
        entityManager.persist(singleNotice(3L));
        entityManager.flush();

        Notice savedNotice = getNoticeResultListSavedInDb().get(0);


        //when
        NoticeDTO noticeDTO = noticeService.findById(savedNotice.getId());

        //then
        assertEquals(savedNotice.getId(), noticeDTO.getId());
        assertEquals(savedNotice.getTitle(), noticeDTO.getTitle());
        assertEquals(savedNotice.getDescription(), noticeDTO.getDescription());
    }

    @Test
    @Transactional
    public void given3NoticesInDb_whenFindById_thenGetNull() {
        //given
        entityManager.persist(singleNotice(1L));
        entityManager.persist(singleNotice(2L));
        entityManager.persist(singleNotice(3L));
        entityManager.flush();

        //when
        NoticeDTO noticeDTO = noticeService.findById(5000L);

        //then
        assertNull(noticeDTO);
    }

    @Test
    @Transactional
    public void givenNotice_whenSave_thenGetSavedNotice() {
        //given
        NoticeDTO notice = singleNoticeDTO(1L);

        //when
        NoticeDTO savedNotice = noticeService.save(notice);

        //then
        assertNotNull(savedNotice.getId());
        assertEquals(notice.getTitle(), savedNotice.getTitle());
        assertEquals(notice.getDescription(), savedNotice.getDescription());
    }

    @Test
    @Transactional
    public void given3NoticesInDb_whenDeleteById_thenGet2Notices() {
        //given
        entityManager.persist(singleNotice(1L));
        entityManager.persist(singleNotice(2L));
        entityManager.persist(singleNotice(3L));
        entityManager.flush();

        //when
        noticeService.delete(1L);

        //then
        List<Notice> noticeDTOList = getNoticeResultListSavedInDb();
        assertEquals(2, noticeDTOList.size());
    }

    @Test
    @Transactional
    public void givenSavedNoticeInDb_whenUpdate_thenNoticeIsUpdated() {
        //given
        entityManager.persist(singleNotice(1L));
        entityManager.flush();

        NoticeDTO toUpdateNoticeDTO = singleNoticeDTO(2L);

        //when
        NoticeDTO updatedNoticeDTO = noticeService.update(1L, toUpdateNoticeDTO);

        //then
        assertEquals(toUpdateNoticeDTO.getTitle(), updatedNoticeDTO.getTitle());
        assertEquals(toUpdateNoticeDTO.getDescription(), updatedNoticeDTO.getDescription());
        assertEquals(1L, updatedNoticeDTO.getId());
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

    private NoticeDTO singleNoticeDTO(Long number){
        return NoticeDTO.builder()
                .title("Notice " + number)
                .description("Notice description " + number)
                .build();
    }
}
