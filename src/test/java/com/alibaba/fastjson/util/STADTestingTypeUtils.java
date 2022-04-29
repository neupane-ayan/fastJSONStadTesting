package com.alibaba.fastjson.util;

import com.alibaba.fastjson.*;
import com.alibaba.fastjson.parser.ParserConfig;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertThrows;

@SuppressWarnings("rawtypes")
public class STADTestingTypeUtils extends TestCase {

    @Test
    public void testIsMapJavaBean() throws Exception {
        HashMap m = new HashMap();
        Assert.assertTrue(m == TypeUtils.castToJavaBean(m, Map.class));
    }

    @Test
    public void testIsJSONJavaBean() throws Exception {
        JSONObject m = new JSONObject();
        Assert.assertTrue(m == TypeUtils.castToJavaBean(m, Map.class));
    }

    @Test
    public void testCastBeanWithKeyVal() throws Exception {
        JSONObject m = new JSONObject();
        m.put("id", 1);
        m.put("name", "connor");
        TestUser user = TypeUtils.castToJavaBean(m, TestUser.class);
        Assert.assertEquals(1L, user.getID());
        Assert.assertEquals("connor", user.getName());
    }

    @Test
    public void testCastInteger() throws Exception {
        JSONObject j = new JSONObject();
        j.put("id", 1L);
        Assert.assertEquals(new Integer(1), j.getObject("id", int.class));
    }

    @Test
    public void testCastIntegerObject() throws Exception {
        JSONObject j = new JSONObject();
        j.put("id", 1L);
        Assert.assertEquals(new Integer(1), j.getObject("id", Integer.class));
    }

    @Test
    public void testCastLong() throws Exception {
        JSONObject j = new JSONObject();
        j.put("id", 1);
        Assert.assertEquals(new Long(1), j.getObject("id", long.class));
    }

    @Test
    public void testCastLongObject() throws Exception {
        JSONObject j = new JSONObject();
        j.put("id", 1);
        Assert.assertEquals(new Long(1), j.getObject("id", Long.class));
    }

    @Test
    public void testCastShort() throws Exception {
        JSONObject j = new JSONObject();
        j.put("id", 1);
        Assert.assertEquals(new Short((short) 1), j.getObject("id", short.class));
    }

    @Test
    public void testCastShortObject() throws Exception {
        JSONObject j = new JSONObject();
        j.put("id", 1);
        Assert.assertEquals(new Short((short) 1), j.getObject("id", Short.class));
    }

    @Test
    public void testCastByte() throws Exception {
        JSONObject j = new JSONObject();
        j.put("id", 1);
        Assert.assertEquals(new Byte((byte) 1), j.getObject("id", byte.class));
    }

    @Test
    public void testCastByteObject() throws Exception {
        JSONObject j = new JSONObject();
        j.put("id", 1);
        Assert.assertEquals(new Byte((byte) 1), j.getObject("id", Byte.class));
    }

    @Test
    public void testCastBigInt() throws Exception {
        JSONObject j = new JSONObject();
        j.put("id", 1);
        Assert.assertEquals(new BigInteger("1"), j.getObject("id", BigInteger.class));
    }

    @Test
    public void testCastBigDecimal() throws Exception {
        JSONObject j = new JSONObject();
        j.put("id", 1);
        Assert.assertEquals(new BigDecimal("1"), j.getObject("id", BigDecimal.class));
    }

    @Test
    public void testCastBool() throws Exception {
        JSONObject j = new JSONObject();
        j.put("id", 1);
        Assert.assertEquals(Boolean.TRUE, j.getObject("id", boolean.class));
    }

    @Test
    public void testCastBoolObject() throws Exception {
        JSONObject j = new JSONObject();
        j.put("id", 1);
        Assert.assertEquals(Boolean.TRUE, j.getObject("id", Boolean.class));
    }

    @Test
    public void testCastNull() throws Exception {
        JSONObject j = new JSONObject();
        j.put("id", null);
        Assert.assertEquals(null, j.getObject("id", Boolean.class));
    }

    @Test
    public void testCastString() throws Exception {
        JSONObject j = new JSONObject();
        j.put("id", 1);
        Assert.assertEquals("1", j.getObject("id", String.class));
    }

