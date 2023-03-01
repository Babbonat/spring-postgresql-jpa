package it.tai.springpostresqljpa.springpostresqljpa.controller;

import io.swagger.v3.oas.annotations.Hidden;
import it.tai.springpostresqljpa.springpostresqljpa.exceptions.ResourceNotFoundException;
import it.tai.springpostresqljpa.springpostresqljpa.domain.CommentEntity;
import it.tai.springpostresqljpa.springpostresqljpa.repository.CommentRepository;
import it.tai.springpostresqljpa.springpostresqljpa.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
@Hidden
public class CommentController
{
    @Autowired
    private TutorialRepository tutorialRepository;
    @Autowired
    CommentRepository commentRepository;

    @GetMapping("/tutorials/{tutorialId}/comments")
    public ResponseEntity<List<CommentEntity>> getAllCommentsByTutorialId(@PathVariable(value = "tutorialId") long tutorialId)
    {
        if(!tutorialRepository.existsById(tutorialId))
            throw new ResourceNotFoundException("Not found Tutorial with id "+ tutorialId);
        List<CommentEntity> commentEntities = commentRepository.findByTutorialId(tutorialId);
        return new ResponseEntity<>(commentEntities, HttpStatus.OK);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentEntity> getCommentsByTutorialId(@PathVariable(value = "id") Long id) {
        CommentEntity commentEntity = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Comment with id = " + id));
        return new ResponseEntity<>(commentEntity, HttpStatus.OK);
    }

    @PostMapping("/tutorials/{tutorialId}/comments")
    public ResponseEntity<CommentEntity> createComment(@PathVariable(value = "tutorialId") long tutorialId, @RequestBody CommentEntity commentEntityRequest)
    {
        CommentEntity commentEntity = tutorialRepository.findById(tutorialId).map(tutorial ->
        {
            commentEntityRequest.setTutorial(tutorial);
            return commentRepository.save(commentEntityRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found tutorial with id "+tutorialId));
        return new ResponseEntity<>(commentEntity, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CommentEntity> updateComment(@PathVariable("id") long id, @RequestBody CommentEntity commentEntityRequest)
    {
        CommentEntity commentEntity = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("commentId "+id+" not found"));
        commentEntity.setContent(commentEntityRequest.getContent());
        return new ResponseEntity<>(commentRepository.save(commentEntity), HttpStatus.OK);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") long id) {
        commentRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/tutorials/{tutorialId}/comments")
    public ResponseEntity<List<CommentEntity>> deleteAllCommentsOfTutorial(@PathVariable(value = "tutorialId") Long tutorialId) {
        if (!tutorialRepository.existsById(tutorialId))
            throw new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId);
        commentRepository.deleteByTutorialId(tutorialId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
