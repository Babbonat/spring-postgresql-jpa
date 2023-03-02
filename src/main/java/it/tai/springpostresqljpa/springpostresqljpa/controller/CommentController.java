package it.tai.springpostresqljpa.springpostresqljpa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.tai.springpostresqljpa.springpostresqljpa.exceptions.ErrorMessage;
import it.tai.springpostresqljpa.springpostresqljpa.services.CommentService;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.commentsDTO.CommentRequestDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.commentsDTO.CommentResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class CommentController
{
    @Autowired
    private CommentService commentService;

    @GetMapping("/tutorials/{tutorialId}/comments")
    @Operation(summary = "Restituisce tutti i Commenti di un certo Tutorial")
    @ApiResponse(responseCode = "200",
                 description = "Successo",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommentResponseDTO.class,
                                    type = "array")))
    @ApiResponse(responseCode = "400",
                 description = "Richiesta errata",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "404",
                 description = "Nessun Tutorial trovato | Nessun Commento trovato",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "500",
                 description = "Errore interno del Server",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    public ResponseEntity<List<CommentResponseDTO>> getAllCommentsByTutorialId(@PathVariable(value = "tutorialId") long tutorialId)
    {
        List<CommentResponseDTO> comments = commentService.listCommentsByTutorial(tutorialId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comments/{commentId}")
    @Operation(summary = "Restituisce un commento")
    @ApiResponse(responseCode = "200",
                 description = "Successo",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommentResponseDTO.class)))
    @ApiResponse(responseCode = "400",
                 description = "Richiesta errata",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "404",
                 description = "Nessun Commento trovato",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "500",
                 description = "Errore interno del Server",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    public ResponseEntity<CommentResponseDTO> getCommentsById(@PathVariable(value = "commentId") Long commentId) {
        CommentResponseDTO comment = commentService.getCommentById(commentId);
        return ResponseEntity.ok(comment);
    }

    @PostMapping("/tutorials/{tutorialId}/comments")
    @Operation(summary = "Crea un nuovo Commento per un Tutorial")
    @ApiResponse(responseCode = "201",
                 description = "Successo",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommentResponseDTO.class)))
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
    public ResponseEntity<CommentResponseDTO> createComment(@PathVariable(value = "tutorialId") long tutorialId, @RequestBody CommentRequestDTO request)
    {
        CommentResponseDTO comment = commentService.commentTutorial(tutorialId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @PutMapping("/comments/{commentId}")
    @Operation(summary = "Modifica un Commento di un Tutorial")
    @ApiResponse(responseCode = "200",
                 description = "Successo",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommentResponseDTO.class)))
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
    public ResponseEntity<CommentResponseDTO> updateComment(@PathVariable("commentId") long commentId, @RequestBody CommentRequestDTO request)
    {
        CommentResponseDTO comment = commentService.editComment(commentId, request);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/comments/{commentId}")
    @Operation(summary = "Elimina un Commento")
    @ApiResponse(responseCode = "200",
                 description = "Successo",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HttpStatus.class)))
    @ApiResponse(responseCode = "500",
                 description = "Errore interno del Server",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("commentId") long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tutorials/{tutorialId}/comments")
    @Operation(summary = "Elimina tutti i commenti di un Tutorial")
    @ApiResponse(responseCode = "200",
                 description = "Successo",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HttpStatus.class)))
    @ApiResponse(responseCode = "500",
                 description = "Errore interno del Server",
                 content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
    public ResponseEntity<HttpStatus> deleteAllCommentsOfTutorial(@PathVariable(value = "tutorialId") Long tutorialId) {
        commentService.deleteAllComments(tutorialId);
        return ResponseEntity.ok().build();
    }
}
