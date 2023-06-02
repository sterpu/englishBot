package com.katetask.englishBot.repository;

import com.katetask.englishBot.entity.Translation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TranslationRepository extends CrudRepository<Translation, Long> {
    @Query(
            value = "SELECT * from Translation order by RANDOM() limit 1 ",
            nativeQuery = true
    )
    Optional<Translation> findRandom();
}
