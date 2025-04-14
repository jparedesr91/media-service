package com.newsnow.media.outside.driving.api.util;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

import java.io.IOException;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * Ftility class for file operations.
 *
 */
public final class FileUtils {

    static public Stream<String> findFilePathsAsStream(String pattern, String filename, String extension, int levelsToSkip, String delimiter) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(FileUtils.class.getClassLoader());
        try {
            Resource[] resources = resolver.getResources("classpath*:i18n/*.properties");
            return stream(resources)
                    .filter(resource -> null != resource.getFilename() && resource.getFilename().startsWith(filename) && resource.getFilename().endsWith(extension))
                    .map(resource -> map(resource, filename, extension, levelsToSkip, delimiter))
                    .distinct();
        } catch (Exception ex) {
            throw new RuntimeException("Error when trying to find file resources!", ex);
        }
    }

    //<editor-fold desc="Support methods">
    private static String map(Resource resource, String filename, String extension, int levelsToSkip, String delimiter) {
        try {
            String[] path = resource.getURL().getPath().split("/");
            return stream(path)
                    .skip(path.length - levelsToSkip)
                    .map(line -> line.endsWith(extension) ? line.substring(0, filename.length()) : line)
                    .collect(joining(delimiter));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    //</editor-fold>

    private FileUtils() {
        throw new AssertionError("No 'FileUtils' instances for you!");
    }
}
