package com.wkrzywiec.medium.noticeboard.service;

import com.wkrzywiec.medium.noticeboard.controller.dto.AuthorDTO;
import com.wkrzywiec.medium.noticeboard.entity.Author;
import com.wkrzywiec.medium.noticeboard.mapper.AuthorMapper;
import com.wkrzywiec.medium.noticeboard.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.wkrzywiec.medium.noticeboard.mapper.AuthorMapper.INSTANCE;

@Service
@RequiredArgsConstructor
public class AuthorService implements CrudService<AuthorDTO> {
    @Autowired
    private final AuthorRepository authorRepository;

    @Override
    public List<AuthorDTO> findAll() {
        List<AuthorDTO> authorDTOList = new ArrayList<>();
        authorRepository.findAll().forEach(author -> authorDTOList.add(INSTANCE.authorToDto(author)));
        return authorDTOList;
    }

    @Override
    public AuthorDTO findById(Long id) {
        try{
        Author authorOptional = authorRepository.findById(id).get();
        }
        catch(Exception ex){
                System.out.println("Author Not Found With this id");
        }
            return authorOptional.map(AuthorMapper.INSTANCE::authorToDto);
    }

    @Override
    public AuthorDTO save(AuthorDTO authorDTO) {
        Author author = INSTANCE.dtoToAuthor(authorDTO);
        return INSTANCE.authorToDto(authorRepository.save(author));
    }

    @Override
    public void delete(Long id) {
        try{
        authorRepository.deleteById(id);
        }
        catch(Exception ex){
        System.out.println("Author Not Found With this id");
        }
    
    }

    @Override
    public AuthorDTO update(Long id, AuthorDTO authorDTO) {
        Author savedAuthor = authorRepository.findById(id).orElseThrow();
        Author authorToUpdate = INSTANCE.dtoToAuthor(authorDTO);

        savedAuthor.setFirstName(authorToUpdate.getFirstName());
        savedAuthor.setLastName(authorToUpdate.getLastName());

        return INSTANCE.authorToDto(authorRepository.save(savedAuthor));
    }
}
