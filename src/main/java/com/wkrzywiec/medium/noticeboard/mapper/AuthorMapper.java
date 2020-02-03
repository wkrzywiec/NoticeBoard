package com.wkrzywiec.medium.noticeboard.mapper;

import com.wkrzywiec.medium.noticeboard.controller.dto.AuthorDTO;
import com.wkrzywiec.medium.noticeboard.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    AuthorDTO authorToDto(Author author);
    Author dtoToAuthor(AuthorDTO authorDTO);
}
