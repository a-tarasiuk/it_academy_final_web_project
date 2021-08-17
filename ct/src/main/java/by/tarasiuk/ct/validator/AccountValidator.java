package by.tarasiuk.ct.validator;

import by.tarasiuk.ct.controller.command.AttributeName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for validating the offer data.
 * Validation is done using regular expressions.
 */
public class AccountValidator {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String REGEX_VALID_ACCOUNT_FIRST_NAME = "\\p{Lu}\\p{Ll}{2,50}$";
    private static final String REGEX_VALID_ACCOUNT_LAST_NAME = "^\\p{Lu}\\p{Ll}{2,50}$";
    private static final String REGEX_VALID_ACCOUNT_LOGIN = "^[\\p{L}\\p{Nd}]{3,50}$";
    private static final String REGEX_VALID_ACCOUNT_EMAIL = "^(([^<>()\\[\\]\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
    private static final String REGEX_VALID_COMPANY_NAME = "^[\\p{L}\\p{Nd}\\p{P}\\p{Zs}]{3,150}$";
    private static final String REGEX_VALID_COMPANY_ADDRESS = "^[\\p{L}\\p{Nd}\\p{P}\\p{Zs}]{3,200}$";
    private static final String REGEX_VALID_COMPANY_PHONE_NUMBER = "^\\p{Nd}{11,16}$";
    private static final String REGEX_VALID_ACCOUNT_PASSWORD = "^.{8,30}$";

    private AccountValidator() {
    }

    /**
     * Checking for validity account data.
     *
     * @param accountData       Account data.
     * @return                  <code>true</code> if the account data is valid.
     *                          Otherwise return <code>false</code>.
     */
    public static boolean isValidAccountData(Map<String, String> accountData) {
        boolean result;

        if(accountData == null || accountData.isEmpty()) {
            LOGGER.info("Incorrect account data!");
            result = false;
        } else {
            String firstName = accountData.get(AttributeName.ACCOUNT_FIRST_NAME);
            String lastName = accountData.get(AttributeName.ACCOUNT_LAST_NAME);
            String login = accountData.get(AttributeName.ACCOUNT_LOGIN);
            String email = accountData.get(AttributeName.ACCOUNT_EMAIL);
            String password = accountData.get(AttributeName.ACCOUNT_PASSWORD);
            String confirmPassword = accountData.get(AttributeName.ACCOUNT_CONFIRM_PASSWORD);

            result = isValidFirstName(firstName) && isValidLastName(lastName)
                    && isValidLogin(login) && isValidEmail(email)
                    && isValidPassword(password) && isValidPassword(confirmPassword) && password.equals(confirmPassword);
        }

        return result;
    }

    /**
     * Checking for validity personal account data.
     *
     * @param accountData       Personal account data.
     * @return                  <code>true</code> if the personal account data is valid.
     *                          Otherwise return <code>false</code>.
     */
    public static boolean isValidPersonalAccountData(Map<String, String> accountData) {
        boolean result;

        if(accountData == null || accountData.isEmpty()) {
            LOGGER.info("Incorrect personal account data!");
            result = false;
        } else {
            String accountFirstName = accountData.get(AttributeName.ACCOUNT_FIRST_NAME);
            String accountLastName = accountData.get(AttributeName.ACCOUNT_LAST_NAME);

            result = isValidFirstName(accountFirstName) && isValidLastName(accountLastName);
        }

        return result;
    }


    /**
     * Checking for validity sign in data.
     *
     * @param login             Account login.
     * @param password          Account password.
     * @return                  <code>true</code> if the login and password data is valid.
     *                          Otherwise return <code>false</code>.
     */
    public static boolean isValidSignInData(String login, String password) {
        if(login == null || password == null || login.isEmpty() || password.isEmpty()) {
            return false;
        }

        return isValidLogin(login) && isValidPassword(password);
    }

