package it.tai.springpostresqljpa.springpostresqljpa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.tai.springpostresqljpa.springpostresqljpa.exceptions.ErrorMessage;
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
    private TutorialService tutorialService;

    @GetMapping("/tutorials")
    @Operation(summary = "Restituisce tutti i Tutorial")
    @ApiResponse(responseCode = "200",
                 description = "Successo",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TutorialResponseDTO.class,
                                    type = "array")))
    @ApiResponse(responseCode = "404",
                 description = "Nessun Tutorial trovato",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "500",
                 description = "Errore interno del Server",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    public ResponseEntity<List<TutorialResponseDTO>> getAllTutorials(@RequestParam(required = false) String title)
    {
        List<TutorialResponseDTO> tutorials = tutorialService.listTutorials(title);
        return ResponseEntity.ok(tutorials);
    }

    @GetMapping("/tutorials/{id}")
    @Operation(summary = "Restituisce un Tutorial dato il suo id")
    @ApiResponse(responseCode = "200",
                 description = "Successo",
                 content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = TutorialResponseDTO.class,
                                     type = "array")))
    @ApiResponse(responseCode = "400",
                 description = "Richiesta errata",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "404",
                 description = "Nessun Tutorial trovato",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "500",
                 description = "Errore interno del Server",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    public ResponseEntity<TutorialResponseDTO> getTutorialById(@PathVariable("id") long tutorialId)
    {
        TutorialResponseDTO tutorial = tutorialService.getTutorialById(tutorialId);
        return ResponseEntity.ok(tutorial);
    }

    @GetMapping("/tutorials/published")
    @Operation(summary = "Restituisce tutti i Tutorial pubblicati")
    @ApiResponse(responseCode = "200",
                 description = "Successo",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TutorialResponseDTO.class,
                                    type = "array")))
    @ApiResponse(responseCode = "404",
                 description = "Nessun Tutorial trovato",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "500",
                 description = "Errore interno del Server",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    public ResponseEntity<List<TutorialResponseDTO>> findByPublished() {
        List<TutorialResponseDTO> tutorials = tutorialService.listPublishedTutorils();
        return ResponseEntity.ok(tutorials);
    }

    @PostMapping("/tutorials")
    @Operation(summary = "Crea un nuovo Tutorial")
    @ApiResponse(responseCode = "201",
                 description = "Successo",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CreateTutorialResponseDTO.class)))
    @ApiResponse(responseCode = "400",
                 description = "Richiesta errata",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "500",
                 description = "Errore interno del Server",
                 content = @Content(mediaType = "application/json",
                 schema = @Schema(implementation = ErrorMessage.class)))
    public ResponseEntity<CreateTutorialResponseDTO> createTutorial(@RequestBody CreateTutorialRequestDTO request)
    {
        CreateTutorialResponseDTO response = this.tutorialService.createTutorial(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/tutorials/{id}")
    @Operation(summary = "Aggiorna un Tutorial")
    @ApiResponse(responseCode = "200",
                 description = "Successo",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TutorialResponseDTO.class)))
    @ApiResponse(responseCode = "400",
                description = "Richiesta errata",
                content = @Content(mediaType = "application/json",
                                   schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "404",
                 description = "Nessun Tutorial trovato",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "500",
                 description = "Errore interno del Server",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    public ResponseEntity<TutorialResponseDTO> updateTutorial(@PathVariable("id") long id, @RequestBody UpdateTutorialRequestDTO request)
    {
        TutorialResponseDTO tutorial = tutorialService.updateTutorial(id, request);
        return ResponseEntity.ok(tutorial);
    }


    @DeleteMapping("/tutorials/{id}")
    @Operation(summary = "Elimina un Tutorial")
    @ApiResponse(responseCode = "200",
                 description = "Successo",
                 content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = HttpStatus.class)))
    @ApiResponse(responseCode = "500",
                 description = "Errore interno del Server",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        tutorialService.deleteTutorial(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tutorials")
    @Operation(summary = "Elimina tutti i Tutorial")
    @ApiResponse(responseCode = "200",
                 description = "Successo",
                 content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = HttpStatus.class)))
    @ApiResponse(responseCode = "500",
                 description = "Errore interno del Server",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        tutorialService.deleteAllTutorials();
        return ResponseEntity.ok().build();
    }
}
