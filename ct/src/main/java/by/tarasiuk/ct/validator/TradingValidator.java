package by.tarasiuk.ct.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for validating the trading data.
 * Validation is done using regular expressions.
 */
public class TradingValidator {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String REGEX_VALID_OFFER_FREIGHT = "^\\p{Nd}{1,20}(?:\\.\\p{Nd}{0,2})?$";

    private TradingValidator() {
    }

    /**
     * Checking for validity offer freight value.
     *
     * @param freight   Offer freight value.
     * @return          <code>true</code> if the freight value is valid.
     *                  Otherwise return <code>false</code>.
     */
    public static boolean isValidFreight(String freight) {
        boolean result = freight != null && !freight.isEmpty() && isFoundMatcher(freight, REGEX_VALID_OFFER_FREIGHT);
        LOGGER.info(result ? "{} '{}' is valid." : "{} '{}' invalid.", "Freight", freight);
        return result;
    }

    private static boolean isFoundMatcher(String source, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(source);
        return matcher.find();
    }
}
