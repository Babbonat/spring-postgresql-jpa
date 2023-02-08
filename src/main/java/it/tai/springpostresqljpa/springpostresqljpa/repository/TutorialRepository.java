package it.tai.springpostresqljpa.springpostresqljpa.repository;

import it.tai.springpostresqljpa.springpostresqljpa.model.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

//interfaccia che estende JpaRepository per i metodi CRUD e i custom finder methods
public interface TutorialRepository extends JpaRepository<Tutorial, Long>
{
    List<Tutorial> findByPublished(boolean published);
    List<Tutorial> findByTitleContaining(String title);
}
