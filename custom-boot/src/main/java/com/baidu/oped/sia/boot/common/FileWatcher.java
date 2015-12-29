package com.baidu.oped.sia.boot.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.TimeUnit;

/**
 * Watch file for content changing and convert the file to POJO object.
 *
 * @param <T> The POJO Object from configuration file.
 * @author mason
 */
public class FileWatcher<T> {
    private static final Logger LOG = LoggerFactory.getLogger(FileWatcher.class);

    private final int refreshInterval;
    private final File configFile;
    private final Class<T> contentType;
    private DelegateHolder<T> holder;
    private boolean stop = false;

    public FileWatcher(File configFile, Class<T> type) {
        this(1, configFile, type);
    }

    /**
     * Construct a file watcher and convert to given type instance in the specified period.
     *
     * @param refreshInterval reload interval
     * @param configFile      reloadable configuration file
     * @param type            the configuration instance type
     */
    public FileWatcher(int refreshInterval, File configFile, Class<T> type) {
        Assert.state(refreshInterval > 0, "The minimum refresh interval is 1 second");
        Assert.notNull(configFile, "Configuration File must not be null.");
        Assert.notNull(type, "Content type must null be null.");

        if (!configFile.exists()) {
            LOG.error("Configuration File not exist.");
            throw new IllegalArgumentException("Configuration File not exists.");
        }

        if (configFile.isDirectory()) {
            LOG.error("Configuration File required a file, given a directory. Given path is {}", configFile.getPath());
            throw new IllegalArgumentException("Configuration File should not be a directory.");
        }

        if (!configFile.canRead()) {
            LOG.error("No permission to read Configuration File, given path is {}", configFile.getPath());
            throw new IllegalArgumentException("Configuration File cannot be read.");
        }

        this.refreshInterval = refreshInterval;
        this.configFile = configFile;
        this.contentType = type;

        load();
        watchingForChanges();
        registerShutDownHook();
    }

    public DelegateHolder<T> getHolder() {
        return holder;
    }

    private void load() {
        LOG.debug("start to load properties with path {}", configFile.getName());

        try (InputStream inputStream = new FileInputStream(configFile)) {
            Yaml yaml = new Yaml();
            T context = yaml.loadAs(inputStream, contentType);
            holder.setContext(context);
        } catch (IOException e) {
            LOG.warn("Cannot reading configurations");
        }

        LOG.info("reload properties finished.");
    }

    private void registerShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            stop = true;
        }));
    }

    private void watchingForChanges() {
        new Thread(() -> {
            try {
                final Path parentPath = Paths.get(configFile.getParent());
                final WatchService watchService = FileSystems.getDefault().newWatchService();
                parentPath.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

                while (!stop) {
                    final WatchKey watchKey = watchService.poll(refreshInterval, TimeUnit.SECONDS);
                    if (watchKey != null) {
                        watchKey.pollEvents().stream()
                                .filter(event -> configFile.getName().equals(event.context().toString()))
                                .forEach(event -> {
                                    LOG.debug("Start to loading properties changes.");
                                    load();
                                    LOG.debug("Finish to load properties changes.");
                                });
                    }
                }
            } catch (IOException | InterruptedException e) {
                LOG.warn("Watching file failed or reloading failed");
            }
        }).start();
    }

}
