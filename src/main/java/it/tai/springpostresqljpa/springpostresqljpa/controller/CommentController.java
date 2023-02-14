package it.tai.springpostresqljpa.springpostresqljpa.controller;

import it.tai.springpostresqljpa.springpostresqljpa.exceptions.ResourceNotFoundException;
import it.tai.springpostresqljpa.springpostresqljpa.model.Comment;
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
        List<Comment> comments = commentRepository.findByTutorialId((tutorialId));
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping("/tutorials/{tutorialId}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable(value = "tutorialIs") long tutorialId,
                                                 @RequestBody Comment commentRequest)
    {
        Comment comment = tutorialRepository.findById(tutorialId).map(tutorial ->
        {
            commentRequest.setTutorial(tutorial);
            return commentRepository.save(commentRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found tutorial with id "+tutorialId));
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }
}
