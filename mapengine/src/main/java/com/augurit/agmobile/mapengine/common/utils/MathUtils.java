package com.augurit.agmobile.mapengine.common.utils;

/**
 * @author 创建人 ：weiqiuyue
 * @package 包名 ：com.augurit.am.intro.utils
 * @createTime 创建时间 ：2017-01-18
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：2017-01-18 14:04
 */
public class MathUtils {
    /**
     * Test a value in specified range, returning minimum if it's below, and maximum if it's above
     *
     * @param value Value to test
     * @param min   Minimum value of range
     * @param max   Maximum value of range
     * @return value if it's between min and max, min if it's below, max if it's above
     */
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Test a value in specified range, returning minimum if it's below, and maximum if it's above
     *
     * @param value Value to test
     * @param min   Minimum value of range
     * @param max   Maximum value of range
     * @return value if it's between min and max, min if it's below, max if it's above
     */
    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Constrains value to the given range (including min, excluding max) via modular arithmetic.
     * <p>
     * Same formula as used in Core GL (wrap.hpp)
     * std::fmod((std::fmod((value - min), d) + d), d) + min;
     *
     * @param value Value to wrap
     * @param min   Minimum value
     * @param max   Maximum value
     * @return Wrapped value
     */
    public static double wrap(double value, double min, double max) {
        double delta = max - min;

        double firstMod = (value - min) % delta;
        double secondMod = (firstMod + delta) % delta;

        return secondMod + min;
    }

    /**
     * Convert bearing from core to match Android SDK value.
     *
     * @param nativeBearing bearing value coming from core
     * @return bearing in degrees starting from 0 rotating clockwise
     */
    public static double convertNativeBearing(double nativeBearing) {
        double direction = -nativeBearing;

        while (direction > 360) {
            direction -= 360;
        }
        while (direction < 0) {
            direction += 360;
        }
        return direction;
    }
}
