package org.netflix.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OidGenerator {

    public static long getNew() {
        final String LAST_OID_FILENAME = "/home/tina/IdeaProjects/netflix-model/src/main/resources/last-oid.txt";
        long newId = -1;

        try {
            Path path = Paths.get(LAST_OID_FILENAME);
            String content = new String(Files.readAllBytes(path));
            newId = Long.parseLong(content);
            newId += 1;
            String newIdAsString = String.valueOf(newId);
            Files.write(path, newIdAsString.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return newId;
    }
}
