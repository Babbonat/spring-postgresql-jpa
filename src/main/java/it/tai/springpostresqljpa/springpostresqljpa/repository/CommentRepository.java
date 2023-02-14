package it.tai.springpostresqljpa.springpostresqljpa.repository;

import it.tai.springpostresqljpa.springpostresqljpa.model.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>
{
    List<Comment> findByTutorialId(long postId);
    @Transactional
    void deleteByTutorialId(long tutorialId);
}
