package net.rustmc.cloud.base.util.operations;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * This class belongs to the HarmanCloud project
 *
 * @author Alexander Jilge
 * @since 21.10.2022
 */
public final class UnsafeInstanceOperations {

    private static final Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object construct() {
        try {
            return unsafe.allocateInstance(Object.class);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static<T> T construct(Class<T> tClass) {
        try {
            return (T) unsafe.allocateInstance(tClass);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

}
