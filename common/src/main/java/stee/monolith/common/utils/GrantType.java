package stee.monolith.common.utils;

import java.util.*;

public enum GrantType {
    PASSWORD("password"),
    REFRESH_TOKEN("refreshToken");

    private final String name;
    private int mask;

    private GrantType(String name) {
        this.name = name;

        Map<String, String> masks = new HashMap<>();
        masks.put("password", "0000000000000000000001");
        masks.put("refreshToken", "0000000000000000000010");

        this.mask = Integer.parseInt(masks.get(name), 2);
    }

    public String getName() {
        return name;
    }

    public int getMask() {
        return mask;
    }

    public boolean match(int grantType) {
        return (grantType & mask) != 0;
    }

    public static GrantType valueOf(int mask) {
        for (GrantType grantType : all()) {
            if (grantType.match(mask))
                return grantType;
        }
        return null;
    }

    public static Set<GrantType> all() {
        return new HashSet<>(Arrays.asList(GrantType.values()));
    }

    @Override
    public String toString() {
        return name;
    }
}


