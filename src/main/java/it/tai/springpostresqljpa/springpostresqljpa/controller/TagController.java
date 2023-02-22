package it.tai.springpostresqljpa.springpostresqljpa.controller;

import it.tai.springpostresqljpa.springpostresqljpa.exceptions.ResourceNotFoundException;
import it.tai.springpostresqljpa.springpostresqljpa.model.Tag;
import it.tai.springpostresqljpa.springpostresqljpa.model.Tutorial;
import it.tai.springpostresqljpa.springpostresqljpa.repository.TagRepository;
import it.tai.springpostresqljpa.springpostresqljpa.repository.TutorialRepository;
import it.tai.springpostresqljpa.springpostresqljpa.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class TagController
{
    @Autowired
    private TutorialRepository tutorialRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public ResponseEntity<List<Tag>> getAllTags()
    {
        List<Tag> tags = this.tagService.listTags();
        return (tags != null && tags.size() != 0) ? ResponseEntity.ok(tags) : ResponseEntity.noContent().build();

        /*List<Tag> tags = new ArrayList<>();
        tagRepository.findAll().forEach(tags::add);
        if(tags.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(tags, HttpStatus.OK);*/
    }

    @GetMapping("/tags/{id}")
    public ResponseEntity<Tag> getTagsById(@PathVariable(value = "id") long id)
    {
        return ResponseEntity.ok(this.tagService.getTag(id));

        /*Tag tag = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + id));
        return new ResponseEntity<>(tag, HttpStatus.OK);*/
    }

    @GetMapping("/tutorials/{tutorialId}/tags")
    public ResponseEntity<List<Tag>> getAllTagsByTutorialId(@PathVariable(value = "tutorialId") long tutorialId)
    {
        List<Tag> tags = this.tagService.getTagsByTutorialId(tutorialId);
        return (tags != null && tags.size() != 0) ? ResponseEntity.ok(tags) : ResponseEntity.noContent().build();

        /*if(!tutorialRepository.existsById(tutorialId))
            throw new ResourceNotFoundException("Not found Tutorial with id "+tutorialId);
        List<Tag> tags = tagRepository.findTagsByTutorialsId(tutorialId);
        return new ResponseEntity<>(tags, HttpStatus.OK);*/
    }

    @GetMapping("/tags/{tagId}/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorialsByTagId(@PathVariable(value = "tagId") long tagId)
    {
        List<Tutorial> tutorials = this.tagService.getTutorialsByTagId(tagId);
        return (tutorials != null && tutorials.size() != 0) ? ResponseEntity.ok(tutorials) : ResponseEntity.noContent().build();

        /*if(!tagRepository.existsById(tagId))
            throw new ResourceNotFoundException("Not found Tag with id = "+tagId);
        List<Tutorial> tutorials = tutorialRepository.findTutorialsByTagsId(tagId);
        return new ResponseEntity<>(tutorials, HttpStatus.OK);*/
    }

    @PostMapping("/tutorials/{tutorialId}/tags/{tagName}")
    public ResponseEntity addTag(@PathVariable(value = "tutorialId") long tutorialId, @PathVariable(value = "tagName") String tagName)
    {
        this.tagService.tagTutorial(tutorialId, tagName);
        return ResponseEntity.ok().build();

        /*Tag tag = tutorialRepository.findById(tutorialId).map(tutorial -> {
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
        return new ResponseEntity<>(tag, HttpStatus.CREATED);*/
    }

    @PutMapping("/tags/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable("id") long id, @RequestBody Tag tagRequest)
    {
        this.tagService.updateTagInfo(id, tagRequest);
        return ResponseEntity.ok().build();

        /*Tag tag = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = "+id));
        tag.setName(tagRequest.getName());
        return new ResponseEntity<>(tagRepository.save(tag), HttpStatus.OK);*/
    }

    @DeleteMapping("/tutorials/{tutorialId}/tags/{tagId}")
    public ResponseEntity<HttpStatus> deleteTagFromTutorial(@PathVariable(value = "tutorialId") long tutorialId, @PathVariable(value = "tagId") Long tagId) {
        this.tagService.untagTutorial(tutorialId, tagId);
        return ResponseEntity.ok().build();

        /*Tutorial tutorial = tutorialRepository.findById(tutorialId).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));
        tutorial.removeTag(tagId);
        tutorialRepository.save(tutorial);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);*/
    }

    @DeleteMapping("/tags/{id}")
    public ResponseEntity<HttpStatus> deleteTag(@PathVariable("id") long id) {
        this.tagService.deleteTag(id);
        return ResponseEntity.ok().build();

        /*tagRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);*/
    }
}
