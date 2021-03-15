package com.tgweatherbot.bot.handlers;

import com.tgweatherbot.bot.handlers.interafaces.ICommandHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class UnknownCommandHandler implements ICommandHandler {
    @Override
    public SendMessage handle(Message message) {
        var sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Sorry, but I could not find the command");

        return sendMessage;
    }
}
