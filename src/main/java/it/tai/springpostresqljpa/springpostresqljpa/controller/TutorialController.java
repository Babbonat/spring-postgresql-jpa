package it.tai.springpostresqljpa.springpostresqljpa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.tai.springpostresqljpa.springpostresqljpa.domain.TutorialEntity;
import it.tai.springpostresqljpa.springpostresqljpa.mapper.TutorialMapper;
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

    @Operation(summary = "Restituisce tutti i tutorial")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Restituisce tutti i tutorial",
                         content = {@Content(mediaType = "application/json",
                         schema = @Schema(implementation = TutorialResponseDTO.class))}),
            @ApiResponse(responseCode = "204",
                         description = "Nessun tutorial trovato",
                         content = @Content)
    })
    @GetMapping("/tutorials")
    public ResponseEntity<List<TutorialResponseDTO>> getAllTutorials(@RequestParam(required = false) String title)
    {
        List<TutorialResponseDTO> tutorials = tutorialService.listTutorials(title);
        if(tutorials.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(tutorials);
    }

    @Operation(summary = "Restituisce un tutorial dato il suo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Restituisce un tutorial dato il suo id",
                    content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = TutorialResponseDTO.class))}),
            @ApiResponse(responseCode = "204",
                    description = "Nessun tutorial trovato",
                    content = @Content)
    })
    @GetMapping("/tutorials/{id}")
    public ResponseEntity<TutorialResponseDTO> getTutorialById(@PathVariable("id") long tutorialId)
    {
        TutorialResponseDTO tutorial = tutorialService.getTutorialById(tutorialId);
        return ResponseEntity.ok(tutorial);
    }

    @Operation(summary = "Restituisce tutti i tutorial pubblicati")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Restituisce tutti i tutorial pubblicati",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TutorialResponseDTO.class))}),
            @ApiResponse(responseCode = "204",
                    description = "Nessun tutorial pubblicato trovato",
                    content = @Content)
    })
    @GetMapping("/tutorials/published")
    public ResponseEntity<List<TutorialResponseDTO>> findByPublished() {
        List<TutorialResponseDTO> tutorials = tutorialService.listPublishedTutorils();
        if(tutorials == null || tutorials.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(tutorials);
    }

    @Operation(summary = "Pubblica un nuovo tutorial")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Pubblica un nuovo tutorial",
                    content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CreateTutorialRequestDTO.class))}),
            @ApiResponse(responseCode = "400",
                         description = "Richiesta mal formata")
    })
    @PostMapping("/tutorials")
    public ResponseEntity<CreateTutorialResponseDTO> createTutorial(@RequestBody CreateTutorialRequestDTO request)
    {
        CreateTutorialResponseDTO response = this.tutorialService.createTutorial(request);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Aggiorna un Tutorial")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Aggiorna un Tutorial",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TutorialResponseDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Richiesta mal formata",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Tutorial non trovato",
                    content = @Content)
    })
    @PutMapping("/tutorials/{id}")
    public ResponseEntity<TutorialResponseDTO> updateTutorial(@PathVariable("id") long id, @RequestBody UpdateTutorialRequestDTO request)
    {
        TutorialResponseDTO tutorial = tutorialService.updateTutorial(id, request);
        return ResponseEntity.ok(tutorial);
    }

    @Operation(summary = "Elimina un Tutorial")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Elimina un Tutorial dato un id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TutorialResponseDTO.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Tutorial non trovato",
                    content = @Content)
    })
    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        tutorialService.deleteTutorial(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Elimina tutti i Tutorial")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Elimina tutti i Tutorial",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TutorialResponseDTO.class))})
    })
    @DeleteMapping("/tutorials")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        tutorialService.deleteAllTutorials();
        return ResponseEntity.ok().build();
    }
}
