package com.katetask.englishBot.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TranslationDTO {
    private String english;

    private String russian;

    private String definition;
}
