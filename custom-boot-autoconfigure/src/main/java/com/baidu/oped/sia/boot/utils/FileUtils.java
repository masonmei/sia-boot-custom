package com.baidu.oped.sia.boot.utils;

import org.springframework.util.StringUtils;

import java.io.File;

/**
 * Created by mason on 11/10/15.
 */
public abstract class FileUtils {
    /**
     * Get file with the given fileName and parent directory
     *
     * @param parentDirectory parent directory
     * @param fileName        file name
     * @return
     */
    public static File resolveConfigFile(String parentDirectory, String fileName) {
        File configFile;
        if (StringUtils.hasText(parentDirectory)) {
            configFile = new File(parentDirectory.concat(fileName));
        } else {
            String configFilePath = FileUtils.class.getClassLoader().getResource(fileName).getFile();
            configFile = new File(configFilePath);
        }

        if (!configFile.exists()) {
            configFile = null;
        }
        return configFile;
    }
}
