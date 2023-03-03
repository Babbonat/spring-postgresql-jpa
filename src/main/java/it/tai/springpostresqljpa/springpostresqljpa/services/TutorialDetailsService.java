package it.tai.springpostresqljpa.springpostresqljpa.services;

import it.tai.springpostresqljpa.springpostresqljpa.domain.TutorialDetailsEntity;
import it.tai.springpostresqljpa.springpostresqljpa.domain.TutorialEntity;
import it.tai.springpostresqljpa.springpostresqljpa.exceptions.BadParameterException;
import it.tai.springpostresqljpa.springpostresqljpa.exceptions.ResourceNotFoundException;
import it.tai.springpostresqljpa.springpostresqljpa.mapper.TutorialDetailsMapper;
import it.tai.springpostresqljpa.springpostresqljpa.repository.TutorialDetailsRepository;
import it.tai.springpostresqljpa.springpostresqljpa.repository.TutorialRepository;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tutorialDetailsDTO.TDRequestDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tutorialDetailsDTO.TDResponseDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class TutorialDetailsService
{
    @Autowired
    private TutorialRepository tutorialRepository;
    @Autowired
    private TutorialDetailsRepository detailsRepository;
    @Autowired
    private TutorialDetailsMapper detailsMapper;

    public TDResponseDTO getDetailsById(long id)
    {
        if(id < 0)
            throw new BadParameterException("id");
        Optional<TutorialDetailsEntity> details = detailsRepository.findById(id);
        if(details.isEmpty())
            throw new ResourceNotFoundException("details not found", id);
        return detailsMapper.toResponse(details.get());
    }

    public TDResponseDTO createDetails(long tutorialId, TDRequestDTO request)
    {
        if(tutorialId < 0)
            throw new BadParameterException("tutorialId");
        if(request == null)
            throw new BadParameterException("request");
        if(request.getCreatedBy() == null)
            throw new BadParameterException("request.createdBy");
        Optional<TutorialEntity> tutorial = tutorialRepository.findById(tutorialId);
        if(tutorial.isEmpty())
            throw new ResourceNotFoundException("tutorial not found", tutorialId);
        TutorialDetailsEntity details = detailsMapper.toEntity(request);
        details.setCreatedOn(new Date());
        details.setTutorial(tutorial.get());
        detailsRepository.saveAndFlush(details);
        return detailsMapper.toResponse(details);
    }

    public TDResponseDTO updateDetails(long id, TDRequestDTO request)
    {
        if(id < 0)
            throw new BadParameterException("id");
        if(request == null)
            throw new BadParameterException("request");
        if(request.getCreatedBy() == null)
            throw new BadParameterException("request.createdBy");
        Optional<TutorialDetailsEntity> detailsToUpdate = detailsRepository.findById(id);
        if(detailsToUpdate.isEmpty())
            throw new ResourceNotFoundException("details not found", id);
        TutorialDetailsEntity details = detailsMapper.toEntity(request);
        detailsToUpdate.get().setCreatedBy(details.getCreatedBy());
        detailsRepository.saveAndFlush(detailsToUpdate.get());
        return detailsMapper.toResponse(detailsToUpdate.get());
    }

    public void deleteDetails(long id)
    {
        if(detailsRepository.existsById(id))
            detailsRepository.deleteById(id);
    }

    public void deleteTutorialDetails(long tutorialId)
    {
        if(!tutorialRepository.existsById(tutorialId))
            throw new ResourceNotFoundException("tutorial not found", tutorialId);
        detailsRepository.deleteByTutorialId(tutorialId);
    }
}
