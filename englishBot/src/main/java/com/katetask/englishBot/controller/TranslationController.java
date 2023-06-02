package com.katetask.englishBot.controller;

import com.katetask.englishBot.dto.TranslationDTO;
import com.katetask.englishBot.service.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TranslationController {
    private final TranslationService translationService;

    @PostMapping("/create")
    public void create(TranslationDTO translationDTO){
        translationService.create(translationDTO);
    }

    @GetMapping("/hi")
    public String get(){
        return "Hello";
    }
}
