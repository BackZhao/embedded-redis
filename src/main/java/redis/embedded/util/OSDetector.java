package redis.embedded.util;

import redis.embedded.exceptions.OsDetectionException;

public class OSDetector {

    public static OS getOS() {
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.startsWith("win")) {
            return OS.WINDOWS;
        } else if (osName.contains("linux")) {
            return OS.LINUX;
        } else if (osName.startsWith("mac")) {
            return OS.MACOS;
        } else {
            throw new OsDetectionException("Unrecognized/Unsupported OS: " + osName);
        }
    }

    public static Architecture getArchitecture() {
        String osArch = System.getProperty("os.arch").toLowerCase();
        switch (osArch) {
            case "x86":
            case "i386":
            case "i486":
            case "i586":
            case "i686":
                return Architecture.X86;

            case "amd64":
            case "x86_64":
            case "x64":
                return Architecture.X86_64;

            case "aarch64":
            case "arm64":
                return Architecture.ARM64;

            case "arm":
            case "armv6":
            case "armv7":
            case "armv7l":
            case "armv8l":
                return Architecture.ARM32;

            case "loongarch64":
                return Architecture.LOONGARCH64;

            case "riscv64":
            case "ppc64le":
            case "s390x":
            default:
                throw new OsDetectionException("Unrecognized/Unsupported Architecture: " + osArch);
        }
    }
}
