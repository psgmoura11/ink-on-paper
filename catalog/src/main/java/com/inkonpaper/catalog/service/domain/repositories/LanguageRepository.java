package com.inkonpaper.catalog.service.domain.repositories;

import com.inkonpaper.catalog.service.domain.entities.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {

}
