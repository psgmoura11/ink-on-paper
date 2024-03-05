package com.inkonpaper.catalog.service.domain.repositories;

import com.inkonpaper.catalog.service.domain.entities.BookSample;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookSampleRepository extends JpaRepository<BookSample, Long> {

}
