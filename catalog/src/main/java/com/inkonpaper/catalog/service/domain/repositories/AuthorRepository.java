package com.inkonpaper.catalog.service.domain.repositories;

import com.inkonpaper.catalog.service.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
