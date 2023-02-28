package it.tai.springpostresqljpa.springpostresqljpa.controller;

import it.tai.springpostresqljpa.springpostresqljpa.domain.TagEntity;
import it.tai.springpostresqljpa.springpostresqljpa.repository.TagRepository;
import it.tai.springpostresqljpa.springpostresqljpa.repository.TutorialRepository;
import it.tai.springpostresqljpa.springpostresqljpa.services.TagService;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tagsDTO.UpdateTagRequestDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tutorialsDTO.TutorialResponseDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tagsDTO.TagsResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public ResponseEntity<List<TagsResponseDTO>> getAllTags()
    {
        List<TagsResponseDTO> tagEntities = this.tagService.listTags();
        return (tagEntities != null && tagEntities.size() != 0) ? ResponseEntity.ok(tagEntities) : ResponseEntity.noContent().build();
    }

    @GetMapping("/tags/{id}")
    public ResponseEntity<TagEntity> getTagsById(@PathVariable(value = "id") long id)
    {
        return ResponseEntity.ok(this.tagService.getTag(id));
    }

    @GetMapping("/tutorials/{tutorialId}/tags")
    public ResponseEntity<List<TagsResponseDTO>> getAllTagsByTutorialId(@PathVariable(value = "tutorialId") long tutorialId)
    {
        List<TagsResponseDTO> tagEntities = this.tagService.getTagsByTutorialId(tutorialId);
        if(tagEntities == null && tagEntities.size() == 0)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(tagEntities);
    }

    @GetMapping("/tags/{tagId}/tutorials")
    public ResponseEntity<List<TutorialResponseDTO>> getAllTutorialsByTagId(@PathVariable(value = "tagId") long tagId)
    {
        List<TutorialResponseDTO> tutorialEntities = this.tagService.getTutorialsByTagId(tagId);
        if(tutorialEntities == null && tutorialEntities.size() == 0)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(tutorialEntities);
    }

    @GetMapping("/tags/{tagName}/tutorials")
    public ResponseEntity<List<TutorialResponseDTO>> getAllTutorialsByTagName(@PathVariable(value = "tagName") String tagName)
    {
        List<TutorialResponseDTO> tutorialEntities = this.tagService.getTutorialsByTagName(tagName);
        if(tutorialEntities == null && tutorialEntities.size() == 0)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(tutorialEntities);
    }

    @PostMapping("/tutorials/{tutorialId}/tags/{tagName}")
    public ResponseEntity addTag(@PathVariable(value = "tutorialId") long tutorialId, @PathVariable(value = "tagName") String tagName)
    {
        this.tagService.tagTutorial(tutorialId, tagName);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/tags/{id}")
    public ResponseEntity<TagEntity> updateTag(@PathVariable("id") long id, @RequestBody UpdateTagRequestDTO request)
    {
        this.tagService.updateTagInfo(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tutorials/{tutorialId}/tags/{tagId}")
    public ResponseEntity<HttpStatus> deleteTagFromTutorial(@PathVariable(value = "tutorialId") long tutorialId, @PathVariable(value = "tagId") Long tagId) {
        this.tagService.untagTutorial(tutorialId, tagId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tags/{id}")
    public ResponseEntity<HttpStatus> deleteTag(@PathVariable("id") long id) {
        this.tagService.deleteTag(id);
        return ResponseEntity.ok().build();
    }
}
