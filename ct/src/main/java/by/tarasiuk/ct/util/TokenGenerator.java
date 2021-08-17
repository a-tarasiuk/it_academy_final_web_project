package by.tarasiuk.ct.util;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import java.util.UUID;

/**
 * Token generator {@link fasterxml.com}
 */
public class TokenGenerator {
    private static final TimeBasedGenerator generator = Generators.timeBasedGenerator(EthernetAddress.fromInterface());

    /**
     * Token number generator.
     *
     * @return      Token number string.
     */
    public static String generate() {
        UUID uuid = generator.generate();
        return uuid.toString().replaceAll("-", "").toUpperCase();
    }
}
