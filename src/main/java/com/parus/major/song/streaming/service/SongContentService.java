package com.parus.major.song.streaming.service;

import com.parus.major.song.streaming.client.SongMetadataServiceClient;
import com.parus.major.song.streaming.component.SongMetadataExtractor;
import com.parus.major.song.streaming.domain.SongContent;
import com.parus.major.song.streaming.repo.SongContentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SongContentService {
    private final SongContentRepository songContentRepository;
    private final SongMetadataExtractor songMetadataExtractor;
    private final SongMetadataServiceClient songMetadataServiceClient;

    public byte[] find(Long id) {
        var songContent = songContentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No song with %d ID.", id)));
        return songContent.getContent();
    }

    public Long save(byte[] content) {
        var songContent = SongContent.builder()
                .content(content)
                .build();
        songContentRepository.save(songContent);
        var songMetadata = songMetadataExtractor.extractSongMetadata(songContent.getId(), content);
        var metadataId = songMetadataServiceClient.saveSongMetadata(songMetadata).getId();
        return songContent.getId();
    }

    public List<Long> deleteByIds(List<Long> ids) {
        var existingIds = songContentRepository.findExistingIds(ids);
        songContentRepository.deleteAllById(existingIds);
        return existingIds;
    }
}
