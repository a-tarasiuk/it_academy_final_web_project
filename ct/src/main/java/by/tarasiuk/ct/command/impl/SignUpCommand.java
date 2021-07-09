package by.tarasiuk.ct.command.impl;

import by.tarasiuk.ct.command.Command;
import by.tarasiuk.ct.util.AccountValidator;
import jakarta.servlet.http.HttpServletRequest;

public class SignUpCommand implements Command {
    private final AccountValidator accountValidator = AccountValidator.getInstance();
    @Override
    public String execute(HttpServletRequest request) {
        /*
        if(!accountValidator.isValidSingUpData(accountData, passwordWithoutEncrypt, confirmPassword)) {
            return null;
        }

         */
        return null;
    }
}
