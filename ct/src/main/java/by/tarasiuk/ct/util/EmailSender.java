package by.tarasiuk.ct.util;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public class EmailSender {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PROPERTIES_EMAIL = "email.properties";
    private static final String USER_NAME = "mail.user.name";
    private static final String USER_PASSWORD = "mail.user.password";
    private static final String PERSONAL = "mail.user.personal";

    private EmailSender() {
    }

    public static void send(String emailTo, String emailSubject, String emailText) {
        try {
            Properties properties = PropertiesLoader.getProperties(PROPERTIES_EMAIL);
            properties.forEach((key, value) -> System.out.println("key: " + key + "; value: " + value));

            String user = properties.getProperty(USER_NAME);
            String password = properties.getProperty(USER_PASSWORD);
            String personalName = properties.getProperty(PERSONAL);

            // Authentication
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, password);
                }
            };

            final Session session = Session.getDefaultInstance(properties, authenticator);
            session.setDebug(true);

            InternetAddress addressFrom = new InternetAddress(user, personalName);
            InternetAddress addressTo = new InternetAddress(emailTo);

            final Message message = new MimeMessage(session);
            message.setFrom(addressFrom);
            message.setRecipient(Message.RecipientType.TO, addressTo);
            message.setSubject(emailSubject);
            message.setText(emailText);

            Transport.send(message);
            LOGGER.info("Message to '{}' with subject '{}' was successfully send!", emailTo, emailSubject);
        } catch (IOException e) {
            LOGGER.fatal("Properties cannot be loaded", e);
            throw new RuntimeException("Properties cannot be loaded", e);
        } catch (AddressException e) {
            LOGGER.fatal("Can't created Internet address for address '{}'.", emailTo, e);
            throw new RuntimeException("Can't created Internet address for address '" + emailTo + "'.", e);
        } catch (MessagingException e) {
            LOGGER.fatal("Can't created message.", e);
            throw new RuntimeException("Can't created message.", e);
        }
    }
}
