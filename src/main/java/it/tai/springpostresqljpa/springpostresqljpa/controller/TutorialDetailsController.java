package it.tai.springpostresqljpa.springpostresqljpa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.tai.springpostresqljpa.springpostresqljpa.exceptions.ErrorMessage;
import it.tai.springpostresqljpa.springpostresqljpa.domain.TutorialDetailsEntity;
import it.tai.springpostresqljpa.springpostresqljpa.services.TutorialDetailsService;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tutorialDetailsDTO.TDRequestDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tutorialDetailsDTO.TDResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class TutorialDetailsController
{
    @Autowired
    private TutorialDetailsService detailsService;

    @GetMapping({"/details/{id}", "/tutorials/{id}/details"})
    @Operation(summary = "Restituisce tutti i TutorialDetails")
    @ApiResponse(responseCode = "200",
                 description = "Successo",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TDResponseDTO.class)))
    @ApiResponse(responseCode = "400",
                 description = "Richiesta errata",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "404",
                 description = "Nessun TutorialDetails trovato",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "500",
                 description = "Errore interno del Server",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    public ResponseEntity<TDResponseDTO> getDetailsById(@PathVariable(value = "id") long id)
    {
        TDResponseDTO details = detailsService.getDetailsById(id);
        return ResponseEntity.ok(details);

        /*TutorialDetailsEntity details = detailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found TutotialDetails with id "+id));
        return new ResponseEntity<>(details, HttpStatus.OK);*/
    }

    @PostMapping("/tutorials/{tutorialId}/details")
    @Operation(summary = "Crea un nuovo TutorialDetails")
    @ApiResponse(responseCode = "201",
                 description = "Successo",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TDResponseDTO.class)))
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
    public ResponseEntity<TDResponseDTO> createDetails(@PathVariable(value = "tutorialId") long tutorialId, @RequestBody TDRequestDTO request)
    {
        TDResponseDTO details = detailsService.createDetails(tutorialId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(details);

        /*TutorialEntity t = tutorialRepository.findById(tutorialId).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id "+ tutorialId));
        detailsRequest.setCreatedOn(new Date());
        detailsRequest.setTutorial(t);
        TutorialDetailsEntity details = detailsRepository.save(detailsRequest);
        return new ResponseEntity<>(details, HttpStatus.CREATED);*/
    }

    @PutMapping("/details/{id}")
    @Operation(summary = "Aggiorna un TutorialDetails")
    @ApiResponse(responseCode = "200",
                 description = "Successo",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TDResponseDTO.class)))
    @ApiResponse(responseCode = "400",
                 description = "Richiesta errata",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "404",
                 description = "Nessun TutorialDetails trovato",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "500",
                 description = "Errore interno del Server",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    public ResponseEntity<TDResponseDTO> updateDetails(@PathVariable("id") long id, @RequestBody TDRequestDTO request)
    {
        TDResponseDTO details = detailsService.updateDetails(id, request);
        return ResponseEntity.ok(details);

        /*TutorialDetailsEntity details = detailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id "+id+" not found"));
        details.setCreatedBy(detailsRequest.getCreatedBy());
        return new ResponseEntity<>(detailsRepository.save(details), HttpStatus.OK);*/
    }

    @DeleteMapping("/details/{id}")
    @Operation(summary = "Elimina un TutorialDetails")
    @ApiResponse(responseCode = "200",
                 description = "Successo",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HttpStatus.class)))
    @ApiResponse(responseCode = "500",
                 description = "Errore interno del Server",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    public ResponseEntity<HttpStatus> deleteDetails(@PathVariable("id") long id)
    {
        detailsService.deleteDetails(id);
        return ResponseEntity.ok().build();

        /*detailsRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);*/
    }

    @DeleteMapping("/tutorials/{tutorialId}/details")
    @Operation(summary = "Elimina un TutorialDetails da un Tutorial")
    @ApiResponse(responseCode = "200",
                 description = "Successo",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HttpStatus.class)))
    @ApiResponse(responseCode = "404",
                 description = "Nessun Tutorial trovato",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "500",
                 description = "Errore interno del Server",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    public ResponseEntity<TutorialDetailsEntity> deleteDetailsOfTutorial(@PathVariable(value = "tutorialId") long tutorialId)
    {
        detailsService.deleteTutorialDetails(tutorialId);
        return ResponseEntity.ok().build();

        /*if(!tutorialRepository.existsById(tutorialId))
            throw new ResourceNotFoundException("Not found Tutorial with id "+tutorialId);
        detailsRepository.deleteByTutorialId(tutorialId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);*/
    }
}
