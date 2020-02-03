package com.wkrzywiec.medium.noticeboard.service;

import com.wkrzywiec.medium.noticeboard.controller.dto.BoardDTO;
import com.wkrzywiec.medium.noticeboard.entity.Board;
import com.wkrzywiec.medium.noticeboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.wkrzywiec.medium.noticeboard.mapper.BoardMapper.INSTANCE;

@Service
@RequiredArgsConstructor
public class BoardService implements CrudService<BoardDTO> {

    private final BoardRepository boardRepository;

    @Override
    public List<BoardDTO> findAll() {
        List<BoardDTO> boardDTOList = new ArrayList<>();
        boardRepository.findAll().forEach(board -> boardDTOList.add(INSTANCE.boardToDto(board)));
        return boardDTOList;
    }

    @Override
    public Optional<BoardDTO> findById(Long id) {
        Optional<Board> boardOptional = boardRepository.findById(id);
        return boardOptional.map(INSTANCE::boardToDto);
    }

    @Override
    public BoardDTO save(BoardDTO boardDTO) {
        Board board = INSTANCE.dtoToBoard(boardDTO);
        return INSTANCE.boardToDto(boardRepository.save(board));
    }

    @Override
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    @Override
    public BoardDTO update(Long id, BoardDTO boardDTO) {
        Board savedBoard = boardRepository.findById(id).orElseThrow();
        Board boardToUpdate = INSTANCE.dtoToBoard(boardDTO);

        savedBoard.setTitle(boardToUpdate.getTitle());
        savedBoard.setNoticeList(boardToUpdate.getNoticeList());

        return INSTANCE.boardToDto(boardRepository.save(savedBoard));
    }
}
