package com.example.CRUDapplication.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CRUDapplication.model.Book;

@Repository
public interface Bookrepo extends JpaRepository<Book, Long>{

}