    @Test
    public void testCastDate() throws Exception {
        long ms = System.currentTimeMillis();
        JSONObject j = new JSONObject();
        j.put("d", ms);
        Assert.assertEquals(new Date(ms), j.getObject("d", Date.class));
    }

    @Test
    public void testCastSQLDate() throws Exception {
        long ms = System.currentTimeMillis();
        JSONObject j = new JSONObject();
        j.put("d", ms);
        Assert.assertEquals(new java.sql.Date(ms), j.getObject("d", java.sql.Date.class));
    }

    @Test
    public void testCastSQLDateCastString() throws Exception {
        long ms = System.currentTimeMillis();
        JSONObject j = new JSONObject();
        j.put("d", Long.toString(ms));
        Assert.assertEquals(new java.sql.Date(ms), j.getObject("d", java.sql.Date.class));
    }

    @Test
    public void testCastSQLDatePutNull() throws Exception {
        JSONObject j = new JSONObject();
        j.put("d", null);
        Assert.assertEquals(null, j.getObject("d", java.sql.Date.class));
    }

    @Test
    public void testCastSQLDateAllNull() throws Exception {
        Assert.assertEquals(null, TypeUtils.castToSqlDate(null));
    }

    @Test
    public void testCastSQLUtilDate() throws Exception {
        long ms = System.currentTimeMillis();
        JSONObject j = new JSONObject();
        j.put("d", new Date(ms));
        Assert.assertEquals(new java.sql.Date(ms), j.getObject("d", java.sql.Date.class));
    }

    @Test
    public void testCastSQLCalendar() throws Exception {
        long ms = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ms);

