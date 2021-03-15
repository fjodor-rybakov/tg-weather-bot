package com.tgweatherbot.bot;

import com.tgweatherbot.bot.handlers.interafaces.ICommandHandler;
import com.tgweatherbot.util.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class WeatherBot extends TelegramLongPollingBot {
    @Value("${telegram.token}")
    private String botToken;
    @Value("${telegram.bot-name}")
    private String botName;
    @Value("${telegram.command-prefix}")
    private String commandPrefix;
    private final Logger logger = LoggerFactory.getLogger(WeatherBot.class);
    @Autowired
    private Helper helper;
    @Autowired
    private ICommandHandler startCommandHandler;
    @Autowired
    private ICommandHandler subToWeatherCommandHandler;
    @Autowired
    private ICommandHandler unknownCommandHandler;

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage()) {
                var message = update.getMessage();
                if (message.hasText() || message.hasLocation()) {
                    handleIncomingMessage(message);
                }
            }
        } catch (Exception e) {
            logger.error("Shit happen", e);
        }
    }

    private void handleIncomingMessage(Message message) {
        var messageText = message.getText();
        if (helper.messageIsCommand(messageText, commandPrefix)) {
            SendMessage sendMessage;
            sendMessage = switch (helper.getCommand(messageText, commandPrefix.length())) {
                case "start" -> startCommandHandler.handle(message);
                case "subToWeather" -> subToWeatherCommandHandler.handle(message);
                default -> unknownCommandHandler.handle(message);
            };
            if (sendMessage != null) {
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
