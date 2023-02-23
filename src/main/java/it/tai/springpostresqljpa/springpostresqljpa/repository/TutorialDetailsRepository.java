package it.tai.springpostresqljpa.springpostresqljpa.repository;


import it.tai.springpostresqljpa.springpostresqljpa.domain.TutorialDetailsEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorialDetailsRepository extends JpaRepository<TutorialDetailsEntity, Long>
{
    @Transactional
    void deleteById(long id);
    @Transactional
    void deleteByTutorialId(long tutorialId);
}
