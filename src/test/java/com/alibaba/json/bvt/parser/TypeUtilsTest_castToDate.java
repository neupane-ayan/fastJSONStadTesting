package com.alibaba.json.bvt.parser;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import junit.framework.TestCase;

import org.junit.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.TypeUtils;
import sun.util.calendar.BaseCalendar;

import javax.validation.constraints.AssertTrue;

public class TypeUtilsTest_castToDate extends TestCase {
    protected void setUp() throws Exception {
        JSON.defaultTimeZone = TimeZone.getTimeZone("Asia/Shanghai");
        JSON.defaultLocale = Locale.CHINA;
    }
    
    public void test_castToDate() throws Exception {
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
        Date date = TypeUtils.castToDate("2012-07-15 12:12:11");
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(JSON.defaultTimeZone);
        Assert.assertEquals(format.parseObject("2012-07-15 12:12:11"), date);
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    }

    public void test_castToDate_error() throws Exception {
        Exception error = null;
        try {
            TypeUtils.castToDate("你妈你妈-MM-dd");
        } catch (Exception ex) {
            error = ex;
        }
        Assert.assertNotNull(error);
    }

    public void test_castToDate_zero() throws Exception {
        Assert.assertEquals(new Date(0), TypeUtils.castToDate("0"));
    }

    public void test_castToDate_negative() throws Exception {
        Assert.assertEquals(new Date(-1), TypeUtils.castToDate(-1));
    }

    //STAD TESTING
    public void testBigDecimal() {
        BigDecimal bd = new BigDecimal(20220101);
        Date date = new Date(20220101);
        Assert.assertEquals(date, TypeUtils.castToDate(bd));
    }
    public void testBigDecimal_F() {
        BigDecimal bd = new BigDecimal(20220101);
        Date date = new Date(20220101);
        Assert.assertEquals(date, TypeUtils.castToDate(bd,"yyyy-MM-dd"));
    }
    public void testStr_F1() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", JSON.defaultLocale);
        dateFormat.setTimeZone(JSON.defaultTimeZone);
        Date date = dateFormat.parse("2022-02-02 10:00:00");
        String bd = "2022-02-02 10:00:00";
        Assert.assertEquals(date, TypeUtils.castToDate(bd,null));
    }

    public void testStr_F2() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", JSON.defaultLocale);
        dateFormat.setTimeZone(JSON.defaultTimeZone);
        Date date = dateFormat.parse("2020-08-24T21:49:31.702+04:00");
        String bd = "2020-08-24T21:49:31.702+04:00";
        Assert.assertEquals(date, TypeUtils.castToDate(bd,null));
    }

    public void testStr_F3() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", JSON.defaultLocale);
        dateFormat.setTimeZone(JSON.defaultTimeZone);
        Date date = dateFormat.parse("2013-07-29 06:35:40.622");
        String bd = "2013-07-29 06:35:40.622";
        Assert.assertEquals(date, TypeUtils.castToDate(bd,null));
    }

    public void testStr_F4() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS", JSON.defaultLocale);
        dateFormat.setTimeZone(JSON.defaultTimeZone);
        Date date = dateFormat.parse("2013-07-29 06:35:40,622");
        String bd = "2013-07-29 06:35:40,622";
        Assert.assertEquals(date, TypeUtils.castToDate(bd,null));
    }

    public void testStr_F5Unix() throws ParseException {
        Date date = new Date(20220000);
        Number bd = 20220.000;
        Assert.assertEquals(date.getTime(), TypeUtils.castToDate(bd,"unixtime").getTime());
    }



}
