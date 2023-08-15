package com.parus.major.song.streaming.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Year;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongMetadataDTO {
    private Long songId;
    private String name;
    private String artist;
    private String album;
    private Duration length;
    private Year year;
}
