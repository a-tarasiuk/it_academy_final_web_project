package by.tarasiuk.ct.util;

import by.tarasiuk.ct.manager.AttributeName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountValidator {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final AccountValidator instance = new AccountValidator();
    private static final String REGEX_VALID_LOGIN = "";
    private static final String REGEX_VALID_PASSWORD = "";
    private static final String REGEX_VALID_EMAIL = "";
    private static final String REGEX_VALID_REGISTRATION_DATE = "";
    private static final String REGEX_VALID_PHONE_NUMBER = "";
    private static final String REGEX_VALID_ADDRESS = "";
    private static final String REGEX_VALID_ACCOUNT_ROLE_NAME = "";
    private static final String REGEX_VALID_ACCOUNT_STATUS_NAME = "";
    private static final int COUNT_SING_UP_DATA = 9;

    private AccountValidator() {
    }

    public static AccountValidator getInstance() {
        return instance;
    }

    public boolean isValidSingInData(String login, String password) {
        if(login == null || password == null || login.isEmpty() || password.isEmpty() || !login.equals(password)) {
            return false;
        }

        return isValidLogin(login) && isValidPassword(password);
    }

    public boolean isValidSingUpData(Map<String, String> accountData, String password, String confirmPassword) {
        if(accountData.isEmpty() || accountData.size() != COUNT_SING_UP_DATA) {
            return false;
        }

        String login = accountData.get(AttributeName.USER_LOGIN);
        String email = accountData.get(AttributeName.USER_EMAIL);
        String registrationDate = accountData.get(AttributeName.USER_REGISTRATION_DATE);
        String phoneNumber = accountData.get(AttributeName.USER_PHONE_NUMBER);
        String address = accountData.get(AttributeName.USER_ADDRESS);
        String roleName = accountData.get(AttributeName.USER_ROLE_ID);
        String statusName = accountData.get(AttributeName.USER_STATUS_ID);

        return isValidLogin(login) && isValidPassword(password) && password.equals(confirmPassword)
                && isValidEmail(email) && isValidRegistrationDate(registrationDate)
                && isValidPhoneNumber(phoneNumber) && isValidAddress(address)
                && isValidRoleName(roleName) && isValidStatusName(statusName);
    }

    private static boolean isValidLogin(String login) {
        return login != null && !login.isEmpty() && isFoundMatcher(login, REGEX_VALID_LOGIN);
    }

    private static boolean isValidPassword(String password) {
        return password != null && !password.isEmpty() && isFoundMatcher(password, REGEX_VALID_PASSWORD);
    }

    private static boolean isValidEmail(String email) {
        return email != null && !email.isEmpty() && isFoundMatcher(email, REGEX_VALID_EMAIL);
    }

    private static boolean isValidRegistrationDate(String registrationDate) {
        return registrationDate != null && !registrationDate.isEmpty() && isFoundMatcher(registrationDate, REGEX_VALID_REGISTRATION_DATE);
    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && !phoneNumber.isEmpty() && isFoundMatcher(phoneNumber, REGEX_VALID_PHONE_NUMBER);
    }

    private static boolean isValidAddress(String address) {
        return address != null && !address.isEmpty() && isFoundMatcher(address, REGEX_VALID_ADDRESS);
    }

    private static boolean isValidRoleName(String accountRoleName) {
        return accountRoleName != null && !accountRoleName.isEmpty() && isFoundMatcher(accountRoleName, REGEX_VALID_ACCOUNT_ROLE_NAME);
    }

    private static boolean isValidStatusName(String accountStatusName) {
        return accountStatusName != null && !accountStatusName.isEmpty() && isFoundMatcher(accountStatusName, REGEX_VALID_ACCOUNT_STATUS_NAME);
    }

    private static boolean isFoundMatcher(String source, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        return matcher.find();
    }
}
