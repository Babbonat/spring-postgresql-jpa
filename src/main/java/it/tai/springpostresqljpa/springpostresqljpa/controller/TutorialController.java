package it.tai.springpostresqljpa.springpostresqljpa.controller;

import it.tai.springpostresqljpa.springpostresqljpa.model.Tutorial;
import it.tai.springpostresqljpa.springpostresqljpa.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    class TutorialsIdComparator implements Comparator<Tutorial> {
        // override the compare() method
        public int compare(Tutorial t1, Tutorial t2)
        {
            if (t1.getId() == t2.getId())
                return 0;
            else if (t1.getId() > t2.getId())
                return 1;
            else
                return -1;
        }
    }

    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title)
    {
        try
        {
            List<Tutorial> tutorials = new ArrayList<>();
            if (title == null)
                tutorialRepository.findAllByOrderByIdAsc().forEach(tutorials::add);
            else
                tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);
            if(tutorials.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id)
    {
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
        if(tutorialData.isPresent())
            return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
        if(tutorialData.isPresent())
        {
            Tutorial t = tutorialData.get();
            t.setTitle(tutorial.getTitle());
            t.setDescription(tutorial.getDescription());
            t.setPublished(tutorial.isPublished());
            return new ResponseEntity<>(tutorialRepository.save(t), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        try {
            tutorialRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        try {
            tutorialRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<List<Tutorial>> findByPublished() {
        try {
            List<Tutorial> tutorials = tutorialRepository.findByPublished(true);

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
