package it.tai.springpostresqljpa.springpostresqljpa.controller;

import it.tai.springpostresqljpa.springpostresqljpa.exceptions.ResourceNotFoundException;
import it.tai.springpostresqljpa.springpostresqljpa.model.Comment;
import it.tai.springpostresqljpa.springpostresqljpa.model.Tutorial;
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
public class CommentController
{
    @Autowired
    private TutorialRepository tutorialRepository;
    @Autowired
    CommentRepository commentRepository;

    @GetMapping("/tutorials/{tutorialId}/comments")
    public ResponseEntity<List<Comment>> getAllCommentsByTutorialId(@PathVariable(value = "tutorialId") long tutorialId)
    {
        if(!tutorialRepository.existsById(tutorialId))
            throw new ResourceNotFoundException("Not found Tutorial with id "+ tutorialId);
        List<Comment> comments = commentRepository.findByTutorialId(tutorialId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<Comment> getCommentsByTutorialId(@PathVariable(value = "id") Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Comment with id = " + id));
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PostMapping("/tutorials/{tutorialId}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable(value = "tutorialId") long tutorialId, @RequestBody Comment commentRequest)
    {
        Comment comment = tutorialRepository.findById(tutorialId).map(tutorial ->
        {
            commentRequest.setTutorial(tutorial);
            return commentRepository.save(commentRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found tutorial with id "+tutorialId));
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Comment> updateComment(@PathVariable("id") long id, @RequestBody Comment commentRequest)
    {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("commentId "+id+" not found"));
        comment.setContent(commentRequest.getContent());
        return new ResponseEntity<>(commentRepository.save(comment), HttpStatus.OK);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") long id) {
        commentRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/tutorials/{tutorialId}/comments")
    public ResponseEntity<List<Comment>> deleteAllCommentsOfTutorial(@PathVariable(value = "tutorialId") Long tutorialId) {
        if (!tutorialRepository.existsById(tutorialId))
            throw new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId);
        commentRepository.deleteByTutorialId(tutorialId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
