package it.tai.springpostresqljpa.springpostresqljpa.controller;

import it.tai.springpostresqljpa.springpostresqljpa.exceptions.ResourceNotFoundException;
import it.tai.springpostresqljpa.springpostresqljpa.model.Tag;
import it.tai.springpostresqljpa.springpostresqljpa.model.Tutorial;
import it.tai.springpostresqljpa.springpostresqljpa.repository.TagRepository;
import it.tai.springpostresqljpa.springpostresqljpa.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class TagController
{
    @Autowired
    private TutorialRepository tutorialRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("/tags")
    public ResponseEntity<List<Tag>> getAllTags()
    {
        List<Tag> tags = new ArrayList<>();
        tagRepository.findAll().forEach(tags::add);
        if(tags.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping("/tutorials/{tutorialId}/tags")
    public ResponseEntity<List<Tag>> getAllTagsByTutorialId(@PathVariable(value = "tutorialId") long tutorialId)
    {
        if(!tutorialRepository.existsById(tutorialId))
            throw new ResourceNotFoundException("Not found Tutorial with id "+tutorialId);
        List<Tag> tags = tagRepository.findTagsByTutorialsId(tutorialId);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping("/tags/{id}")
    public ResponseEntity<Tag> getTagsById(@PathVariable(value = "id") long id)
    {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + id));
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @GetMapping("/tags/{tagId}/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorialsByTagId(@PathVariable(value = "tagId") long tagId)
    {
        if(!tagRepository.existsById(tagId))
            throw new ResourceNotFoundException("Not found Tag with id = "+tagId);
        List<Tutorial> tutorials = tutorialRepository.findTutorialsByTagsId(tagId);
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }

    @PostMapping("/tutorials/{tutorialId}/tags")
    public ResponseEntity<Tag> addTag(@PathVariable(value = "tutoiralId") long tutorialId,
                                      @RequestBody Tag tagRequest)
    {
        Tag tag = tutorialRepository.findById(tutorialId).map(tutorial -> {
            long tagId = tagRequest.getId();
            if(tagId != 0L)     //tag esiste
            {
                Tag t = tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = "+ tagId));
                tutorial.addTag(t);
                tutorialRepository.save(tutorial);
                return t;
            }
            tutorial.addTag(tagRequest);
            return tagRepository.save(tagRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = "+ tutorialId));
        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }

    @PutMapping("/tags/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable("id") long id, @RequestBody Tag tagRequest)
    {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = "+id));
        tag.setName(tagRequest.getName());
        return new ResponseEntity<>(tagRepository.save(tag), HttpStatus.OK);
    }

    @DeleteMapping("/tutorials/{tutorialId}/tags/{tagId}")
    public ResponseEntity<HttpStatus> deleteTagFromTutorial(@PathVariable(value = "tutorialId") Long tutorialId, @PathVariable(value = "tagId") Long tagId) {
        Tutorial tutorial = tutorialRepository.findById(tutorialId).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));
        tutorial.removeTag(tagId);
        tutorialRepository.save(tutorial);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/tags/{id}")
    public ResponseEntity<HttpStatus> deleteTag(@PathVariable("id") long id) {
        tagRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
