package com.inkonpaper.catalog.service.domain.repositories;

import com.inkonpaper.catalog.service.domain.entities.Book;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource(exported = false)
public interface BookRepository extends JpaRepository<Book, Long> {

  Optional<Book> findByOriginalTitle(String originalTitle);

  Optional<Book> findByIsbn(String isbn);

  @Transactional
  @Modifying
  @Query("UPDATE Book b SET b.stockAvailable = :stock WHERE b.id = :id")
  void updateStock(@Param("stock") int stock, @Param("id") Long id);
}
