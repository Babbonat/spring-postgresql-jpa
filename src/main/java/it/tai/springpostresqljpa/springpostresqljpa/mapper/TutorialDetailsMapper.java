package it.tai.springpostresqljpa.springpostresqljpa.mapper;

import it.tai.springpostresqljpa.springpostresqljpa.domain.TutorialDetailsEntity;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tutorialDetailsDTO.TDRequestDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tutorialDetailsDTO.TDResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TutorialDetailsMapper
{
    TDResponseDTO toResponse(TutorialDetailsEntity entity);
    TutorialDetailsEntity toEntity(TDRequestDTO request);
}
