package by.tarasiuk.ct.util;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class EmailSender {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final MessageManager messageManager = MessageManager.getInstance();
    private static final String PROPERTIES_EMAIL = "email.properties";
    private static final String USER_NAME = "mail.user.name";
    private static final String USER_PASSWORD = "mail.user.password";
    private static final String PERSONAL = "mail.user.personal";
    private static final String SUBJECT_COMPLETION_REGISTRATION = "email.signUp.completionRegistration.subject";
    private static final String TEXT_COMPLETION_REGISTRATION = "email.signUp.completionRegistration.text";
    private static final String LINK_START = "http://localhost:8081/controller?account_email=";
    private static final String LINK_COMMAND = "&command=activate_account";
    private static final String LINK_TOKEN = "&token=";
    private static final String CONTENT = "text/html";
    private static final String user;
    private static final String password;
    private static final String personalName;
    private static final Session session;

    static  {
        try {
            Properties properties = PropertiesLoader.getProperties(PROPERTIES_EMAIL);

            user = properties.getProperty(USER_NAME);
            password = properties.getProperty(USER_PASSWORD);
            personalName = properties.getProperty(PERSONAL);

            // Authentication
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, password);
                }
            };

            session = Session.getDefaultInstance(properties, authenticator);
            session.setDebug(true);
        } catch (IOException e) {
            LOGGER.fatal("Properties to send a message cannot be loaded.", e);
            throw new RuntimeException("Properties to send a message cannot be loaded.", e);
        }
    }

    private EmailSender() {
    }

    public static void sendActivationEmail(String locale, String firstName, String emailTo, String token) {
        String emailSubject = messageManager.findMassage(SUBJECT_COMPLETION_REGISTRATION, locale);
        String formatMessage = messageManager.findMassage(TEXT_COMPLETION_REGISTRATION, locale);
        StringBuilder link = new StringBuilder(LINK_START)
                .append(emailTo)
                .append(LINK_COMMAND)
                .append(LINK_TOKEN)
                .append(token);
        String emailMessage = String.format(formatMessage, firstName, link);

        try {
            InternetAddress addressTo = new InternetAddress(emailTo);
            InternetAddress addressFrom = new InternetAddress(user, personalName);

            final Message message = new MimeMessage(session);
            message.setFrom(addressFrom);
            message.setRecipient(Message.RecipientType.TO, addressTo);
            message.setSubject(emailSubject);
            message.setContent(emailMessage, CONTENT);

            Transport.send(message);
            LOGGER.info("Message to '{}' with subject '{}' was successfully send!", emailTo, emailSubject);
        } catch (AddressException e) {
            LOGGER.fatal("Can't created Internet address for address '{}'.", user, e);
            throw new RuntimeException("Can't created Internet address for address '" + user + "'.", e);
        } catch (MessagingException e) {
            LOGGER.fatal("Can't created message.", e);
            throw new RuntimeException("Can't created message.", e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