        JSONObject j = new JSONObject();
        j.put("d", calendar);
        Assert.assertEquals(new java.sql.Date(ms), j.getObject("d", java.sql.Date.class));
    }

    @Test
    public void testCastToSQLDateException() throws Exception {
        JSONObject j = new JSONObject();
        j.put("d", 0);
        Exception exception = assertThrows(JSONException.class, () -> {
            j.getObject("d", java.sql.Date.class);
        });
    }

    @Test
    public void testCastToJavaTimeStamp() throws Exception {
        long ms = System.currentTimeMillis();
        JSONObject j = new JSONObject();
        j.put("d", ms);
        Assert.assertEquals(new Timestamp(ms), j.getObject("d", Timestamp.class));
    }

    @Test
    public void testCastToJavaTimeStampString() throws Exception {
        long ms = System.currentTimeMillis();
        JSONObject j = new JSONObject();
        j.put("d", Long.toString(ms));
        Assert.assertEquals(new Timestamp(ms), j.getObject("d", Timestamp.class));
    }

    @Test
    public void testCastToJavaTimeStampWithNumber() throws Exception {
        long ms = System.currentTimeMillis();
        JSONObject j = new JSONObject();
        j.put("d", new BigDecimal(Long.toString(ms)));
        Assert.assertEquals(new Timestamp(ms), j.getObject("d", Timestamp.class));
    }

    @Test
    public void testCastToJavaTimeStampNull() throws Exception {
        JSONObject j = new JSONObject();
        j.put("d", null);
        Assert.assertEquals(null, j.getObject("d", Timestamp.class));
    }

    @Test
    public void testCastToJavaTimeStampAllNull() throws Exception {
        Assert.assertEquals(null, TypeUtils.castToTimestamp(null));
    }

    @Test
    public void testCastToTimeStampTheStartOfTime() throws Exception {
        JSON.defaultTimeZone = TimeZone.getTimeZone("Asia/Shanghai");
        Assert.assertEquals(new Timestamp(0), TypeUtils.castToTimestamp("1970-01-01 08:00:00"));
    }

    @Test
    public void castToBigDecimalEqual() throws Exception {
        BigDecimal val = new BigDecimal("123");
        Assert.assertEquals(true, val == TypeUtils.castToBigDecimal(val));
    }

    @Test
    public void castToBigIntegerEqual() throws Exception {
        BigInteger val = new BigInteger("123");
        Assert.assertEquals(true, val == TypeUtils.castToBigInteger(val));
    }

    @Test
    public void testCastArray() throws Exception {
        Assert.assertEquals(Integer[].class, TypeUtils.cast(new ArrayList(), Integer[].class, null).getClass());
    }

    @Test
    public void testCastTimeStampUtil() throws Exception {
        long ms = System.currentTimeMillis();

        JSONObject j = new JSONObject();
        j.put("d", new Date(ms));
        Assert.assertEquals(new Timestamp(ms), j.getObject("d", Timestamp.class));
    }

    @Test
    public void testCastTimestampSQLDate() throws Exception {
        long ms = System.currentTimeMillis();

        JSONObject j = new JSONObject();
        j.put("d", new java.sql.Date(ms));
        Assert.assertEquals(new Timestamp(ms), j.getObject("d", Timestamp.class));
    }

    @Test
    public void testCastSQLTimeStamp() throws Exception {
        long ms = System.currentTimeMillis();

        Timestamp d = new Timestamp(ms);
        Assert.assertEquals(d, TypeUtils.castToTimestamp(d));
    }

    @Test
    public void testCastTimeStampSQLWithKey() throws Exception {
        long ms = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ms);

        JSONObject j = new JSONObject();
        j.put("d", calendar);
        Assert.assertEquals(new Timestamp(ms), j.getObject("d", Timestamp.class));
    }

    @Test
    public void testCastNoError() throws Exception {
        JSONObject j = new JSONObject();
        j.put("d", -1);

        JSONException error = null;
        try {
            j.getObject("d", Timestamp.class);
        } catch (JSONException e) {
            error = e;
        }
        Assert.assertNull(error);
        Assert.assertEquals(new Timestamp(-1L), (Timestamp) j.getObject("d", Timestamp.class));
    }

    @Test
    public void testCastCustomObjectAB() throws Exception {
        B b = new B();

        JSONObject j = new JSONObject();
        j.put("val", b);
        Assert.assertEquals(b, j.getObject("val", A.class));
    }

    @Test
    public void testCastCustomObjectInterfaceAClass() throws Exception {
        B b = new B();

        JSONObject j = new JSONObject();
        j.put("val", b);
        Assert.assertEquals(b, j.getObject("val", InterfaceA.class));
    }

    @Test
    public void testCastCustomABException() throws Exception {
        A a = new A();

        JSONObject j = new JSONObject();
        j.put("val", a);

        JSONException error = null;
        try {
            j.getObject("val", B.class);
        } catch (JSONException e) {
            error = e;
        }
        Assert.assertNotNull(error);
    }

    @Test
    public void testJSONCastJavaBeanExcept() throws Exception {
        JSONObject j = new JSONObject();
        j.put("id", 1);

        JSONException error = null;
        try {
            TypeUtils.castToJavaBean(j, C.class, ParserConfig.getGlobalInstance());
        } catch (JSONException e) {
            error = e;
        }
        Assert.assertNotNull(error);
    }

    @Test
    public void testCastParserError() throws Exception {
        JSONObject j = new JSONObject();
        j.put("id", 1);

        Method method = STADTestingTypeUtils.class.getMethod("testFunc", List.class);

        Throwable error = null;
        try {
            TypeUtils.cast(j, method.getGenericParameterTypes()[0], ParserConfig.getGlobalInstance());
        } catch (JSONException ex) {
            error = ex;
        }
        assertNotNull(error);
    }

    @Test
    public void testPutIsCorrect() throws Exception {
        JSONObject m = new JSONObject();
        m.put("id", 1);
        m.put("name", "connor");

        TestUser user = JSON.toJavaObject(m, TestUser.class);
        Assert.assertEquals(1L, user.getID());
        Assert.assertEquals("connor", user.getName());
    }

    @Test
    public void testParsFloatWithLoop() throws Exception {
        Random rand = new Random();
        for (int i = 0; i < 99999; ++i) {
            String str = Float.toString(rand.nextFloat());
            assertEquals(Double.parseDouble(str), TypeUtils.parseDouble(str));
        }
    }

    @Test
    public void testParseDoubleWithLoop() throws Exception {
        Random rand = new Random();
        for (int i = 0; i < 99999; ++i) {
            String str = Double.toString(rand.nextDouble());
            assertEquals(Double.parseDouble(str), TypeUtils.parseDouble(str));
        }
    }


    @Test
    public void testParseIntAsDoubleLoop() throws Exception {
        Random rand = new Random();
        for (int i = 0; i < 99999; ++i) {
            String str = Integer.toString(rand.nextInt());
            assertEquals(Double.parseDouble(str), TypeUtils.parseDouble(str));
        }
    }

    @Test
    public void testParseLargerIntInLoop() throws Exception {
        Random rand = new Random();

        for (int i = 0; i < 99999; ++i) {
            String str = Integer.toString(rand.nextInt(99999));
            assertEquals(Double.parseDouble(str), TypeUtils.parseDouble(str));
        }
    }

    @Test
    public void testParseLongAsDouble() throws Exception {
        Random r = new Random();
        for (int i = 0; i < 99999; ++i) {
            String str = Long.toString(r.nextLong());
            assertEquals(Double.parseDouble(str), TypeUtils.parseDouble(str));
        }
    }

    @Test
    public void testParseCustomDoubles() throws Exception {
        String[] arr = new String[] {
                "0.34856254",
                "1",
                ".1"
                ,"1.1"
                , "0.1"
                , "0.12"
                , "0.123"
                , "0.1234"
                , "0.12345"
        };

        for (String str : arr) {
            assertEquals(Double.parseDouble(str), TypeUtils.parseDouble(str));
        }
    }

    @Test
    public void testCastToDate() throws Exception {
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
        Date d = TypeUtils.castToDate("2012-07-15 12:12:11");
        SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sDF.setTimeZone(JSON.defaultTimeZone);
        Assert.assertEquals(sDF.parseObject("2012-07-15 12:12:11"), d);
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    }

    @Test
    public void testCastToDateError() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            TypeUtils.castToDate("****-MM-dd");
        });
    }

    @Test
    public void testCastToDateZero() throws Exception {
        Date d = new Date(0);
        Assert.assertEquals(d, TypeUtils.castToDate("0"));
    }

    @Test
    public void testCastToDateNegative() throws Exception {
        Date d = new Date(-1);
        Assert.assertEquals(d, TypeUtils.castToDate(-1));
    }

    @Test
    public void testBigDecimal() {
        BigDecimal bd = new BigDecimal(20220101);
        Date d = new Date(20220101);
        Assert.assertEquals(d, TypeUtils.castToDate(bd));
    }

    @Test
    public void testBigDecimal_F() {
        BigDecimal bd = new BigDecimal(20220101);
        Date d = new Date(20220101);
        Assert.assertEquals(d, TypeUtils.castToDate(bd,"yyyy-MM-dd"));
    }

    @Test
    public void testStrF1() throws ParseException {
        SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", JSON.defaultLocale);
        sDF.setTimeZone(JSON.defaultTimeZone);
        Date d = sDF.parse("2022-02-02 10:00:00");
        String bd = "2022-02-02 10:00:00";
        Assert.assertEquals(d, TypeUtils.castToDate(bd,null));
    }

    @Test
    public void testStrF2() throws ParseException {
        SimpleDateFormat dF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", JSON.defaultLocale);
        dF.setTimeZone(JSON.defaultTimeZone);
        Date d = dF.parse("2020-08-24T21:49:31.702+04:00");
        String bd = "2020-08-24T21:49:31.702+04:00";
        Assert.assertEquals(d, TypeUtils.castToDate(bd,null));
    }

    @Test
    public void testStrF3() throws ParseException {
        SimpleDateFormat dF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", JSON.defaultLocale);
        dF.setTimeZone(JSON.defaultTimeZone);
        Date d = dF.parse("2013-07-29 06:35:40.622");
        String bd = "2013-07-29 06:35:40.622";
        Assert.assertEquals(d, TypeUtils.castToDate(bd,null));
    }

    @Test
    public void testStrF4() throws ParseException {
        SimpleDateFormat dF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS", JSON.defaultLocale);
        dF.setTimeZone(JSON.defaultTimeZone);
        Date d = dF.parse("2013-07-29 06:35:40,622");
        String bd = "2013-07-29 06:35:40,622";
        Assert.assertEquals(d, TypeUtils.castToDate(bd,null));
    }

    @Test
    public void testStrF5Unix() throws ParseException {
        Date d = new Date(20220000);
        Number bd = 20220.000;
        Assert.assertEquals(d.getTime(), TypeUtils.castToDate(bd,"unixtime").getTime());
    }

    @Test
    public void testCastBigIntDoubleNanInfinite() throws Exception {
        Assert.assertEquals(null, TypeUtils.castToBigInteger(1.0d / 0.0d));
        Assert.assertEquals(null, TypeUtils.castToBigInteger(-1.0d / 0.0d));
        Assert.assertEquals(null, TypeUtils.castToBigInteger(0.0d / 0.0d));
    }

    @Test
    public void testCastBigIntFloatNanInfinite() throws Exception {
        Assert.assertEquals(null, TypeUtils.castToBigInteger(1.0f / 0.0f));
        Assert.assertEquals(null, TypeUtils.castToBigInteger(-1.0f / 0.0f));
        Assert.assertEquals(null, TypeUtils.castToBigInteger(0.0f / 0.0f));
    }

    @Test
    public void testCastBytesToDateByteArrayEqual() throws Exception {
        byte[] b = new byte[0];
        Assert.assertArrayEquals(b, TypeUtils.castToBytes(b));
    }

    @Test
    public void testCastBigIntToDateThrowsError() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            TypeUtils.castToBytes(new int[0]);
        });
    }

    @Test
    public void testCastBigDecFloatNanInfinite_BigDecimal() throws Exception {
        Assert.assertEquals(null, TypeUtils.castToBigDecimal(1.0f / 0.0f));
        Assert.assertEquals(null, TypeUtils.castToBigDecimal(-1.0f / 0.0f));
        Assert.assertEquals(null, TypeUtils.castToBigDecimal(0.0f / 0.0f));
    }

    @Test
    public void testCastBigDecDoubleNanInfinite_BigDecimal() throws Exception {
        Assert.assertEquals(null, TypeUtils.castToBigDecimal(1.0d / 0.0d));
        Assert.assertEquals(null, TypeUtils.castToBigDecimal(-1.0d / 0.0d));
        Assert.assertEquals(null, TypeUtils.castToBigDecimal(0.0d / 0.0d));
    }

    @Test
    public void testCastBigIntFloatNanInfinite_BigDecimal_INT() throws Exception {
        Assert.assertEquals(null, TypeUtils.castToBigInteger(1.0f / 0.0f));
        Assert.assertEquals(null, TypeUtils.castToBigInteger(-1.0f / 0.0f));
        Assert.assertEquals(null, TypeUtils.castToBigInteger(0.0f / 0.0f));
    }

    @Test
    public void testCastBigIntNanInfinite_BigDecimal_INT() throws Exception {
        Assert.assertEquals(null, TypeUtils.castToBigInteger(1.0d / 0.0d));
        Assert.assertEquals(null, TypeUtils.castToBigInteger(-1.0d / 0.0d));
        Assert.assertEquals(null, TypeUtils.castToBigInteger(0.0d / 0.0d));
    }

    @Test
    public void testNullString_BigDecimal_INT() throws Exception {
        Assert.assertEquals(null, TypeUtils.castToBigInteger(""));
        Assert.assertEquals(null, TypeUtils.castToBigInteger("null"));
    }

    @Test
    public void testNullString_BigDecimal() throws Exception {
        Assert.assertEquals(null, TypeUtils.castToBigDecimal(""));
        Assert.assertEquals(null, TypeUtils.castToBigDecimal("null"));
    }

    @Test
    public void testCastByteArray() throws Exception {
        byte[] b = new byte[0];
        Assert.assertArrayEquals(b, TypeUtils.cast(b, byte[].class, null));
    }

    @Test
    public void testCastRefList() throws Exception {
        ParameterizedType pT = (ParameterizedType) new TypeReference<List<?>>(){}.getType();
        Type t = pT.getActualTypeArguments()[0];
        Assert.assertEquals(null, TypeUtils.cast("", t, null));
    }

    @Test
    public void testCastThrowsErrorFromUnknownNullClass() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            TypeUtils.cast(0, (Class<?>) null, null);
        });
    }

    @Test
    public void testCastErrorIncorrectClass() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            TypeUtils.cast("xxx", Object[].class, null);
        });
    }

    @Test
    public void testCastErrorIncorrectObject() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            TypeUtils.cast(12345, new TypeReference<Object[]>() {
            }.getType(), null);
        });
    }

    @Test
    public void testExceptionClassRuntime() throws Exception {
        JSONObject o = (JSONObject) JSON.toJSON(new RuntimeException());
        o.put("class", "xxxxx");
        Assert.assertEquals(Exception.class, JSON.parseObject(o.toJSONString(), Exception.class).getClass());
    }

    @Test
    public void testCastNullReferenceObject() throws Exception {
        TypeReference<Object> tR = new TypeReference<Object>(){};
        Assert.assertEquals(null, TypeUtils.cast(null, tR.getType(), null));
    }

    @Test
    public void testTypeUtilsCastAllTypes() throws Exception {
        Assert.assertEquals(null, TypeUtils.cast("", Byte.class, null));
        Assert.assertEquals(null, TypeUtils.cast("", Short.class, null));
        Assert.assertEquals(null, TypeUtils.cast("", Integer.class, null));
        Assert.assertEquals(null, TypeUtils.cast("", Long.class, null));
        Assert.assertEquals(null, TypeUtils.cast("", Float.class, null));
        Assert.assertEquals(null, TypeUtils.cast("", Double.class, null));
        Assert.assertEquals(null, TypeUtils.cast("", Character.class, null));
        Assert.assertEquals(null, TypeUtils.cast("", java.util.Date.class, null));
        Assert.assertEquals(null, TypeUtils.cast("", java.sql.Date.class, null));
        Assert.assertEquals(null, TypeUtils.cast("", java.sql.Timestamp.class, null));
        Assert.assertEquals(null, TypeUtils.castToChar(""));
        Assert.assertEquals(null, TypeUtils.castToChar(null));
        Assert.assertEquals('A', TypeUtils.castToChar('A').charValue());
        Assert.assertEquals('A', TypeUtils.castToChar("A").charValue());
        Assert.assertEquals(null, TypeUtils.castToBigDecimal(""));
        Assert.assertEquals(null, TypeUtils.castToBigInteger(""));
        Assert.assertEquals(null, TypeUtils.castToBoolean(""));
    }

    @Test
    public void assertExpectedNullCastUtilsError() throws Exception {
        TypeReference<Object> tR = new TypeReference<Object>(){};
        Assert.assertEquals(null, TypeUtils.cast("", tR.getType(), null));
    }

    @Test
    public void testCastErrorExpectNullException() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            TypeReference<Object> tR = new TypeReference<Object>(){};
            Assert.assertEquals(null, TypeUtils.cast("a", tR.getType(), null));
        });
    }

    @Test
    public void testThrowsErrorCastToTypeRefList() throws Exception {
        TypeReference<Object> tR = new TypeReference<Object>(){};
        Exception exception = assertThrows(Exception.class, () -> {
            Assert.assertEquals(null, TypeUtils.cast("a", ((ParameterizedType) tR.getType()).getActualTypeArguments()[0], null));
        });
    }

    @Test
    public void testThrowsErrorCastToCharIncorrectString() throws Exception {
        TypeReference<Object> tR = new TypeReference<Object>(){};
        Exception exception = assertThrows(Exception.class, () -> {
            TypeUtils.castToChar("abc");
        });
    }

    @Test
    public void testThrowsErrorCastToCharIncorrectBool() throws Exception {
        TypeReference<Object> tR = new TypeReference<Object>(){};
        Exception exception = assertThrows(Exception.class, () -> {
            TypeUtils.castToChar(true);
        });
    }

    @Test
    public void testExceptionStackTraceToJSON() throws Exception {
        RuntimeException ex = new RuntimeException();
        JSONObject object = (JSONObject) JSON.toJSON(ex);
        JSONArray array = object.getJSONArray("stackTrace");
        array.getJSONObject(0).put("lineNumber", null);
        JSON.parseObject(object.toJSONString(), Exception.class);
    }

    //CLASS STUFF:
    public static class TestUser {
        private long id;
        private String name;
        public long getID() {
            return id;
        }
        public void setId(long id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }

    public static class A implements InterfaceA {

    }

    public static interface InterfaceA {

    }

    public static class B extends A {

    }

    public static class C extends B {

        public int getID() {
            throw new UnsupportedOperationException();
        }

        public void setId(int id) {
            throw new UnsupportedOperationException();
        }
    }

    public static void testFunc(List<?> l) {

    }
}
