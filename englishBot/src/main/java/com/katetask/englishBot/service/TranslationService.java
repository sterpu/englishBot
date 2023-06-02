package com.katetask.englishBot.service;

import com.katetask.englishBot.dto.TranslationDTO;
import com.katetask.englishBot.entity.Translation;
import com.katetask.englishBot.repository.TranslationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class TranslationService {
    private final TranslationRepository translationRepository;
    private final Random random = new Random();

    public void create(TranslationDTO translationDTO) {
        Translation translation = Translation.builder()
                .definition(translationDTO.getDefinition())
                .english(translationDTO.getEnglish())
                .russian(translationDTO.getRussian())
                .build();
        translationRepository.save(translation);
    }

    public String getRandomRuWord() {
        //Todo создать в репе метод который будет возвращать случайную сущность, используя @query(native query)
        //Для постгресса использовать order by random + limit
        //Translation translation = getRandomTranslation();
        Optional<Translation> translation = translationRepository.findRandom();
        return translation.isPresent() ? translation.get().getRussian() : "There are not translations in DB";
    }

    private Translation getRandomTranslation() {
        Iterable<Translation> translations = translationRepository.findAll();
        List<Translation> result =
                StreamSupport.stream(translations.spliterator(), false).toList();
        Translation translation = result.get(random.nextInt(0, result.size()));
        return translation;
    }

    public String getRandomDescription() {
        return getRandomTranslation().getDefinition();
    }
}
