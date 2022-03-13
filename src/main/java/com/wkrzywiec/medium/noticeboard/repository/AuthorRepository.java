package com.wkrzywiec.medium.noticeboard.repository;

import com.wkrzywiec.medium.noticeboard.entity.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
  
  List<Author> findById();
}
