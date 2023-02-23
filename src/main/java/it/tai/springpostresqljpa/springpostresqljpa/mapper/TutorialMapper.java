package it.tai.springpostresqljpa.springpostresqljpa.mapper;

import it.tai.springpostresqljpa.springpostresqljpa.domain.TutorialEntity;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.CreateTutorialRequestDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.CreateTutorialResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TutorialMapper
{
    TutorialEntity toEntity(CreateTutorialRequestDTO request);
    CreateTutorialResponseDTO toResponse(TutorialEntity entity);
}
