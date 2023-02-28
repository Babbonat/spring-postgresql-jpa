package it.tai.springpostresqljpa.springpostresqljpa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.tai.springpostresqljpa.springpostresqljpa.domain.TutorialEntity;
import it.tai.springpostresqljpa.springpostresqljpa.services.TutorialService;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tutorialsDTO.CreateTutorialRequestDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tutorialsDTO.CreateTutorialResponseDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tutorialsDTO.TutorialResponseDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tutorialsDTO.UpdateTutorialRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//rest controller che fa il request mapping dei metodi per restful request
@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api")
public class TutorialController
{
    @Autowired
    TutorialService tutorialService;

    @Operation(summary = "get all tutorials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "lists all tutorials",
                         content = {@Content(mediaType = "application/json",
                         schema = @Schema(implementation = TutorialEntity.class))}),
            @ApiResponse(responseCode = "204",
                         description = "no tutorials found",
                         content = @Content)
    })
    @GetMapping("/tutorials")
    public ResponseEntity<List<TutorialResponseDTO>> getAllTutorials(@RequestParam(required = false) String title)
    {
        List<TutorialResponseDTO> tutorials = tutorialService.listTutorials(title);
        if(tutorials.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(tutorials);

        /*List<TutorialEntity> tutorialEntities = new ArrayList<>();
        //Order order = new Order(Sort.Direction.ASC, "id");
        if (title == null)
            tutorialRepository.findAll(Sort.by("id")).forEach(tutorialEntities::add);
        //tutorialRepository.findAll(Sort.by(order)).forEach(tutorials::add);
        else
            tutorialRepository.findByTitleContaining(title).forEach(tutorialEntities::add);
        if(tutorialEntities.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(tutorialEntities, HttpStatus.OK);*/
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<TutorialEntity> getTutorialById(@PathVariable("id") long tutorialId)
    {
        TutorialEntity tutorial = tutorialService.getTutorialById(tutorialId);
        return ResponseEntity.ok(tutorial);

        /*TutorialEntity t = tutorialRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));
        return new ResponseEntity<>(t, HttpStatus.CREATED);*/
    }

    @PostMapping("/tutorials")
    public ResponseEntity<CreateTutorialResponseDTO> createTutorial(@RequestBody CreateTutorialRequestDTO request)
    {
        CreateTutorialResponseDTO response = this.tutorialService.createTutorial(request);
        return ResponseEntity.ok(response);

        /*try
        {
            TutorialEntity t = tutorialRepository.save(new TutorialEntity(tutorialEntity.getTitle(), tutorialEntity.getDescription(),false));
            return new ResponseEntity<>(t, HttpStatus.CREATED);
         } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }*/
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<TutorialEntity> updateTutorial(@PathVariable("id") long id, @RequestBody UpdateTutorialRequestDTO request)
    {
        TutorialEntity tutorial = tutorialService.updateTutorial(id, request);
        return ResponseEntity.ok(tutorial);

        /*TutorialEntity t = tutorialRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));
        t.setTitle(tutorialEntity.getTitle());
        t.setDescription(tutorialEntity.getDescription());
        t.setPublished(tutorialEntity.isPublished());
        return new ResponseEntity<>(tutorialRepository.save(t), HttpStatus.OK);*/
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        tutorialService.deleteTutorial(id);
        return ResponseEntity.ok().build();
        /*tutorialRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);*/
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        tutorialService.deleteAllTutorials();
        return ResponseEntity.ok().build();

        /*tutorialRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);*/
    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<List<TutorialEntity>> findByPublished() {
        List<TutorialEntity> tutorials = tutorialService.listPublishedTutorils();
        if(tutorials.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(tutorials);

        /*List<TutorialEntity> tutorialEntities = tutorialRepository.findByPublished(true);
        if (tutorialEntities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tutorialEntities, HttpStatus.OK);*/
    }
}
