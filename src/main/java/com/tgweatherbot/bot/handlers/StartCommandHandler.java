package com.tgweatherbot.bot.handlers;

import com.tgweatherbot.bot.handlers.interafaces.ICommandHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class StartCommandHandler implements ICommandHandler {
    @Override
    public SendMessage handle(Message message) {
        var sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Hello! Welcome to my weather bot");

        return sendMessage;
    }
}
