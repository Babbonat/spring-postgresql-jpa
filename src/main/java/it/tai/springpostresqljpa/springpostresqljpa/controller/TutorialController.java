package it.tai.springpostresqljpa.springpostresqljpa.controller;

import it.tai.springpostresqljpa.springpostresqljpa.exceptions.ResourceNotFoundException;
import it.tai.springpostresqljpa.springpostresqljpa.model.Tutorial;
import it.tai.springpostresqljpa.springpostresqljpa.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//rest controller che fa il request mapping dei metodi per restful request
@RestController
@RequestMapping("/api")
public class TutorialController
{
    @Autowired
    TutorialRepository tutorialRepository;

    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title)
    {
        List<Tutorial> tutorials = new ArrayList<>();
        //Order order = new Order(Sort.Direction.ASC, "id");
        if (title == null)
            tutorialRepository.findAll(Sort.by("id")).forEach(tutorials::add);
        //tutorialRepository.findAll(Sort.by(order)).forEach(tutorials::add);
        else
            tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);
        if(tutorials.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id)
    {
        Tutorial t = tutorialRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));
        return new ResponseEntity<>(t, HttpStatus.CREATED);
    }

    @PostMapping("/tutorials")
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial)
    {
        try
        {
            Tutorial t = tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(),false));
            return new ResponseEntity<>(t, HttpStatus.CREATED);
         } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial)
    {
        Tutorial t = tutorialRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));
        t.setTitle(tutorial.getTitle());
        t.setDescription(tutorial.getDescription());
        t.setPublished(tutorial.isPublished());
        return new ResponseEntity<>(tutorialRepository.save(t), HttpStatus.OK);
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        tutorialRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        tutorialRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<List<Tutorial>> findByPublished() {
        List<Tutorial> tutorials = tutorialRepository.findByPublished(true);
        if (tutorials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }
}
