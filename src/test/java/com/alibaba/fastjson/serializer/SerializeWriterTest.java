package com.alibaba.fastjson.serializer;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.IOUtils;

public class SerializeWriterTest {

    private final Logger logger = Logger.getLogger(SerializeWriterTest.class.getSimpleName());

    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

    private final SerializeWriter writer = new SerializeWriter(new OutputStreamWriter(baos));

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testWriteLiteBasicStr() throws UnsupportedEncodingException {
        String targetStr = new String(IOUtils.DIGITS);
        this.doTestWrite(targetStr);
    }

    private String doTestWrite(String input) throws UnsupportedEncodingException {
        writer.writeString(input, (char) 0);
        writer.flush();
        String result = this.baos.toString("UTF-8");

        Assert.assertEquals(input, JSON.parse(result));

        logger.info(result);

        return result;
    }

    @Test
    public void testWriteLiteSpecilaStr() throws UnsupportedEncodingException {
        this.doTestWrite(this.makeSpecialChars());
    }

    private String makeSpecialChars() {
        StringBuilder strBuilder = new StringBuilder(128);
        for (char c = 128; c <= 160; c++) {
            strBuilder.append(c);
        }
        return strBuilder.toString();
    }

    @Test
    public void testWriteLargeBasicStr() throws UnsupportedEncodingException {
        String str = createLargeBasicStr();
        this.doTestWrite(str);
    }

    private String createLargeBasicStr() {
        String tmp = new String(IOUtils.DIGITS);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 400; i++) {
            builder.append(tmp);
        }
        return builder.toString();
    }

    @Test
    public void testWriteLargeSpecialStr() throws UnsupportedEncodingException {

        String tmp = this.makeSpecialChars();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 200; i++) {
            builder.append(tmp);
        }
        this.doTestWrite(builder.toString());
    }

    @Test
    public void test_large() throws Exception {
        SerializeWriter writer = new SerializeWriter();

        for (int i = 0; i < 1024 * 1024; ++i) {
            writer.write(i);
        }

        writer.close();
    }

    @Test
    public void testBytesBufLocal() throws Exception {
        String str = createLargeBasicStr();
        SerializeWriter writer = new SerializeWriter();
        //????????????12K????????????
        writer.writeString(str);
        writer.writeString(str);
        byte[] bytes = writer.toBytes("UTF-8");
        writer.close();

        //??????bytesLocal??????????????????????????????????????????????????????bytes??????
        Field bytesBufLocalField = SerializeWriter.class.getDeclaredField("bytesBufLocal");
        bytesBufLocalField.setAccessible(true);
        ThreadLocal<byte[]> bytesBufLocal = (ThreadLocal<byte[]>) bytesBufLocalField.get(null);
        byte[] bytesLocal = bytesBufLocal.get();
        Assert.assertNotNull("bytesLocal is null", bytesLocal);
        Assert.assertTrue("bytesLocal is smaller than expected", bytesLocal.length >= bytes.length);
    }
}
