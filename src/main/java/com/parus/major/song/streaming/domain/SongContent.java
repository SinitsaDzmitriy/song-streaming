package com.parus.major.song.streaming.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "song_content")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongContent {
    @Id
    @GeneratedValue
    private Long id;

    private byte[] content;
}
