package stee.monolith.common.utils;

import java.util.*;

/**
 * Models a permission, that is the ability to do something <br>
 *
 * <ul>
 * <li>CREATE: ability to insert</li>
 * <li>READ: ability to read</li>
 * <li>UPDATE: ability to UPDATE</li>
 * <li>SECURITY: ability to change security rules(permission)</li>
 * <li>DELETE: ability to delete</li>
 * <li>ARCHIVE: ability to archive</li>
 * <li>CALENDAR: ability to handle calendar events</li>
 * <li>PRINT: ability to print</li>
 * <li>EMAIL: ability to send emails</li>
 * <li>STORAGE: ability to handle the storage</li>
 * <li>DOWNLOAD: ability to download</li>
 * </ul>
 *
 * @author stee - Fortify
 */
public enum Permission {
    CREAD("cread"), READ("read"), UPDATE("write"), SECURITY("security"),
    DELETE("delete"), ARCHIVE("archive"), CALENDAR("calendar"), PRINT("print"),
    EMAIL("email"), STORAGE("storage"), DOWNLOAD("download");

    private final String name;

    private int mask;

    private Permission(String name) {
        this.name = name;

        Map<String, String> masks = new HashMap<>();
        masks.put("read", "0000000000000000000001"); //1
        masks.put("create", "0000000000000000000010"); //2
        masks.put("update", "0000000000000000000100"); //4
        masks.put("security", "0000000000000000001000"); //8
        masks.put("delete", "0000000000000000010000"); //16
        masks.put("archive", "0000000000000000100000"); //32
        masks.put("calendar", "0000000000000001000000"); //64
        masks.put("print", "0000000000000010000000"); //128
        masks.put("email", "0000000000000100000000"); //256
        masks.put("storage", "0000000000001000000000"); //512
        masks.put("archive", "0000000000010000000000"); //1024
        masks.put("download", "0000000000100000000000"); //2048

        this.mask = Integer.parseInt(masks.get(name), 2);
    }

    public String getName() {
        return name;
    }

    public int getMask() {
        return mask;
    }

    public boolean match(int permission) {
        return (permission & mask) != 0;
    }

    public static Permission valueOf(int mask) {
        for (Permission permission : all()) {
            if (permission.match(mask))
                return permission;
        }
        return null;
    }

    public static Set<Permission> all() {
        return new HashSet<>(Arrays.asList(Permission.values()));
    }

    public static Set<Permission> forGuests() {
        HashSet<Permission> set = new HashSet<>();
        set.add(READ);
        set.add(DOWNLOAD);
        set.add(PRINT);
        set.add(EMAIL);
        return set;
    }

    @Override
    public String toString() {
        return name;
    }
}
