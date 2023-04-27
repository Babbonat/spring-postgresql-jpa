package it.tai.springpostresqljpa.springpostresqljpa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.tai.springpostresqljpa.springpostresqljpa.exceptions.ErrorMessage;
import it.tai.springpostresqljpa.springpostresqljpa.services.TagService;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tagsDTO.TagRequestDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tagsDTO.TagResponseDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tutorialsDTO.TutorialResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "security_auth")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    @Operation(summary = "Restituisce tutti i Tag")
    @ApiResponse(responseCode = "200",
                 description = "Successo",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TagResponseDTO.class,
                                    type = "array")))
    @ApiResponse(responseCode = "404",
                 description = "Nessun Tag trovato",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "500",
                 description = "Errore interno del Server",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    public ResponseEntity<List<TagResponseDTO>> getAllTags() {
        List<TagResponseDTO> tagEntities = this.tagService.listTags();
        return ResponseEntity.ok(tagEntities);
    }

    @GetMapping("/tags/{id}")
    @Operation(summary = "Restituisce un Tag dato il suo id")
    @ApiResponse(responseCode = "200",
                 description = "Successo",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TagResponseDTO.class,
                                    type = "array")))
    @ApiResponse(responseCode = "404",
                 description = "Nessun Tag trovato",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "400",
                 description = "Richiesta errata",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "500",
                 description = "Errore interno del Server",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    public ResponseEntity<TagResponseDTO> getTagsById(@PathVariable(value = "id") long id) {
        return ResponseEntity.ok(this.tagService.getTag(id));
    }

    @GetMapping("/tutorials/{tutorialId}/tags")
    @Operation(summary = "Restituisce tutti i Tag dato un certo Tutorial")
    @ApiResponse(responseCode = "200",
                 description = "Successo",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TagResponseDTO.class,
                                    type = "array")))
    @ApiResponse(responseCode = "400",
                 description = "Richiesta errata",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "404",
                 description = "Nessun Tag trovato | Nessun Tutorial trovato",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "500",
                 description = "Errore interno del Server",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    public ResponseEntity<List<TagResponseDTO>> getAllTagsByTutorialId(@PathVariable(value = "tutorialId") long tutorialId) {
        List<TagResponseDTO> tagEntities = this.tagService.getTagsByTutorialId(tutorialId);
        return ResponseEntity.ok(tagEntities);
    }

    @GetMapping("/tags/{tagName}/tutorials")
    @Operation(summary = "Restituisce tutti i Tutorial con un certo Tag")
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
                 description = "Nessun Tag trovato | Nessun Tutorial trovato",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "500",
                 description = "Errore interno del Server",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    public ResponseEntity<List<TutorialResponseDTO>> getAllTutorialsByTagName(@PathVariable(value = "tagName") String tagName) {
        List<TutorialResponseDTO> tutorialEntities = this.tagService.getTutorialsByTagName(tagName);
        return ResponseEntity.ok(tutorialEntities);
    }

    @PostMapping("/tutorials/{tutorialId}/tags")
    @Operation(summary = "Aggiunge un nuovo Tag ad un certo Tutorial")
    @ApiResponse(responseCode = "201",
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
    public ResponseEntity<TutorialResponseDTO> addTag(@PathVariable(value = "tutorialId") long tutorialId, @RequestBody TagRequestDTO request)
    {
        TutorialResponseDTO response = tagService.tagTutorial(tutorialId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/tags/{id}")
    @Operation(summary = "Aggiorna un Tutorial")
    @ApiResponse(responseCode = "200",
                 description = "Successo",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TagResponseDTO.class)))
    @ApiResponse(responseCode = "400",
                 description = "Richiesta errata",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "404",
                 description = "Nessun Tag trovato",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "500",
                 description = "Errore interno del Server",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    public ResponseEntity<TagResponseDTO> updateTag(@PathVariable("id") long id, @RequestBody TagRequestDTO request)
    {
        TagResponseDTO response = tagService.updateTagInfo(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/tutorials/{tutorialId}/tags/{tagId}")
    @Operation(summary = "Elimina un Tag da un Tutorial")
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
    public ResponseEntity<TutorialResponseDTO> deleteTagFromTutorial(@PathVariable(value = "tutorialId") long tutorialId, @PathVariable(value = "tagId") Long tagId) {
        TutorialResponseDTO response = tagService.untagTutorial(tutorialId, tagId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/tags/{tagId}")
    @Operation(summary = "Elimina un Tutorial")
    @ApiResponse(responseCode = "200",
                 description = "Successo",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HttpStatus.class)))
    @ApiResponse(responseCode = "400",
                 description = "Richiesta errata",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "500",
                 description = "Errore interno del Server",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    public ResponseEntity<HttpStatus> deleteTag(@PathVariable("tagId") long tagId) {
        this.tagService.deleteTag(tagId);
        return ResponseEntity.ok().build();
    }
}
