package redis.embedded.exceptions;

import redis.embedded.util.Platform;

public class UnsupportedPlatformException extends RuntimeException {

    public UnsupportedPlatformException(Platform platform) {
        super("Unsupported platform: " + platform);
    }
}