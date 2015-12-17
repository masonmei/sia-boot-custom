package com.baidu.oped.sia.boot.resolver;

import static com.baidu.oped.sia.boot.utils.ArrayUtils.convertStringArrayToPrimitiveArr;
import static com.baidu.oped.sia.boot.utils.ArrayUtils.convertStringToPrimitive;

import com.baidu.oped.sia.boot.resolver.annotation.FromQuery;

import org.junit.Test;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

/**
 * Created by mason on 11/18/15.
 */
public class ResolverUtilsTest {

    @Test
    public void testGetDeclaredFields() throws Exception {
        Field[] fields = ResolverUtils.getDeclaredFields(TestTarget.class);
        TestTarget target = new TestTarget();
        for (Field field : fields) {
            FromQuery annotation = field.getAnnotation(FromQuery.class);
            ResolverUtils.makeAccessible(field);
            if (annotation != null) {
                String name = annotation.name();
                if (StringUtils.isEmpty(name)) {
                    name = field.getName();
                }
                if (field.getType().isArray()) {
                    System.out.println(field.getType());
                    try {
                        field.set(target, convertStringArrayToPrimitiveArr(field.getType(), new String[]{"1", "2"}));
                    } catch (IllegalAccessException e) {
                        doNothing();
                    }
                } else {
                    System.out.println(field.getType());
                    try {
                        field.set(target, convertStringToPrimitive(field.getType(), "1"));
                    } catch (IllegalAccessException e) {
                        doNothing();
                    }
                }
            }
        }
        System.out.println(target);
    }

    private void doNothing() {

    }

    @Test
    public void testMakeAccessible() throws Exception {

    }

    class TestTarget implements Resolvable {

        @FromQuery
        private boolean booleanValue;
        @FromQuery
        private byte byteValue;

        @FromQuery
        private char charValue;
        @FromQuery
        private short shortValue;
        @FromQuery
        private Integer intValue;
        private long longValue;
        private float floatValue;
        private double doubleValue;
        private String stringValue;
        private boolean[] booleanArrValue;
        @FromQuery
        private Byte[] byteArrValue;
        @FromQuery
        private byte[] byteArrValue2;
        private short[] shortArrValue;
        private int[] intArrValue;
        private long[] longArrValue;
        private float[] floatArrValue;
        private double[] doubleArrValue;
        private String[] stringArrValue;

        public boolean[] getBooleanArrValue() {
            return booleanArrValue;
        }

        public void setBooleanArrValue(boolean[] booleanArrValue) {
            this.booleanArrValue = booleanArrValue;
        }

        public Byte[] getByteArrValue() {
            return byteArrValue;
        }

        public void setByteArrValue(Byte[] byteArrValue) {
            this.byteArrValue = byteArrValue;
        }

        public byte getByteValue() {
            return byteValue;
        }

        public void setByteValue(byte byteValue) {
            this.byteValue = byteValue;
        }

        public double[] getDoubleArrValue() {
            return doubleArrValue;
        }

        public void setDoubleArrValue(double[] doubleArrValue) {
            this.doubleArrValue = doubleArrValue;
        }

        public double getDoubleValue() {
            return doubleValue;
        }

        public void setDoubleValue(double doubleValue) {
            this.doubleValue = doubleValue;
        }

        public float[] getFloatArrValue() {
            return floatArrValue;
        }

        public void setFloatArrValue(float[] floatArrValue) {
            this.floatArrValue = floatArrValue;
        }

        public float getFloatValue() {
            return floatValue;
        }

        public void setFloatValue(float floatValue) {
            this.floatValue = floatValue;
        }

        public int[] getIntArrValue() {
            return intArrValue;
        }

        public void setIntArrValue(int[] intArrValue) {
            this.intArrValue = intArrValue;
        }

        public int getIntValue() {
            return intValue;
        }

        public void setIntValue(int intValue) {
            this.intValue = intValue;
        }

        public long[] getLongArrValue() {
            return longArrValue;
        }

        public void setLongArrValue(long[] longArrValue) {
            this.longArrValue = longArrValue;
        }

        public long getLongValue() {
            return longValue;
        }

        public void setLongValue(long longValue) {
            this.longValue = longValue;
        }

        public short[] getShortArrValue() {
            return shortArrValue;
        }

        public void setShortArrValue(short[] shortArrValue) {
            this.shortArrValue = shortArrValue;
        }

        public short getShortValue() {
            return shortValue;
        }

        public void setShortValue(short shortValue) {
            this.shortValue = shortValue;
        }

        public String[] getStringArrValue() {
            return stringArrValue;
        }

        public void setStringArrValue(String[] stringArrValue) {
            this.stringArrValue = stringArrValue;
        }

        public String getStringValue() {
            return stringValue;
        }

        public void setStringValue(String stringValue) {
            this.stringValue = stringValue;
        }

        public boolean isBooleanValue() {
            return booleanValue;
        }

        public void setBooleanValue(boolean booleanValue) {
            this.booleanValue = booleanValue;
        }
    }
}