package com.wkrzywiec.medium.noticeboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wkrzywiec.medium.noticeboard.controller.dto.BoardDTO;
import com.wkrzywiec.medium.noticeboard.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static com.wkrzywiec.medium.noticeboard.util.TestDataFactory.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BoardController.class)
@DisplayName("Unit tests of BoardController")
public class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardService boardService;

    @Test
    @DisplayName("GET an empty list of Boards")
    public void givenNoBoards_whenGETfindAll_thenGetEmptyList() throws Exception {
        //given
        when(boardService.findAll())
                .thenReturn(Collections.emptyList());

        // when
        mockMvc.perform(
                get("/boards/")
        )
                // then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    @DisplayName("GET a list with single Board")
    public void givenSingleBoard_whenGETfindAll_thenGetSingleBoardList() throws Exception {
        //given
        when(boardService.findAll())
                .thenReturn(getBoardListDTO(1L, 1L));

        mockMvc.perform(
                get("/boards/")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("[0].title").value("Board 1"))
                .andExpect(jsonPath("[0].noticeList", hasSize(1)));
    }

    @Test
    @DisplayName("GET a Board by Id")
    public void givenBoardId_whenGETById_thenGetSingleBoard() throws Exception {
        //given
        when(boardService.findById(1L))
                .thenReturn(Optional.of(getSingleBoardDTO(1L, 1L)));

        //when & then
        mockMvc.perform(
                get("/boards/1")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Board 1"))
                .andExpect(jsonPath("$.noticeList", hasSize(1)));
    }

    @Test
    @DisplayName("GET a Board by Id and return 404 Not Found")
    public void givenIncorrectBoardId_whenGETById_thenGetNotFoundBoard() throws Exception {
        //given
        when(boardService.findById(1L))
                .thenReturn(Optional.empty());

        //when & then
        mockMvc.perform(
                get("/boards/1")
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST a Notice to create it")
    public void givenBoard_whenPOSTSave_thenGetSavedBoard() throws Exception {
        //given
        BoardDTO boardDTO = getSingleBoardDTO(1L, 5L);
        boardDTO.setId(null);

        //when
        mockMvc.perform(
                post("/boards/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(boardDTO))
                        .characterEncoding("utf-8")

        )

                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("DELETE a Board by Id")
    public void givenBoardId_whenDELETEById_thenBoardIsDeleted() throws Exception {
        //given
        Long boardId = 1L;
        when(boardService.findById(1L))
                .thenReturn(Optional.of(getSingleBoardDTO(1L, 5L)));

        //when
        mockMvc.perform(
                delete("/boards/" + boardId)
        )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE a Board by Id and return 404 HTTP Not Found")
    public void givenBoardId_whenDELETEbyId_thenBoardNotFound() throws Exception {
        //given
        Long boardId = 1L;
        when(boardService.findById(1L))
                .thenReturn(Optional.empty());

        //when
        mockMvc.perform(
                delete("/boards/" + boardId)
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT a Board by Id to update it")
    public void givenIdAndUpdatedBoard_whenPUTUpdate_thenBoardIsUpdated() throws Exception {
        //given
        Long boardId = 1L;
        BoardDTO boardDTO = getSingleBoardDTO(1L, 5L);

        when(boardService.findById(1L))
                .thenReturn(Optional.of(boardDTO));

        BoardDTO updatedBoard = boardDTO;
        updatedBoard.setTitle("New Title");
        updatedBoard.setNoticeList(getNoticeListDTO(10L));

        //when
        mockMvc.perform(
                put("/boards/" + boardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(boardDTO))
                        .characterEncoding("utf-8")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Object with id 1 was updated."));

    }

    @Test
    @DisplayName("PUT a Board by Id to update it and return 404 HTTP Not Found")
    public void givenIdAndUpdatedBoard_whenPUTUpdate_thenBoardNotFound() throws Exception {
        //given
        Long boardId = 1L;
        BoardDTO boardDTO = getSingleBoardDTO(1L, 5L);

        when(boardService.findById(1L))
                .thenReturn(Optional.empty());

        //when
        mockMvc.perform(
                put("/boards/" + boardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(boardDTO))
                        .characterEncoding("utf-8")
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private String asJsonString(Object object){
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
