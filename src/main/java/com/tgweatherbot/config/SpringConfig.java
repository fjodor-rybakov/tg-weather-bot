package com.tgweatherbot.config;

import com.tgweatherbot.bot.WeatherBot;
import com.tgweatherbot.bot.handlers.StartCommandHandler;
import com.tgweatherbot.bot.handlers.SubToWeatherCommandHandler;
import com.tgweatherbot.bot.handlers.UnknownCommandHandler;
import com.tgweatherbot.bot.handlers.interafaces.ICommandHandler;
import com.tgweatherbot.util.Helper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;

@Configuration
public class SpringConfig {
    @Bean
    public WeatherBot getWeatherBot() {
        return new WeatherBot();
    }

    @Bean
    public Helper getHelper() {
        return new Helper();
    }

    @Bean
    public ICommandHandler getStartCommandHandler() {
        return new StartCommandHandler();
    }

    @Bean
    public ICommandHandler getUnknownCommandHandler() {
        return new UnknownCommandHandler();
    }

    @Bean
    public ICommandHandler getSubToWeatherCommandHandler() {
        return new SubToWeatherCommandHandler();
    }

    @PostConstruct
    public void getTelegramBotApi() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(getWeatherBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
