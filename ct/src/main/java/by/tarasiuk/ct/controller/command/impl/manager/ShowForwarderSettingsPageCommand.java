package by.tarasiuk.ct.controller.command.impl.manager;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.CommandType;
import by.tarasiuk.ct.controller.command.PagePath;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.AccountServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShowForwarderSettingsPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final AccountServiceImpl accountService = ServiceProvider.getAccountService();

    @Override
    public String execute(RequestContent requestContent) {
        String page = PagePath.FORWARDER_SETTINGS;

        LOGGER.info("Command '{}' return path '{}'", CommandType.SHOW_FORWARDER_SETTINGS_PAGE, PagePath.FORWARDER_SETTINGS);
        return page;
    }
}
