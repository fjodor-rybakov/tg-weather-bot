package com.tgweatherbot.bot.handlers;

import com.tgweatherbot.bot.WeatherBot;
import com.tgweatherbot.bot.handlers.interafaces.ICommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class SubToWeatherCommandHandler implements ICommandHandler {
    private final Logger logger = LoggerFactory.getLogger(WeatherBot.class);

    @Override
    public SendMessage handle(Message message) {
        var sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        if (message.hasLocation()) {
            sendMessage.setText(message.getLocation().toString());
        } else {
            sendMessage.setText("Ups! Location not defined");
        }

        return sendMessage;
    }
}
