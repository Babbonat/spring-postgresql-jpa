package it.tai.springpostresqljpa.springpostresqljpa.controller;

import it.tai.springpostresqljpa.springpostresqljpa.exceptions.ResourceNotFoundException;
import it.tai.springpostresqljpa.springpostresqljpa.domain.TutorialEntity;
import it.tai.springpostresqljpa.springpostresqljpa.domain.TutorialDetailsEntity;
import it.tai.springpostresqljpa.springpostresqljpa.repository.TutorialDetailsRepository;
import it.tai.springpostresqljpa.springpostresqljpa.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class TutorialDetailsController
{
    @Autowired
    private TutorialDetailsRepository detailsRepository;
    @Autowired
    private TutorialRepository tutorialRepository;
    @GetMapping({"/details/{id}", "/tutorials/{id}/details"})
    public ResponseEntity<TutorialDetailsEntity> getDetailsById(@PathVariable(value = "id") long id)
    {
        TutorialDetailsEntity details = detailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found TutotialDetails with id "+id));
        return new ResponseEntity<>(details, HttpStatus.OK);
        /*Optional<TutorialDetails> opt = detailsRepository.findById(id);
        if(opt.isEmpty())
            throw new ResourceNotFoundException("Not found TutotialDetails with id "+id);
        else {
            TutorialDetails details = opt.get();
            return new ResponseEntity<>(details, HttpStatus.OK);
        }*/
    }
    @PostMapping("/tutorials/{tutorialId}/details")
    public ResponseEntity<TutorialDetailsEntity> createDetails(@PathVariable(value = "tutorialId") Long tutorialId,
                                                               @RequestBody TutorialDetailsEntity detailsRequest)
    {
        TutorialEntity t = tutorialRepository.findById(tutorialId).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id "+ tutorialId));
        detailsRequest.setCreatedOn(new Date());
        detailsRequest.setTutorialEntity(t);
        TutorialDetailsEntity details = detailsRepository.save(detailsRequest);
        return new ResponseEntity<>(details, HttpStatus.CREATED);
    }

    @PutMapping("/details/{id}")
    public ResponseEntity<TutorialDetailsEntity> updateDetails(@PathVariable("id") long id,
                                                               @RequestBody TutorialDetailsEntity detailsRequest)
    {
        TutorialDetailsEntity details = detailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id "+id+" not found"));
        details.setCreatedBy(detailsRequest.getCreatedBy());
        return new ResponseEntity<>(detailsRepository.save(details), HttpStatus.OK);
    }

    @DeleteMapping("/details/{id}")
    public ResponseEntity<HttpStatus> deleteDetails(@PathVariable("id") long id)
    {
        detailsRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/tutorials/{tutorialId}/details")
    public ResponseEntity<TutorialDetailsEntity> deleteDetailsOfTutorial(@PathVariable(value = "tutorialId") long tutorialId)
    {
        if(!tutorialRepository.existsById(tutorialId))
            throw new ResourceNotFoundException("Not found Tutorial with id "+tutorialId);
        detailsRepository.deleteByTutorialId(tutorialId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
