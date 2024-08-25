package stee.monolith.common.utils;

public enum UserType {
    SYSTEM("SYSTEM"),
    EXCHANGER("EXCHANGER"),
    SIMPLE("SIMPLE");

    private final String displayName;

    UserType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}

