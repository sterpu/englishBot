package com.katetask.englishBot;

import com.katetask.englishBot.config.BotConfig;
import com.katetask.englishBot.dto.TranslationDTO;
import com.katetask.englishBot.service.TranslationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final TranslationService translationService;

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start" -> startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                case "/random_ru" -> sendRandomRuWord(chatId);
                case "/random_desc" -> sendRandomDescription(chatId);//TODO возвращать случайное описание
                default -> processText(messageText, chatId);
            }
        }

    }

    private void sendRandomDescription(long chatId) {
        String randomDescription = translationService.getRandomDescription();
        sendMessage(chatId, randomDescription);
    }

    private void sendRandomRuWord(long chatId) {
        String ruWord = translationService.getRandomRuWord();
        sendMessage(chatId, ruWord);
    }

    private void processText(String messageText, Long chatId) {
        String[] strings = messageText.split("\\n");
        if (strings.length != 3) {
            sendMessage(chatId, "Message have to consist of 3 lines");
            return;
        }
        translationService.create(TranslationDTO.builder()
                .english(strings[0])
                .definition(strings[1])
                .russian(strings[2])
                .build());
        sendMessage(chatId, "Entity created");
    }

    private void startCommandReceived(Long chatId, String name) {
        sendMessage(chatId, "Hi, " + name);
    }

    private void sendMessage(Long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        try {
            execute(sendMessage);
        } catch (TelegramApiException ignored) {

        }
    }
}
