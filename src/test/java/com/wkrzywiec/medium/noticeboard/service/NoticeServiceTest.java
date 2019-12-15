package com.wkrzywiec.medium.noticeboard.service;

import com.wkrzywiec.medium.noticeboard.controller.dto.NoticeDTO;
import com.wkrzywiec.medium.noticeboard.entity.Notice;
import com.wkrzywiec.medium.noticeboard.repository.NoticeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.Assert.assertEquals;
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

    private Notice singleNotice(Long id){
        return Notice.builder()
                .id(id)
                .title("Notice " + id)
                .description("Notice description " + id)
                .build();
    }

    private List<Notice> noticeList(Long noticesCount){
        return LongStream.rangeClosed(1, noticesCount)
                .mapToObj(id -> singleNotice(id))
                .collect(Collectors.toList());
    }
}
