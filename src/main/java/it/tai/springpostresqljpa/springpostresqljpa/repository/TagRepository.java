package it.tai.springpostresqljpa.springpostresqljpa.repository;


import it.tai.springpostresqljpa.springpostresqljpa.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long>
{
    List<Tag> findTagsByTutorialsId(long tutorialId);
    Optional<Tag> findByName(String name);
}
