package com.wkrzywiec.medium.noticeboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wkrzywiec.medium.noticeboard.controller.dto.AuthorDTO;
import com.wkrzywiec.medium.noticeboard.controller.dto.BoardDTO;
import com.wkrzywiec.medium.noticeboard.service.AuthorService;
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
import static com.wkrzywiec.medium.noticeboard.util.TestDataFactory.getSingleBoardDTO;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthorController.class)
@DisplayName("Unit tests of AuthorController")
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Test
    @DisplayName("GET an empty list of Authors")
    public void givenNoAuthors_whenGETfindAll_thenGetEmptyList() throws Exception {
        //given
        when(authorService.findAll())
                .thenReturn(Collections.emptyList());

        // when
        mockMvc.perform(
                get("/authors/")
        )
                // then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    @DisplayName("GET a list with single Author")
    public void givenSingleAuthor_whenGETfindAll_thenGetSingleAuthorList() throws Exception {
        //given
        when(authorService.findAll())
                .thenReturn(getAuthorListDTO(1L));

        mockMvc.perform(
                get("/authors/")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("[0].firstName").value("First Name 1"))
                .andExpect(jsonPath("[0].lastName").value("Last Name 1"));
    }

    @Test
    @DisplayName("GET an Author by Id")
    public void givenAuthorId_whenGETById_thenGetSingleAuthor() throws Exception {
        //given
        when(authorService.findById(1L))
                .thenReturn(Optional.of(getSingleAuthorDTO(1L)));

        //when & then
        mockMvc.perform(
                get("/authors/1")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("First Name 1"))
                .andExpect(jsonPath("$.lastName").value("Last Name 1"));
    }

    @Test
    @DisplayName("GET an Author by Id and return 404 Not Found")
    public void givenIncorrectAuthorId_whenGETById_thenGetNotFoundBoard() throws Exception {
        //given
        when(authorService.findById(1L))
                .thenReturn(Optional.empty());

        //when & then
        mockMvc.perform(
                get("/authors/1")
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST an Author to create it")
    public void givenAuthor_whenPOSTSave_thenGetSavedAuthor() throws Exception {
        //given
        AuthorDTO authorDTO = getSingleAuthorDTO(1L);
        authorDTO.setId(null);

        //when
        mockMvc.perform(
                post("/authors/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(authorDTO))
                        .characterEncoding("utf-8")

        )

                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("DELETE an Author by Id")
    public void givenAuthorId_whenDELETEById_thenAuthorIsDeleted() throws Exception {
        //given
        Long authorId = 1L;
        when(authorService.findById(1L))
                .thenReturn(Optional.of(getSingleAuthorDTO(1L)));

        //when
        mockMvc.perform(
                delete("/authors/" + authorId)
        )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE an Author by Id and return 404 HTTP Not Found")
    public void givenAuthorId_whenDELETEbyId_thenAuthorNotFound() throws Exception {
        //given
        Long authorId = 1L;
        when(authorService.findById(1L))
                .thenReturn(Optional.empty());

        //when
        mockMvc.perform(
                delete("/authors/" + authorId)
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT an Author by Id to update it")
    public void givenIdAndUpdatedAuthor_whenPUTUpdate_thenAuthorIsUpdated() throws Exception {
        //given
        Long authorId = 1L;
        AuthorDTO authorDTO = getSingleAuthorDTO(1L);

        when(authorService.findById(1L))
                .thenReturn(Optional.of(authorDTO));

        AuthorDTO updatedAuthor = authorDTO;
        updatedAuthor.setFirstName("New First Name");
        updatedAuthor.setLastName("New Last Name");

        //when
        mockMvc.perform(
                put("/authors/" + authorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedAuthor))
                        .characterEncoding("utf-8")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Object with id 1 was updated."));

    }

    @Test
    @DisplayName("PUT an Author by Id to update it and return 404 HTTP Not Found")
    public void givenIdAndUpdatedAuthor_whenPUTUpdate_thenAuthorNotFound() throws Exception {
        //given
        Long authorId = 1L;
        AuthorDTO authorDTO = getSingleAuthorDTO(1L);

        when(authorService.findById(1L))
                .thenReturn(Optional.empty());

        AuthorDTO updatedAuthor = authorDTO;
        updatedAuthor.setFirstName("New First Name");
        updatedAuthor.setLastName("New Last Name");

        //when
        mockMvc.perform(
                put("/authors/" + authorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedAuthor))
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
