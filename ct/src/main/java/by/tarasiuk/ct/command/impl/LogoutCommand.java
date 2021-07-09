package by.tarasiuk.ct.command.impl;

import by.tarasiuk.ct.command.Command;
import jakarta.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        // уничтожение сессии
        request.getSession().invalidate();

        return page;
    }
}
