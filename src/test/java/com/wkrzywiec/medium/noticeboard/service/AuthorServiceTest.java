package com.wkrzywiec.medium.noticeboard.service;

import com.wkrzywiec.medium.noticeboard.controller.dto.AuthorDTO;
import com.wkrzywiec.medium.noticeboard.entity.Author;
import com.wkrzywiec.medium.noticeboard.repository.AuthorRepository;
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
@DisplayName("Unit tests of AuthorService class")
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    @DisplayName("Get an empty list of Author")
    public void givenNoAuthors_whenFindAll_thenGetEmptyList() {
        //given
        when(authorRepository.findAll())
                .thenReturn(Collections.emptyList());

        //when
        List<AuthorDTO> authorList = authorService.findAll();

        //then
        assertEquals(0, authorList.size());
    }

    @Test
    @DisplayName("Get a list with single Author")
    public void givenSingleAuthors_whenFindAll_thenSingleAuthorList() {
        //given
        when(authorRepository.findAll())
                .thenReturn(getAuthorList(1L));

        //when
        List<AuthorDTO> authorList = authorService.findAll();

        //then
        assertEquals(1, authorList.size());
        assertEquals("First Name 1", authorList.get(0).getFirstName());
        assertEquals("Last Name 1", authorList.get(0).getLastName());
    }

    @Test
    @DisplayName("Get a list of 500 Authors")
    public void given500Authors_whenFindAll_then500AuthorList() {
        //given
        when(authorRepository.findAll())
                .thenReturn(getAuthorList(500L));

        //when
        List<AuthorDTO> authorList = authorService.findAll();

        //then
        assertEquals(500, authorList.size());
    }

    @Test
    @DisplayName("Get an Author by Id")
    public void givenSingleAuthor_whenFindById_thenGetSingleAuthor(){
        //given
        when(authorRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getSingleAuthor(1L)));

        //when
        Optional<AuthorDTO> authorOpt = authorService.findById(1L);

        //then
        assertTrue(authorOpt.isPresent());
        assertNotNull(authorOpt.get().getId());
        assertEquals("First Name 1", authorOpt.get().getFirstName());
        assertEquals("Last Name 1", authorOpt.get().getLastName());
    }

    @Test
    @DisplayName("Get an Author by Id and return empty result")
    public void givenNoAuthor_whenFindById_thenGetEmptyOptional(){
        //given
        when(authorRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        //when
        Optional<AuthorDTO> authorOpt = authorService.findById(1L);

        //then
        assertFalse(authorOpt.isPresent());
    }

    @Test
    @DisplayName("Save an Author")
    public void givenAuthor_whenSave_thenGetSavedAuthor() {
        //given
        when(authorRepository.save(any(Author.class)))
                .thenReturn(getSingleAuthor(1L));

        AuthorDTO authorDTO = getSingleAuthorDTO(1L);

        //when
        AuthorDTO savedAuthor = authorService.save(authorDTO);

        //then
        assertNotNull(savedAuthor.getId());
    }

    @Test
    @DisplayName("Update an Author")
    public void givenSavedAuthor_whenUpdate_thenAuthorIsUpdated() {
        //given
        when(authorRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getSingleAuthor(1L)));

        when(authorRepository.save(any(Author.class)))
                .thenReturn(getSingleAuthor(2L));

        AuthorDTO toBeUpdatedAuthorDTO = getSingleAuthorDTO(2L);

        //when
        AuthorDTO updatedAuthorDTO = authorService.update(1L, toBeUpdatedAuthorDTO);

        //then
        assertEquals(toBeUpdatedAuthorDTO.getFirstName(), updatedAuthorDTO.getFirstName());
        assertEquals(toBeUpdatedAuthorDTO.getLastName(), updatedAuthorDTO.getLastName());
    }
}
