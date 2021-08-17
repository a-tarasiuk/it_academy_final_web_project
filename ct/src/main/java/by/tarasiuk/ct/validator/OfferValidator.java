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
public class OfferValidator {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String REGEX_VALID_OFFER_PRODUCT_NAME = "^\\p{Lu}[\\p{L}\\p{P}\\p{Zs}\\p{Mc}]{3,197}$";
    private static final String REGEX_VALID_OFFER_PRODUCT_WEIGHT = "^\\p{Nd}{1,10}(?:\\.\\p{Nd}{0,2})?$";
    private static final String REGEX_VALID_OFFER_PRODUCT_VOLUME = "^\\p{Nd}{1,10}(?:\\.\\p{Nd}{0,2})?$";
    private static final String REGEX_VALID_OFFER_ADDRESS = "^\\p{Lu}[\\p{L}\\p{P}\\p{N}\\p{Zs}]{3,197}$";
    private static final String REGEX_VALID_OFFER_FREIGHT = "^\\p{Nd}{1,20}(?:\\.\\p{Nd}{0,2})?$";

    private OfferValidator() {
    }

    /**
     * Checking for validity offer data.
     *
     * @param offerData         Offer data.
     * @return                  <code>true</code> if the offer data is valid.
     *                          Otherwise return <code>false</code>.
     */
    public static boolean isValidOfferData(Map<String, String> offerData) {
        boolean result;

        if(offerData == null || offerData.isEmpty()) {
            LOGGER.info("Incorrect offer data!");
            result = false;
        } else {
            String productName = offerData.get(AttributeName.OFFER_PRODUCT_NAME);
            String productWeight = offerData.get(AttributeName.OFFER_PRODUCT_WEIGHT);
            String productVolume = offerData.get(AttributeName.OFFER_PRODUCT_VOLUME);
            String addressFrom = offerData.get(AttributeName.OFFER_ADDRESS_FROM);
            String addressTo = offerData.get(AttributeName.OFFER_ADDRESS_TO);
            String freight = offerData.get(AttributeName.OFFER_FREIGHT);

            result = isValidProductName(productName) && isValidProductWeight(productWeight) && isValidProductVolume(productVolume)
                    && isValidAddress(addressFrom) && isValidAddress(addressTo) && isValidFreight(freight);
        }

        return result;
    }

    private static boolean isValidProductName(String productName) {
        boolean result = productName != null && !productName.isEmpty() && isFoundMatcher(productName, REGEX_VALID_OFFER_PRODUCT_NAME);
        LOGGER.info(result ? "{} '{}' is valid." : "{} '{}' invalid.", "Product name", productName);
        return result;
    }

    private static boolean isValidProductWeight(String productWeight) {
        boolean result = productWeight != null && !productWeight.isEmpty() && isFoundMatcher(productWeight, REGEX_VALID_OFFER_PRODUCT_WEIGHT);
        LOGGER.info(result ? "{} '{}' is valid." : "{} '{}' invalid.", "Product weight", productWeight);
        return result;
    }

    private static boolean isValidProductVolume(String productVolume) {
        boolean result = productVolume != null && !productVolume.isEmpty() && isFoundMatcher(productVolume, REGEX_VALID_OFFER_PRODUCT_VOLUME);
        LOGGER.info(result ? "{} '{}' is valid." : "{} '{}' invalid.", "Product volume", productVolume);
        return result;
    }

    private static boolean isValidAddress(String address) {
        boolean result = address != null && !address.isEmpty() && isFoundMatcher(address, REGEX_VALID_OFFER_ADDRESS);
        LOGGER.info(result ? "{} '{}' is valid." : "{} '{}' invalid.", "Address", address);
        return result;
    }

    private static boolean isValidFreight(String freight) {
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
