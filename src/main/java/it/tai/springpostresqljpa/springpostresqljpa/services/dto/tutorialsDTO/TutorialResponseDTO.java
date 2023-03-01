package it.tai.springpostresqljpa.springpostresqljpa.services.dto.tutorialsDTO;

import it.tai.springpostresqljpa.springpostresqljpa.domain.TagEntity;
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
