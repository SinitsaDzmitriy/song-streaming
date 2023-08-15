package com.parus.major.song.streaming.repo;

import com.parus.major.song.streaming.domain.SongContent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SongContentRepository extends CrudRepository<SongContent, Long> {
    @Query("Select sc.id From SongContent sc Where sc.id IN :ids")
    List<Long> findExistingIds(List<Long> ids);
}
