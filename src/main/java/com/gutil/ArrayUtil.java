package com.gutil;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ArrayUtil {

    public static <T> void setEach(T[] array, Function<Integer, T> setter) {
        if (array == null || setter == null) {
            return;
        }

        for (int i = 0; i < array.length; ++i) {
            array[i] = setter.apply(i);
        }
    }

    public static <T> void setEach(T[][] array, BiFunction<Integer, Integer, T> setter) {
        if (array == null || setter == null) {
            return;
        }

        for (int i = 0; i < array.length; ++i) {
            final int row = i;
            setEach(array[i], j -> setter.apply(row, j));
        }
    }

}
