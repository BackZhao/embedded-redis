package redis.embedded;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import redis.embedded.util.Architecture;
import redis.embedded.util.JarUtil;
import redis.embedded.util.OS;
import redis.embedded.util.OSDetector;
import redis.embedded.util.Platform;
import redis.embedded.exceptions.UnsupportedPlatformException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class RedisExecProvider {
    
    private final Map<Platform, String> executables = Maps.newHashMap();

    public static RedisExecProvider defaultProvider() {
        return new RedisExecProvider();
    }
    
    private RedisExecProvider() {
        initExecutables();
    }

    private void initExecutables() {
        executables.put(new Platform(OS.WINDOWS, Architecture.X86_64), "redis-server-2.8.19.exe");
        executables.put(new Platform(OS.LINUX, Architecture.X86), "redis-server-2.8.19-linux-x86");
        executables.put(new Platform(OS.LINUX, Architecture.X86_64), "redis-server-2.8.19-linux-x86_64");
        executables.put(new Platform(OS.LINUX, Architecture.ARM32), "redis-server-2.8.19-linux-arm");
        executables.put(new Platform(OS.LINUX, Architecture.ARM64), "redis-server-2.8.19-linux-aarch64");
        executables.put(new Platform(OS.LINUX, Architecture.LOONGARCH64), "redis-server-2.8.19-linux-loongarch64");
        executables.put(new Platform(OS.MACOS, Architecture.X86_64), "redis-server-2.8.19-mac-x86_64");
    }

    public RedisExecProvider override(OS os, String executable) {
        Preconditions.checkNotNull(executable);
        for (Architecture arch : Architecture.values()) {
            override(os, arch, executable);
        }
        return this;
    }

    public RedisExecProvider override(OS os, Architecture arch, String executable) {
        Preconditions.checkNotNull(executable);
        executables.put(new Platform(os, arch), executable);
        return this;
    }
    
    public File get() throws IOException {
        Platform platform = new Platform(OSDetector.getOS(), OSDetector.getArchitecture());
        String executablePath = executables.get(platform);

        if (executablePath == null) {
            throw new UnsupportedPlatformException(platform);
        }
        
         return fileExists(executablePath) ?
                new File(executablePath) :
                JarUtil.extractExecutableFromJar(executablePath);
        
    }

    private boolean fileExists(String executablePath) {
        return new File(executablePath).exists();
    }
}
