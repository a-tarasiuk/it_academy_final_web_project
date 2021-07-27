package by.tarasiuk.ct.command.impl;

import by.tarasiuk.ct.command.Command;
import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.manager.PagePath;

public class LogoutCommand implements Command {
    @Override
    public String execute(RequestContent requestContent) {
        // уничтожение сессии
        requestContent.invalidateSession();
        return PagePath.MAIN;
    }
}
