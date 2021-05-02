package com.crud.helper;

import java.util.AbstractMap;
import java.util.Collection;

public class NullEmptyChecker {
    public static boolean isNotNullOrEmpty(Object obj) {
        return !isNullOrEmpty(obj);
    }

    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else {
            if (obj instanceof Collection) {
                if (((Collection) obj).isEmpty()) {
                    return true;
                }
            } else if (obj instanceof AbstractMap) {
                if (((AbstractMap) obj).isEmpty()) {
                    return true;
                }
            } else if (obj.toString().trim().equals("")) {
                return true;
            }
        }

        return false;
    }
}
