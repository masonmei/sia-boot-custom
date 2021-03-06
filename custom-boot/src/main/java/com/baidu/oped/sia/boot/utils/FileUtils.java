package com.baidu.oped.sia.boot.utils;

import static com.baidu.oped.sia.boot.exception.ExceptionArgsBuilder.get;

import com.baidu.oped.sia.boot.exception.internal.config.ConfigFileNotFoundException;
import com.baidu.oped.sia.boot.exception.internal.config.InvalidConfigFilePathException;

import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * File Utils for resolve file.
 *
 * @author mason
 */
public abstract class FileUtils {

    public static final String FILE_PREFIX = "file:";
    public static final String CLASSPATH_PREFIX = "classpath:";
    private static final String[] SEARCH_PATH = new String[]{FILE_PREFIX, CLASSPATH_PREFIX};

    /**
     * Get file with the given fileName and parent directory.
     *
     * @param parentDirectory parent directory
     * @param fileName        file name
     * @return resolved file
     */
    public static File resolveConfigFile(String parentDirectory, String fileName) {
        String filePath = "";
        if (StringUtils.hasText(parentDirectory)) {
            filePath = filePath.concat(parentDirectory);

            if (!filePath.endsWith(File.separator)) {
                filePath = filePath.concat(File.separator);
            }
        }

        filePath = filePath.concat(fileName);

        return resolveFile(filePath);
    }

    /**
     * Resolve file with file Path.
     *
     * @param filePath file path to resolved as file.
     * @return resolved file
     */
    public static File resolveFile(String filePath) {
        Assert.hasLength(filePath, "File Path must not be null while resolving file.");
        if (filePath.startsWith(FILE_PREFIX) || filePath.startsWith(CLASSPATH_PREFIX)) {
            File configFile;
            try {
                configFile = ResourceUtils.getFile(filePath);
                if (configFile.exists()) {
                    return configFile.getCanonicalFile();
                }
            } catch (IOException e) {
                throw new InvalidConfigFilePathException(get().with(filePath).args());
            }
        } else {
            for (String path : SEARCH_PATH) {
                File configFile;
                try {
                    configFile = ResourceUtils.getFile(String.format("%s%s", path, filePath));
                    if (configFile.exists()) {
                        return configFile.getCanonicalFile();
                    }
                } catch (IOException e) {
                    throw new InvalidConfigFilePathException(get().with(filePath).args());
                }
            }
        }

        throw new ConfigFileNotFoundException(get().with(filePath).args());
    }
}
