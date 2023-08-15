package com.parus.major.song.streaming.controller;

import com.parus.major.song.streaming.service.SongContentService;
import com.parus.major.song.streaming.transfer.DeleteDTO;
import com.parus.major.song.streaming.transfer.SaveDTO;
import jakarta.validation.constraints.Size;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.TypeConverterSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping(path = "/resources")
public class SongRestController {
    private final SongContentService songContentService;
    private final TypeConverterSupport typeConverterSupport;

    public SongRestController(SongContentService songContentService, @Qualifier("defaultConversionService") ConversionService conversionService) {
        this.songContentService = songContentService;
        this.typeConverterSupport = new SimpleTypeConverter();
        this.typeConverterSupport.setConversionService(conversionService);;
    }

    @PostMapping(consumes = "audio/mpeg")
    @ResponseStatus(HttpStatus.CREATED)
    public SaveDTO upload(@RequestBody byte[] file) {
        long id = songContentService.save(file);
        return new SaveDTO(id);
    }

    @GetMapping(path = "/{id}", produces = "audio/mpeg")
    public byte[] stream(@PathVariable long id) {
        return songContentService.find(id);
    }

    @DeleteMapping
    @SuppressWarnings("unchecked")
    public DeleteDTO delete(@RequestParam(name = "ids") @Size(max = 200) String idsStr) {
        List<Long> ids = typeConverterSupport.convertIfNecessary(idsStr, List.class,
                TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(Long.class)));
        return new DeleteDTO(songContentService.deleteByIds(ids));
    }
}
