package it.tai.springpostresqljpa.springpostresqljpa.repository;


import it.tai.springpostresqljpa.springpostresqljpa.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long>
{
    List<Tag> findTagsByTutorialsId(long tutorialId);
}