    /**
     * Checking for validity sign up data.
     *
     * @param signUp            Sign up data.
     * @return                  <code>true</code> if the sign up data is valid.
     *                          Otherwise return <code>false</code>.
     */
    public static boolean isValidSignUpData(Map<String, String> signUp) {
        boolean result;

        if(signUp == null || signUp.isEmpty()) {
            LOGGER.info("Incorrect sign up data!");
            result = false;
        } else {
            String firstName = signUp.get(AttributeName.ACCOUNT_FIRST_NAME);
            String lastName = signUp.get(AttributeName.ACCOUNT_LAST_NAME);
            String login = signUp.get(AttributeName.ACCOUNT_LOGIN);
            String email = signUp.get(AttributeName.ACCOUNT_EMAIL);
            String companyName = signUp.get(AttributeName.COMPANY_NAME);
            String companyAddress = signUp.get(AttributeName.COMPANY_ADDRESS);
            String companyPhoneNumber = signUp.get(AttributeName.COMPANY_PHONE_NUMBER);
            String password = signUp.get(AttributeName.ACCOUNT_PASSWORD);
            String confirmPassword = signUp.get(AttributeName.ACCOUNT_CONFIRM_PASSWORD);

            result = isValidFirstName(firstName) && isValidLastName(lastName)
                    && isValidLogin(login) && isValidEmail(email)
                    && isValidCompanyName(companyName) && isValidCompanyAddress(companyAddress)
                    && isValidCompanyPhoneNumber(companyPhoneNumber)
                    && isValidPassword(password) && isValidPassword(confirmPassword) && password.equals(confirmPassword);
        }

        return result;
    }

    private static boolean isValidFirstName(String firstName) {
        boolean result = firstName != null && !firstName.isEmpty() && isFoundMatcher(firstName, REGEX_VALID_ACCOUNT_FIRST_NAME);
        LOGGER.info(result ? "{} '{}' is valid." : "{} '{}' invalid.", "First name", firstName);
        return result;
    }

    private static boolean isValidLastName(String lastName) {
        boolean result = lastName != null && !lastName.isEmpty() && isFoundMatcher(lastName, REGEX_VALID_ACCOUNT_LAST_NAME);
        LOGGER.info(result ? "{} '{}' is valid." : "{} '{}' invalid.", "Last name", lastName);
        return result;
    }

    private static boolean isValidLogin(String login) {
        boolean result = login != null && !login.isEmpty() && isFoundMatcher(login, REGEX_VALID_ACCOUNT_LOGIN);
        LOGGER.info(result ? "{} '{}' is valid." : "{} '{}' invalid.", "Login", login);
        return result;
    }

    private static boolean isValidEmail(String email) {
        boolean result = email != null && !email.isEmpty() && isFoundMatcher(email, REGEX_VALID_ACCOUNT_EMAIL);
        LOGGER.info(result ? "{} '{}' is valid." : "{} '{}' invalid.", "Email", email);
        return result;
    }

    private static boolean isValidCompanyName(String companyName) {
        boolean result = companyName != null && !companyName.isEmpty() && isFoundMatcher(companyName, REGEX_VALID_COMPANY_NAME);
        LOGGER.info(result ? "{} '{}' is valid." : "{} '{}' invalid.", "Company name", companyName);
        return result;
    }

    private static boolean isValidCompanyAddress(String companyAddress) {
        boolean result = companyAddress != null && !companyAddress.isEmpty() && isFoundMatcher(companyAddress, REGEX_VALID_COMPANY_ADDRESS);
        LOGGER.info(result ? "{} '{}' is valid." : "{} '{}' invalid.", "Company address", companyAddress);
        return result;
    }

    private static boolean isValidCompanyPhoneNumber(String companyPhoneNumber) {
        boolean result = companyPhoneNumber != null && !companyPhoneNumber.isEmpty() && isFoundMatcher(companyPhoneNumber, REGEX_VALID_COMPANY_PHONE_NUMBER);
        LOGGER.info(result ? "{} '{}' is valid." : "{} '{}' invalid.", "Company phone number", companyPhoneNumber);
        return result;
    }

    public static boolean isValidPassword(String password) {
        boolean result = password != null && !password.isEmpty() && isFoundMatcher(password, REGEX_VALID_ACCOUNT_PASSWORD);
        LOGGER.info(result ? "{} '{}' is valid." : "{} '{}' invalid.", "Password", password);
        return result;
    }

    private static boolean isFoundMatcher(String source, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(source);
        return matcher.find();
    }
}
