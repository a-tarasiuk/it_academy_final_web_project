package by.tarasiuk.ct.validator;

import by.tarasiuk.ct.controller.command.AttributeName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for validating the company data.
 * Validation is done using regular expressions.
 */
public class CompanyValidator {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String REGEX_VALID_COMPANY_NAME = "^[\\p{L}\\p{Nd}\\p{P}\\p{Zs}]{3,150}$";
    private static final String REGEX_VALID_COMPANY_ADDRESS = "^[\\p{L}\\p{Nd}\\p{P}\\p{Zs}]{3,200}$";
    private static final String REGEX_VALID_COMPANY_PHONE_NUMBER = "^\\p{Nd}{11,16}$";

    private CompanyValidator() {
    }

    /**
     * Checking for validity company data.
     *
     * @param companyData       Company data.
     * @return                  <code>true</code> if the company data is valid.
     *                          Otherwise return <code>false</code>.
     */
    public static boolean isValidCompany(Map<String, String> companyData) {
        boolean result;

        if(companyData == null || companyData.isEmpty()) {
            LOGGER.info("Incorrect company!");
            result = false;
        } else {
            String name = companyData.get(AttributeName.COMPANY_NAME);
            String address = companyData.get(AttributeName.COMPANY_ADDRESS);
            String phoneNumber = companyData.get(AttributeName.COMPANY_PHONE_NUMBER);

            result = isValidName(name) && isValidAddress(address) && isValidPhoneNumber(phoneNumber);
        }

        return result;
    }

    /**
     * Checking for validity company address and company phone number.
     *
     * @param address           Company address.
     * @param phoneNumber       Company phone number.
     * @return                  <code>true</code> if the company address and company phone number is valid.
     *                          Otherwise return <code>false</code>.
     */
    public static boolean isValidCompanyData(String address, String phoneNumber) {
        boolean result;

        if(address == null || phoneNumber == null || address.isEmpty() || phoneNumber.isEmpty()) {
            LOGGER.info("Incorrect company data!");
            result = false;
        } else {
            result = isValidAddress(address) && isValidPhoneNumber(phoneNumber);
        }

        return result;
    }

    private static boolean isValidName(String name) {
        boolean result = name != null && !name.isEmpty() && isFoundMatcher(name, REGEX_VALID_COMPANY_NAME);
        LOGGER.info(result ? "{} '{}' is valid." : "{} '{}' invalid.", "Company name", name);
        return result;
    }

    private static boolean isValidAddress(String address) {
        boolean result = address != null && !address.isEmpty() && isFoundMatcher(address, REGEX_VALID_COMPANY_ADDRESS);
        LOGGER.info(result ? "{} '{}' is valid." : "{} '{}' invalid.", "Company address", address);
        return result;
    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        boolean result = phoneNumber != null && !phoneNumber.isEmpty() && isFoundMatcher(phoneNumber, REGEX_VALID_COMPANY_PHONE_NUMBER);
        LOGGER.info(result ? "{} '{}' is valid." : "{} '{}' invalid.", "Company phone number", phoneNumber);
        return result;
    }

    private static boolean isFoundMatcher(String source, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(source);
        return matcher.find();
    }
}
