package com.parus.major.song.streaming.client;

import com.parus.major.song.streaming.transfer.SaveDTO;
import com.parus.major.song.streaming.transfer.SongMetadataDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

@Component
public interface SongMetadataServiceClient {
    @PostExchange("/metadata")
    SaveDTO saveSongMetadata(@RequestBody SongMetadataDTO songMetadata);
}
