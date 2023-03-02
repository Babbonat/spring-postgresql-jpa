package it.tai.springpostresqljpa.springpostresqljpa.mapper;

import it.tai.springpostresqljpa.springpostresqljpa.domain.CommentEntity;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.commentsDTO.CommentRequestDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.commentsDTO.CommentResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper
{
    public CommentResponseDTO toResponse(CommentEntity entity);
    public CommentEntity toEntity(CommentRequestDTO request);
}
