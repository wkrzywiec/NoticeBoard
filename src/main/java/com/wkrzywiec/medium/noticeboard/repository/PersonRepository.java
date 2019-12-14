package com.wkrzywiec.medium.noticeboard.repository;

import com.wkrzywiec.medium.noticeboard.entity.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
}
