package it.tai.springpostresqljpa.springpostresqljpa.mapper;

import it.tai.springpostresqljpa.springpostresqljpa.domain.TagEntity;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tagsDTO.TagsResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper
{
    TagsResponseDTO toResponse(TagEntity entity);
}
