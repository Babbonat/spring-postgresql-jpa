package it.tai.springpostresqljpa.springpostresqljpa.mapper;

import it.tai.springpostresqljpa.springpostresqljpa.domain.TutorialEntity;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tutorialsDTO.CreateTutorialRequestDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tutorialsDTO.CreateTutorialResponseDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tutorialsDTO.TutorialResponseDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tutorialsDTO.UpdateTutorialRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TutorialMapper
{
    TutorialEntity toEntity(CreateTutorialRequestDTO request);
    TutorialEntity toEntity(UpdateTutorialRequestDTO request);
    CreateTutorialResponseDTO toCreateResponse(TutorialEntity entity);
    TutorialResponseDTO toResponse(TutorialEntity entity);
}
