package by.tarasiuk.ct.command.provider;

import by.tarasiuk.ct.command.Command;
import by.tarasiuk.ct.command.CommandType;
import by.tarasiuk.ct.manager.AttributeName;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Optional;

public class CommandProvider {
    private static final Logger LOGGER = LogManager.getLogger();

    private CommandProvider() {
    }

    public static Optional<Command> defineCommand(HttpServletRequest request) {
        String commandValue = request.getParameter(AttributeName.COMMAND);

        if(commandValue == null || commandValue.isEmpty()) {
            LOGGER.info("Incorrect command '{}'.", commandValue);
            return Optional.empty();
        }

        Optional<Command> command = Arrays.stream(CommandType.values())
                .filter(type -> type.name().equalsIgnoreCase(commandValue))
                .map(CommandType::getCommand)
                .findFirst();

        LOGGER.info("Found command: {}.", commandValue);
        return command;
    }
}