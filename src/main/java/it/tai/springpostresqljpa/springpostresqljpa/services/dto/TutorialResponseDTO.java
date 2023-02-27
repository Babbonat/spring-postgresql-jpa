package it.tai.springpostresqljpa.springpostresqljpa.services.dto;

import it.tai.springpostresqljpa.springpostresqljpa.domain.TagEntity;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tagsDTO.TagsResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TutorialResponseDTO
{
    private String title;
    private String description;
    private boolean published;
    private List<TagEntity> tags;
}
