package com.tgweatherbot.bot.handlers;

import com.tgweatherbot.bot.handlers.interafaces.ICommandHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

@Component
public class MarkupCommandHandler implements ICommandHandler {
    @Value("${telegram.command-prefix}")
    private String commandPrefix;
    @Value("${telegram.start-command}")
    private String startCommand;
    @Value("${telegram.sub-to-weather-command}")
    private String subToWeatherCommand;

    @Override
    public SendMessage handle(Message message) {
        var sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Keyboard");

        var replyKeyboardMarkup = createMarkup();
        var keyboard = setupKeyboard();

        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        return sendMessage;
    }

    private ReplyKeyboardMarkup createMarkup() {
        var replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }

    private KeyboardRow createRow(String text) {
        var row = new KeyboardRow();
        row.add(text);
        return row;
    }

    private ArrayList<KeyboardRow> setupKeyboard() {
        var keyboard = new ArrayList<KeyboardRow>();
        keyboard.add(createRow(buildCommand(startCommand)));
        keyboard.add(createRow(buildCommand(subToWeatherCommand)));

        return keyboard;
    }

    private String buildCommand(String command) {
        return commandPrefix + command;
    }
}
