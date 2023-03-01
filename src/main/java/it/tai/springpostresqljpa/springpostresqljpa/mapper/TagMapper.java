package it.tai.springpostresqljpa.springpostresqljpa.mapper;

import it.tai.springpostresqljpa.springpostresqljpa.domain.TagEntity;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tagsDTO.TagRequestDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tagsDTO.TagResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper
{
    TagResponseDTO toResponse(TagEntity entity);
    TagEntity toEntity(TagRequestDTO request);
}
