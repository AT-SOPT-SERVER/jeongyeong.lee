package org.sopt.common.utils;

public class IdGenrator {
    private static Long id = 1L;

    public static Long generateId() {
        return id++;
    }
}
