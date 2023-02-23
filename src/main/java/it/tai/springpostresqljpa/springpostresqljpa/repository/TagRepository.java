package it.tai.springpostresqljpa.springpostresqljpa.repository;


import it.tai.springpostresqljpa.springpostresqljpa.domain.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<TagEntity, Long>
{
    List<TagEntity> findTagsByTutorialsId(long tutorialId);
    Optional<TagEntity> findByName(String name);
}
