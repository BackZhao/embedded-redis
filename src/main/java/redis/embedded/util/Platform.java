package redis.embedded.util;

public final class Platform {

    private final OS os;
    private final Architecture architecture;

    public Platform(OS os, Architecture architecture) {
        this.os = os;
        this.architecture = architecture;
    }

    public OS getOs() {
        return os;
    }

    public Architecture getArchitecture() {
        return architecture;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Platform))
            return false;

        Platform other = (Platform)obj;

        return os == other.os
            && architecture == other.architecture;
    }

    @Override
    public int hashCode() {
        return 31 * os.hashCode()
             + architecture.hashCode();
    }

    public String toString() {
        return os + "/" + architecture;
    }
}