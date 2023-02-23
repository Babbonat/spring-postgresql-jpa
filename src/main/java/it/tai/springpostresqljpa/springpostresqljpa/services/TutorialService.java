package it.tai.springpostresqljpa.springpostresqljpa.services;

import it.tai.springpostresqljpa.springpostresqljpa.domain.TutorialEntity;
import it.tai.springpostresqljpa.springpostresqljpa.exceptions.BadParameterException;
import it.tai.springpostresqljpa.springpostresqljpa.mapper.TutorialMapper;
import it.tai.springpostresqljpa.springpostresqljpa.repository.TutorialRepository;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.CreateTutorialRequestDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.CreateTutorialResponseDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Transactional
public class TutorialService
{
    @Autowired
    TutorialRepository tutorialRepository;

    @Autowired
    TutorialMapper tutorialMapper;

    public CreateTutorialResponseDTO createTutorial(CreateTutorialRequestDTO request)
    {
        if(request.getTitle() == null)
            throw new BadParameterException("title");
        TutorialEntity tutorialEntity = tutorialRepository.saveAndFlush(TutorialEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .published(false).build());
        return CreateTutorialResponseDTO.builder()
                .id(tutorialEntity.getId())
                .title(tutorialEntity.getTitle())
                .description(tutorialEntity.getDescription())
                .published(tutorialEntity.isPublished()).build();
    }
}
