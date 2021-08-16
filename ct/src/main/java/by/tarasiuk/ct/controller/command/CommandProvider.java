package by.tarasiuk.ct.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Optional;

/**
 * Allows you to determine the type of command.
 */
public class CommandProvider {
    private static final Logger LOGGER = LogManager.getLogger();

    private CommandProvider() {
    }

    /**
     * Searches for a command in the command list.
     * @param request - HttpServletRequest
     * @return type command if exist, otherwise - empty command
     */
    public static Optional<Command> defineCommand(HttpServletRequest request) {
        String command = request.getParameter(AttributeName.COMMAND);

        if(command == null || command.isEmpty()) {
            LOGGER.info("Invalid command: {}.", command);
            return Optional.empty();
        }

        Optional<Command> optionalCommand = Arrays.stream(CommandType.values())
                .filter(type -> type.name().equalsIgnoreCase(command))
                .map(CommandType::getCommand)
                .findFirst();

        LOGGER.info(optionalCommand.isPresent() ? "Found command '{}'." : "Command '{}' was not found.", command);
        return optionalCommand;
    }
}