package com.wkrzywiec.medium.noticeboard.service;

import com.wkrzywiec.medium.noticeboard.controller.dto.NoticeDTO;
import com.wkrzywiec.medium.noticeboard.entity.Board;
import com.wkrzywiec.medium.noticeboard.entity.Notice;
import com.wkrzywiec.medium.noticeboard.repository.NoticeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NoticeServiceTest {

    @Mock
    private NoticeRepository noticeRepository;

    @InjectMocks
    private NoticeService noticeService;

    @Test
    public void givenNoNotices_whenFindAllNotices_thenGetEmptyList() {
        //given
        when(noticeRepository.findAll())
                .thenReturn(Collections.emptyList());

        //when
        List<NoticeDTO> noticeList = noticeService.findAll();

        //then
        assertEquals(0, noticeList.size());
    }

    @Test
    public void givenSingleNotices_whenFindAllNotices_thenSingleNoticeList() {
        //given
        when(noticeRepository.findAll())
                .thenReturn(noticeList(1L));

        //when
        List<NoticeDTO> noticeList = noticeService.findAll();

        //then
        assertEquals(1, noticeList.size());
        assertEquals("Notice 1", noticeList.get(0).getTitle());
        assertEquals("Notice description 1", noticeList.get(0).getDescription());
        assertEquals(Long.valueOf(1), noticeList.get(0).getBoardId());
    }

    @Test
    public void given500Notices_whenFindAllNotices_then500NoticeList() {
        //given
        when(noticeRepository.findAll())
                .thenReturn(noticeList(500L));

        //when
        List<NoticeDTO> noticeList = noticeService.findAll();

        //then
        assertEquals(500, noticeList.size());
    }

    @Test
    public void givenSingleNotice_whenFindById_thenGetSingleNotice(){
        //given
        when(noticeRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(singleNotice(1L)));

        //when
        Optional<NoticeDTO> noticeDTOOpt = noticeService.findById(1L);

        //then
        assertTrue(noticeDTOOpt.isPresent());
        assertEquals("Notice 1", noticeDTOOpt.get().getTitle());
        assertEquals("Notice description 1", noticeDTOOpt.get().getDescription());
        assertEquals(Long.valueOf(1), noticeDTOOpt.get().getBoardId());
    }

    @Test
    public void givenNoNotice_whenFindById_thenGetNull(){
        //given
        when(noticeRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        //when
        Optional<NoticeDTO> noticeDTOOpt = noticeService.findById(1L);

        //then
        assertFalse(noticeDTOOpt.isPresent());
    }

    @Test
    public void givenNotice_whenSave_thenGetSavedNotice() {
        //given
        when(noticeRepository.save(any(Notice.class)))
                .thenReturn(singleNotice(1L));

        NoticeDTO noticeDTO = singleNoticeDTO(1L);

        //when
        NoticeDTO savedNotice = noticeService.save(noticeDTO);

        //then
        assertNotNull(savedNotice.getId());
    }

    @Test
    public void givenSavedNotice_whenUpdate_thenNoticeIsUpdated() {
        //given
        when(noticeRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(singleNotice(1L)));

        when(noticeRepository.save(any(Notice.class)))
                .thenReturn(singleNotice(2L));

        NoticeDTO afterUpdeNoticeDTO = singleNoticeDTO(2L);

        //when
        NoticeDTO updatedNoticeDTO = noticeService.update(1L, afterUpdeNoticeDTO);

        //then
        assertEquals(afterUpdeNoticeDTO.getTitle(), updatedNoticeDTO.getTitle());
        assertEquals(afterUpdeNoticeDTO.getDescription(), updatedNoticeDTO.getDescription());
    }

    private Notice singleNotice(Long id){
        Board board = Board.builder()
                .id(id)
                .title("Board " + id)
                .build();

        return Notice.builder()
                .id(id)
                .title("Notice " + id)
                .description("Notice description " + id)
                .board(board)
                .build();
    }

    private List<Notice> noticeList(Long noticesCount){
        return LongStream.rangeClosed(1, noticesCount)
                .mapToObj(id -> singleNotice(id))
                .collect(Collectors.toList());
    }

    private NoticeDTO singleNoticeDTO(Long id){
        return NoticeDTO.builder()
                .title("Notice " + id)
                .description("Notice description " + id)
                .boardId(id)
                .build();
    }
}
