package com.tgweatherbot.bot;

import com.tgweatherbot.bot.handlers.interafaces.ICommandHandler;
import com.tgweatherbot.util.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Component
public class WeatherBot extends TelegramLongPollingBot {
    @Value("${telegram.token}")
    private String botToken;
    @Value("${telegram.bot-name}")
    private String botName;
    @Value("${telegram.command-prefix}")
    private String commandPrefix;
    @Value("${telegram.start-command}")
    private String startCommand;
    @Value("${telegram.sub-to-weather-command}")
    private String subToWeatherCommand;
    @Value("${telegram.keyboard-command:}")
    private String keyboardCommand;

    private final Logger logger = LoggerFactory.getLogger(WeatherBot.class);
    @Autowired
    private Helper helper;
    @Autowired
    private ICommandHandler startCommandHandler;
    @Autowired
    private ICommandHandler subToWeatherCommandHandler;
    @Autowired
    private ICommandHandler unknownCommandHandler;
    @Autowired
    private ICommandHandler markupCommandHandler;

    private HashMap<String, ICommandHandler> commandHandlerList;

    @PostConstruct
    private void init() {
        initCommand();
    }

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
            var currentCommand = helper.getCommand(messageText, commandPrefix.length());
            var commandHandler = commandHandlerList.get(currentCommand);
            var sendMessage = commandHandler == null ?
                    unknownCommandHandler.handle(message) : commandHandler.handle(message);

            if (sendMessage != null) {
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initCommand() {
        var hashMap = new HashMap<String, ICommandHandler>();
        hashMap.put(startCommand, startCommandHandler);
        hashMap.put(subToWeatherCommand, subToWeatherCommandHandler);
        hashMap.put(keyboardCommand, markupCommandHandler);
        commandHandlerList = hashMap;
    }
}
