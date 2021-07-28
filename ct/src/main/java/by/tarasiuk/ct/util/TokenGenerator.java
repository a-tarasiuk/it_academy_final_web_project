package by.tarasiuk.ct.util;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

import java.util.UUID;

public class TokenGenerator {
    private static final TimeBasedGenerator generator = Generators.timeBasedGenerator(EthernetAddress.fromInterface());

    public static String generate() {
        UUID uuid = generator.generate();
        return uuid.toString().replaceAll("-", "").toUpperCase();
    }
}
