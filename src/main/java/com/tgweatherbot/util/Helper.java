package com.tgweatherbot.util;

import org.springframework.stereotype.Component;

@Component
public class Helper {
    public boolean messageIsCommand(String text, String commandPrefix) {
        return text.startsWith(commandPrefix);
    }

    public String getCommand(String text, int commandPrefixLength) {
        return text.substring(commandPrefixLength);
    }
}
