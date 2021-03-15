package com.tgweatherbot.bot.handlers.interafaces;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface ICommandHandler {
    SendMessage handle(Message message);
}
