package com.inkonpaper.catalog.service.domain.repositories;

import com.inkonpaper.catalog.service.domain.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

}
