package com.inkonpaper.catalog.service.domain.repositories;

import com.inkonpaper.catalog.service.domain.entities.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

}
