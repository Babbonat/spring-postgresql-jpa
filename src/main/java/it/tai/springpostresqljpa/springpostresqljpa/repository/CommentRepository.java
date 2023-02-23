package it.tai.springpostresqljpa.springpostresqljpa.repository;

import it.tai.springpostresqljpa.springpostresqljpa.domain.CommentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long>
{
    List<CommentEntity> findByTutorialId(long tutorialId);
    @Transactional
    void deleteByTutorialId(long tutorialId);
}
