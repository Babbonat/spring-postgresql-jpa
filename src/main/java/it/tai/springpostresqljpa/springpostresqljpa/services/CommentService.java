package it.tai.springpostresqljpa.springpostresqljpa.services;

import it.tai.springpostresqljpa.springpostresqljpa.domain.CommentEntity;
import it.tai.springpostresqljpa.springpostresqljpa.domain.TutorialEntity;
import it.tai.springpostresqljpa.springpostresqljpa.exceptions.BadParameterException;
import it.tai.springpostresqljpa.springpostresqljpa.exceptions.ResourceNotFoundException;
import it.tai.springpostresqljpa.springpostresqljpa.mapper.CommentMapper;
import it.tai.springpostresqljpa.springpostresqljpa.repository.CommentRepository;
import it.tai.springpostresqljpa.springpostresqljpa.repository.TutorialRepository;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.commentsDTO.CommentRequestDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.commentsDTO.CommentResponseDTO;import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class CommentService
{
    @Autowired
    private TutorialRepository tutorialRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentMapper commentMapper;

    public List<CommentResponseDTO> listCommentsByTutorial(long tutorialId)
    {
        if(tutorialId < 0)
            throw new BadParameterException("tutorialId");
        if(!tutorialRepository.existsById(tutorialId))
            throw new ResourceNotFoundException("tutorial not found", tutorialId);
        List<CommentEntity> comments = commentRepository.findByTutorialId(tutorialId);
        if(comments.isEmpty())
            throw new ResourceNotFoundException("no comments found");
        List<CommentResponseDTO> response = new ArrayList<>();
        comments.forEach(comment -> response.add(commentMapper.toResponse(comment)));
        return response;
    }

    public CommentResponseDTO getCommentById(long commentId)
    {
        if(commentId < 0)
            throw new BadParameterException("commentId");
        Optional<CommentEntity> comment = commentRepository.findById(commentId);
        if(comment.isEmpty())
            throw new ResourceNotFoundException("comment not found", commentId);
        return commentMapper.toResponse(comment.get());
    }

    public CommentResponseDTO commentTutorial(long tutorialId, CommentRequestDTO request)
    {
        if(tutorialId < 0)
            throw new BadParameterException("tutorialId");
        if(request == null)
            throw new BadParameterException("request");
        if(request.getContent() == null)
            throw new BadParameterException("request.content");
        Optional<TutorialEntity> tutorial = tutorialRepository.findById(tutorialId);
        if(tutorial.isEmpty())
            throw new ResourceNotFoundException("tutorial not found", tutorialId);
        CommentEntity comment = commentMapper.toEntity(request);
        comment.setTutorial(tutorial.get());
        commentRepository.saveAndFlush(comment);
        return commentMapper.toResponse(comment);
    }

    public CommentResponseDTO editComment(long commentId, CommentRequestDTO request)
    {
        if(commentId < 0)
            throw new BadParameterException("commentId");
        if(request == null)
            throw new BadParameterException("request");
        if(request.getContent() == null)
            throw new BadParameterException("request.content");
        Optional<CommentEntity> commentToUpdate = commentRepository.findById(commentId);
        if(commentToUpdate.isEmpty())
            throw new ResourceNotFoundException("comment not found", commentId);
        CommentEntity comment = commentMapper.toEntity(request);
        commentToUpdate.get().setContent(comment.getContent());
        commentRepository.saveAndFlush(commentToUpdate.get());
        return commentMapper.toResponse(commentToUpdate.get());
    }

    public void deleteComment(long commentId)
    {
        if(commentRepository.existsById(commentId))
            commentRepository.deleteById(commentId);
    }

    public void deleteAllComments(long tutorialId)
    {
        if(!tutorialRepository.existsById(tutorialId))
            throw new ResourceNotFoundException("tutorial not found", tutorialId);
        commentRepository.deleteByTutorialId(tutorialId);
    }
}
