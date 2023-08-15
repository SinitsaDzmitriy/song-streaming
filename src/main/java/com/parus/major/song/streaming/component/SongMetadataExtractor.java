package com.parus.major.song.streaming.component;

import com.parus.major.song.streaming.transfer.SongMetadataDTO;
import org.apache.tika.metadata.IPTC;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.XMPDM;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.springframework.stereotype.Component;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.DefaultHandler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;

@Component
public class SongMetadataExtractor {
    public SongMetadataDTO extractSongMetadata(long songId, byte[] content) {
        Metadata metadata = internalExtractSongMetadata(content);
        return buildSongMetadataDTO(songId, metadata);
    }

    private Metadata internalExtractSongMetadata(byte[] content) {
        try (InputStream is = new ByteArrayInputStream(content)) {
            ContentHandler handler = new DefaultHandler();
            Metadata metadata = new Metadata();
            Parser parser = new Mp3Parser();
            ParseContext parseContext = new ParseContext();
            parser.parse(is, handler, metadata, parseContext);
            return metadata;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private SongMetadataDTO buildSongMetadataDTO(long songId, Metadata metadata) {
        var durationInSeconds = Double.valueOf(metadata.get(XMPDM.DURATION)).longValue();
        var releaseLocalDate = LocalDate.parse(metadata.get(XMPDM.RELEASE_DATE), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return SongMetadataDTO.builder()
                .songId(songId)
                .name(metadata.get(IPTC.TITLE.getName()))
                .artist(metadata.get(XMPDM.ARTIST.getName()))
                .album(metadata.get(XMPDM.ALBUM.getName()))
                .length(Duration.ofSeconds(durationInSeconds))
                .year(Year.of(releaseLocalDate.getYear()))
                .build();
    }
}
