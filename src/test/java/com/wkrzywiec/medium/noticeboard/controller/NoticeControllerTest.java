package com.wkrzywiec.medium.noticeboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wkrzywiec.medium.noticeboard.controller.dto.NoticeDTO;
import com.wkrzywiec.medium.noticeboard.service.NoticeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(NoticeController.class)
public class NoticeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoticeService noticeService;

    @Test
    public void givenNoNotices_whenGETNotices_thenGetEmptyList() throws Exception {
        //given
        when(noticeService.findAll())
                .thenReturn(Collections.emptyList());

        // when
        mockMvc.perform(
                get("/notices/")
        )
                // then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    public void givenSingleNotice_whenGETNotices_thenGetSingleNoticeList() throws Exception {
        //given
        when(noticeService.findAll())
                .thenReturn(noticeList(1));

        mockMvc.perform(
                get("/notices/")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("[0].title").value("Notice 1"))
                .andExpect(jsonPath("[0].description").value("Notice description 1"));
    }

    @Test
    public void givenNoticeId_whenGETNoticesById_thenGetSingleNotice() throws Exception {
        //given
        when(noticeService.findById(1L))
                .thenReturn(singleNotice(1));

        //when & then
        mockMvc.perform(
                get("/notices/1")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Notice 1"))
                .andExpect(jsonPath("$.description").value("Notice description 1"));
    }

    @Test
    public void givenIncorrectNoticeId_whenGETNoticesById_thenGetNoeNotice() throws Exception {
        //when & then
        mockMvc.perform(
                get("/notices/1")
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void givenNotice_whenPOSTSave_thenGetSavedNotice() throws Exception {
        //given
        NoticeDTO noticeDTO = singleNotice(1);
        noticeDTO.setId(null);

        //when
        mockMvc.perform(
                post("/notices/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(noticeDTO))
                .characterEncoding("utf-8")

        )

                .andDo(print())
                .andExpect(status().isCreated());
    }

    private NoticeDTO singleNotice(int id){
        return NoticeDTO.builder()
                .id(Long.valueOf(id))
                .title("Notice " + id)
                .description("Notice description " + id)
                .build();
    }

    private List<NoticeDTO> noticeList(int noticesCount){
        return IntStream.rangeClosed(1, noticesCount)
                .mapToObj(id -> singleNotice(id))
                .collect(Collectors.toList());
    }

    private String asJsonString(Object object){
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
