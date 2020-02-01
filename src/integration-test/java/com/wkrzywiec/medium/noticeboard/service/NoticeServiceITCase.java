package com.wkrzywiec.medium.noticeboard.service;

import com.wkrzywiec.medium.noticeboard.controller.dto.NoticeDTO;
import com.wkrzywiec.medium.noticeboard.entity.Notice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Integration Tests of NoticeService with H2 Database")
public class NoticeServiceITCase {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    @DisplayName("Get a list with 3 Notices")
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
    @DisplayName("Get a list with single Notice")
    public void given3NoticesInDb_whenFindById_thenGetSingleNotice() {
        //given
        entityManager.persist(singleNotice(1L));
        entityManager.persist(singleNotice(2L));
        entityManager.persist(singleNotice(3L));
        entityManager.flush();

        Notice savedNotice = getNoticeResultListSavedInDb().get(0);


        //when
        Optional<NoticeDTO> noticeDTOOpt = noticeService.findById(savedNotice.getId());

        //then
        assertTrue(noticeDTOOpt.isPresent());
        assertEquals(savedNotice.getId(), noticeDTOOpt.get().getId());
        assertEquals(savedNotice.getTitle(), noticeDTOOpt.get().getTitle());
        assertEquals(savedNotice.getDescription(), noticeDTOOpt.get().getDescription());
    }

    @Test
    @Transactional
    @DisplayName("Get a Notice by Id and return empty result")
    public void given3NoticesInDb_whenFindById_thenGetEmptyOptional() {
        //given
        entityManager.persist(singleNotice(1L));
        entityManager.persist(singleNotice(2L));
        entityManager.persist(singleNotice(3L));
        entityManager.flush();

        //when
        Optional<NoticeDTO> noticeDTOOpt = noticeService.findById(5000L);

        //then
        assertTrue(noticeDTOOpt.isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("Save a Notice")
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
    @DisplayName("Delete Notice by Id")
    public void given3NoticesInDb_whenDeleteById_thenGet2Notices() {
        //given
        entityManager.persist(singleNotice(1L));
        entityManager.persist(singleNotice(2L));
        entityManager.persist(singleNotice(3L));
        entityManager.flush();
        Notice savedNotice = getNoticeResultListSavedInDb().get(0);

        //when
        noticeService.delete(savedNotice.getId());

        //then
        List<Notice> noticeDTOList = getNoticeResultListSavedInDb();
        assertEquals(2, noticeDTOList.size());
    }

    @Test
    @Transactional
    @DisplayName("Update a Notice")
    public void givenSavedNoticeInDb_whenUpdate_thenNoticeIsUpdated() {
        //given
        entityManager.persist(singleNotice(1L));
        entityManager.flush();
        Notice savedNotice = getNoticeResultListSavedInDb().get(0);

        NoticeDTO toUpdateNoticeDTO = singleNoticeDTO(2L);

        //when
        NoticeDTO updatedNoticeDTO = noticeService.update(savedNotice.getId(), toUpdateNoticeDTO);

        //then
        assertEquals(toUpdateNoticeDTO.getTitle(), updatedNoticeDTO.getTitle());
        assertEquals(toUpdateNoticeDTO.getDescription(), updatedNoticeDTO.getDescription());
        assertEquals(savedNotice.getId(), updatedNoticeDTO.getId());
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
