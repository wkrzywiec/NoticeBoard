package com.wkrzywiec.medium.noticeboard.service;

import com.wkrzywiec.medium.noticeboard.controller.dto.BoardDTO;
import com.wkrzywiec.medium.noticeboard.entity.Board;
import com.wkrzywiec.medium.noticeboard.repository.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.wkrzywiec.medium.noticeboard.util.TestDataFactory.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests of BoardService class")
public class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardService boardService;

    @Test
    @DisplayName("Get an empty list of Boards")
    public void givenNoBoards_whenFindAll_thenGetEmptyList() {
        //given
        when(boardRepository.findAll())
                .thenReturn(Collections.emptyList());

        //when
        List<BoardDTO> boardList = boardService.findAll();

        //then
        assertEquals(0, boardList.size());
    }

    @Test
    @DisplayName("Get a list with single Board")
    public void givenSingleBoards_whenFindAll_thenSingleBoardList() {
        //given
        when(boardRepository.findAll())
                .thenReturn(getBoardList(1L, 5L));

        //when
        List<BoardDTO> boardList = boardService.findAll();

        //then
        assertEquals(1, boardList.size());
        assertEquals("Board 1", boardList.get(0).getTitle());
        assertEquals(5, boardList.get(0).getNoticeList().size());
    }

    @Test
    @DisplayName("Get a list of 500 Boards")
    public void given500Boards_whenFindAll_then500BoardList() {
        //given
        when(boardRepository.findAll())
                .thenReturn(getBoardList(500L, 5L));

        //when
        List<BoardDTO> boardList = boardService.findAll();

        //then
        assertEquals(500, boardList.size());
    }

    @Test
    @DisplayName("Get a Board by Id")
    public void givenSingleBoard_whenFindById_thenGetSingleBoard(){
        //given
        when(boardRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getSingleBoard(1L, 1L)));

        //when
        Optional<BoardDTO> boardDTOOpt = boardService.findById(1L);

        //then
        assertTrue(boardDTOOpt.isPresent());
        assertNotNull(boardDTOOpt.get().getId());
        assertEquals("Board 1", boardDTOOpt.get().getTitle());
        assertEquals(1, boardDTOOpt.get().getNoticeList().size());
        assertNotNull(boardDTOOpt.get().getNoticeList().get(0));
        assertNotNull(boardDTOOpt.get().getNoticeList().get(0).getId());
        assertEquals("Notice 1", boardDTOOpt.get().getNoticeList().get(0).getTitle());
        assertEquals("Notice description 1", boardDTOOpt.get().getNoticeList().get(0).getDescription());
    }

    @Test
    @DisplayName("Get a Board by Id and return empty result")
    public void givenNoBoard_whenFindById_thenGetEmptyOptional(){
        //given
        when(boardRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        //when
        Optional<BoardDTO> boardDTOOpt = boardService.findById(1L);

        //then
        assertFalse(boardDTOOpt.isPresent());
    }

    @Test
    @DisplayName("Save a Board")
    public void givenBoard_whenSave_thenGetSavedBoard() {
        //given
        when(boardRepository.save(any(Board.class)))
                .thenReturn(getSingleBoard(1L, 1L));

        BoardDTO boardDTO = getSingleBoardDTO(1L, 1L);

        //when
        BoardDTO savedBoard = boardService.save(boardDTO);

        //then
        assertNotNull(savedBoard.getId());
    }

    @Test
    @DisplayName("Update a Board")
    public void givenSavedBoard_whenUpdate_thenBoardIsUpdated() {
        //given
        when(boardRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getSingleBoard(1L, 1L)));

        when(boardRepository.save(any(Board.class)))
                .thenReturn(getSingleBoard(2L, 5L));

        BoardDTO toBeUpdatedBoardDTO = getSingleBoardDTO(2L, 5L);

        //when
        BoardDTO updatedBoardDTO = boardService.update(1L, toBeUpdatedBoardDTO);

        //then
        assertEquals(toBeUpdatedBoardDTO.getTitle(), updatedBoardDTO.getTitle());
        assertEquals(5L, updatedBoardDTO.getNoticeList().size());
    }
}
