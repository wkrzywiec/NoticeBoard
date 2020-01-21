package com.wkrzywiec.medium.noticeboard.controller;

import com.wkrzywiec.medium.noticeboard.controller.dto.BaseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CrudController<T extends BaseDTO> {

    @GetMapping("/")
    ResponseEntity<List<T>> getAll();

    @GetMapping("/{id}")
    ResponseEntity<T> getById(@PathVariable Long id);

    @PostMapping("/")
    ResponseEntity<T> save(@RequestBody T body);

    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable Long id);

    @PutMapping("/{id}")
    ResponseEntity<String> update(@PathVariable Long id, @RequestBody T body);
}
