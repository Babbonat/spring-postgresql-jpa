package it.tai.springpostresqljpa.springpostresqljpa.repository;

import it.tai.springpostresqljpa.springpostresqljpa.model.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

//interfaccia che estende JpaRepository per i metodi CRUD e i custom finder methods
//JpaRepository estende PagingAndSortingRepository (metodi per paginare e oridinare i record) che a
//sua volta estende CrudRepository (sostanzialmente metodi CRUD)
public interface TutorialRepository extends JpaRepository<Tutorial, Long>
{
    List<Tutorial> findByPublished(boolean published);
    List<Tutorial> findByTitleContaining(String title);
    List<Tutorial> findTutorialsByTagsId(Long tagId);
}
