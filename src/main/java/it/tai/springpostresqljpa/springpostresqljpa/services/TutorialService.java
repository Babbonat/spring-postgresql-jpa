package it.tai.springpostresqljpa.springpostresqljpa.services;

import it.tai.springpostresqljpa.springpostresqljpa.domain.TutorialEntity;
import it.tai.springpostresqljpa.springpostresqljpa.exceptions.BadParameterException;
import it.tai.springpostresqljpa.springpostresqljpa.exceptions.ResourceNotFoundException;
import it.tai.springpostresqljpa.springpostresqljpa.mapper.TutorialMapper;
import it.tai.springpostresqljpa.springpostresqljpa.repository.TutorialRepository;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tutorialsDTO.CreateTutorialRequestDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tutorialsDTO.CreateTutorialResponseDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tutorialsDTO.TutorialResponseDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tutorialsDTO.UpdateTutorialRequestDTO;
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

    public TutorialResponseDTO getTutorialById(long tutorialId)
    {
        if(tutorialId < 0)
            throw new BadParameterException("tutorialId");
        Optional<TutorialEntity> tutorial = tutorialRepository.findById(tutorialId);
        if(tutorial.isEmpty())
            throw new ResourceNotFoundException("tutorial not found", tutorialId);
        return tutorialMapper.toResponse(tutorial.get());
    }

    public List<TutorialResponseDTO> listPublishedTutorils()
    {
        List<TutorialEntity> tutorials = tutorialRepository.findByPublished(true);
        if(tutorials.isEmpty())
            return null;
        List<TutorialResponseDTO> response = new ArrayList<>();
        tutorials.forEach(tutorial -> response.add(tutorialMapper.toResponse(tutorial)));
        return response;
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
    }

    public TutorialResponseDTO updateTutorial(long tutorialId, UpdateTutorialRequestDTO request)
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

        return tutorialMapper.toResponse(tutorialRepository.saveAndFlush(tutorialToUpdate));
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
