package it.tai.springpostresqljpa.springpostresqljpa.services.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTutorialRequestDTO
{
    private String title;
    private String description;
}
