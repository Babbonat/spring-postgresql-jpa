package it.tai.springpostresqljpa.springpostresqljpa.services;

import it.tai.springpostresqljpa.springpostresqljpa.domain.TutorialEntity;
import it.tai.springpostresqljpa.springpostresqljpa.exceptions.BadParameterException;
import it.tai.springpostresqljpa.springpostresqljpa.exceptions.ResourceNotFoundException;
import it.tai.springpostresqljpa.springpostresqljpa.mapper.TutorialMapper;
import it.tai.springpostresqljpa.springpostresqljpa.repository.TutorialRepository;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.CreateTutorialRequestDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.CreateTutorialResponseDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.TutorialResponseDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.UpdateTutorialRequestDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class TutorialService
{
    @Autowired
    TutorialRepository tutorialRepository;

    @Autowired
    TutorialMapper tutorialMapper;

    //public List<TutorialEntity> listTutorials(String title)
    public List<TutorialResponseDTO> listTutorials(String title)
    {
        List<TutorialEntity> list = new ArrayList<>();
        if(title == null)
            tutorialRepository.findAll().forEach(list::add);
        else
            tutorialRepository.findByTitleContaining(title).forEach(list::add);
        List<TutorialResponseDTO> responses = new ArrayList<>();
        for(TutorialEntity t : list)
            responses.add(tutorialMapper.toResponse(t));
        return responses;
    }

    public TutorialEntity getTutorialById(long tutorialId)
    {
        if(tutorialId < 0)
            throw new BadParameterException("tutorialId");
        Optional<TutorialEntity> tutorial = tutorialRepository.findById(tutorialId);
        if(tutorial.isEmpty())
            throw new ResourceNotFoundException("tutorial not found", tutorialId);
        return tutorial.get();
    }

    public List<TutorialEntity> listPublishedTutorils()
    {
        List<TutorialEntity> tutorials = tutorialRepository.findByPublished(true);
        return tutorials;
    }

    public CreateTutorialResponseDTO createTutorial(CreateTutorialRequestDTO request)
    {
        if(request.getTitle() == null)
            throw new BadParameterException("title");
        if(request.getDescription() == null)
            throw new BadParameterException("description");
        TutorialEntity entity = tutorialMapper.toEntity(request);
        tutorialRepository.saveAndFlush(entity);
        CreateTutorialResponseDTO response = tutorialMapper.toCreateResponse(entity);
        return response;

        /*TutorialEntity tutorialEntity = tutorialRepository.saveAndFlush(TutorialEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .published(false).build());
        return CreateTutorialResponseDTO.builder()
                .id(tutorialEntity.getId())
                .title(tutorialEntity.getTitle())
                .description(tutorialEntity.getDescription())
                .published(tutorialEntity.isPublished()).build();*/
    }

    public TutorialEntity updateTutorial(long tutorialId, UpdateTutorialRequestDTO request)
    {
        if(tutorialId < 0)
            throw new BadParameterException("tutorialId");
        if(request == null)
            throw new BadParameterException("request");
        Optional<TutorialEntity> tutorial = tutorialRepository.findById(tutorialId);
        if(tutorial.isEmpty())
            throw new ResourceNotFoundException("tutorial not found", tutorialId);
        TutorialEntity tutorialToUpdate = tutorial.get();
        TutorialEntity tutorialRequest = tutorialMapper.toEntity(request);
        tutorialToUpdate.setTitle(tutorialRequest.getTitle());
        tutorialToUpdate.setDescription(tutorialRequest.getDescription());
        tutorialToUpdate.setPublished(tutorialRequest.isPublished());

        /*if(tutorialRequest.getTitle() != null)
            tutorialToUpdate.setTitle(tutorialRequest.getTitle());
        if(tutorialRequest.getDescription() != null)
            tutorialToUpdate.setDescription(tutorialRequest.getDescription());
        if(tutorialRequest.getPublished() != null)
        {
            boolean published = Boolean.parseBoolean(request.getPublished());
            tutorialToUpdate.setPublished(published);
        }*/
        return tutorialRepository.saveAndFlush(tutorialToUpdate);
    }

    public void deleteTutorial(long tutorialId)
    {
        if(tutorialId < 0)
            throw new BadParameterException("tutorialId");
        if(tutorialRepository.existsById(tutorialId))
            tutorialRepository.deleteById(tutorialId);
    }

    public void deleteAllTutorials()
    {
        tutorialRepository.deleteAll();
    }
}
