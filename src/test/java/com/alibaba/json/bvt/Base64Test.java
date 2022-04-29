package com.alibaba.json.bvt;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.Base64;
import org.junit.Assert;

import com.alibaba.fastjson.util.IOUtils;

import junit.framework.TestCase;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;

import static org.junit.Assert.assertArrayEquals;


public class Base64Test extends TestCase {
    public void test_base64() throws Exception {
        Assert.assertEquals(IOUtils.decodeBase64(new char[0], 0, 0).length, 0);
        Assert.assertEquals(IOUtils.decodeBase64("ABC".toCharArray(), 0, 3).length, 2);
    }

    //decodeFast(char[] chars, int offset, int charsLen)
    public void testCheckLenZero() {
        char[] input = {};
        byte[] b = Base64.decodeFast(input, 0, input.length);
        byte[] expected = new byte[0];
        assertArrayEquals(expected, b);
    }

    public void testOffsetTrim1() {
        char[] input = "***ABC".toCharArray();
        byte[] b = Base64.decodeFast(input, 3, 3);
        byte[] expected = Base64.decodeFast("ABC".toCharArray(), 0, 3);
        assertArrayEquals(expected, b);
    }

    public void testOffsetTrim2() {
        char[] input = "###***ABC".toCharArray();
        byte[] b = Base64.decodeFast(input, 3, 6);
        byte[] expected = Base64.decodeFast("ABC".toCharArray(), 0, 3);
        assertArrayEquals(expected, b);
    }

    public void testOffsetTrim3() {
        char[] input = "###***ABC".toCharArray();
        byte[] b = Base64.decodeFast(input, 3, 6);
        byte[] expected = Base64.decodeFast("ABC".toCharArray(), 0, 3);
        assertArrayEquals(expected, b);
    }

    public void testOffsetTrimEnd() {
        char[] input = "ABC***".toCharArray();
        byte[] b = Base64.decodeFast(input, 0, 6);
        byte[] expected = Base64.decodeFast("ABC".toCharArray(), 0, 3);
        assertArrayEquals(expected, b);
    }

    public void testBasicWLine() {
        char[] input = "YWJjZGVmCmdoaWpr".toCharArray();
        byte[] b = Base64.decodeFast(input, 0, input.length);
        byte[] expected = ("abcdef\n" + "ghijk").getBytes(StandardCharsets.UTF_8);
        assertArrayEquals(expected, b);
    }

    public void testBasicWOLine() {
        char[] input = "YWJjZGVmZ2hpams=".toCharArray();
        byte[] b = Base64.decodeFast(input, 0, input.length);
        byte[] expected = ("abcdef" + "ghijk").getBytes(StandardCharsets.UTF_8);
        assertArrayEquals(expected, b);
    }

    public void testMultiLine() {
        char[] input = "YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd4eXphYmNkZWZnaGlqa2xtbm9wcXJzdHV2d3h5emFiY2RlZmdoaWprbG1ub3BxcnN0dXZ3eHl6YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd4eXphYmNkZWZnaGlqa2xtbm9wcXJzdHV2d3h5eg==".toCharArray();
        byte[] b = Base64.decodeFast(input, 0, input.length);
        byte[] expected = ("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz").getBytes(StandardCharsets.UTF_8);
        assertArrayEquals(expected, b);
    }

    public void testMultiLineBreak() {
        char[] input = ("YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh\r\r" +
                "YWFhYWFhYWFhYWFhYWFhYWFhYWFhYQ==").toCharArray();
        byte[] b = Base64.decodeFast(input, 0, input.length);
        byte[] expected = ("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa").getBytes(StandardCharsets.UTF_8);
        assertArrayEquals(expected, b);
    }




    public void testCheckLenZero_S() {
        String input = "";
        byte[] b = Base64.decodeFast(input, 0, input.length());
        byte[] expected = new byte[0];
        assertArrayEquals(expected, b);
    }

    public void testOffsetTrim1_S() {
        String input= "***ABC";
        byte[] b = Base64.decodeFast(input, 3, 3);
        byte[] expected = Base64.decodeFast("ABC".toCharArray(), 0, 3);
        assertArrayEquals(expected, b);
    }

    public void testOffsetTrim2_S() {
        String input = "###***ABC";
        byte[] b = Base64.decodeFast(input, 3, 6);
        byte[] expected = Base64.decodeFast("ABC".toCharArray(), 0, 3);
        assertArrayEquals(expected, b);
    }

    public void testOffsetTrim3_S() {
        String input = "###***ABC";
        byte[] b = Base64.decodeFast(input, 3, 6);
        byte[] expected = Base64.decodeFast("ABC".toCharArray(), 0, 3);
        assertArrayEquals(expected, b);
    }

    public void testOffsetTrimEnd_S() {
        String input = "ABC***";
        byte[] b = Base64.decodeFast(input, 0, 6);
        byte[] expected = Base64.decodeFast("ABC".toCharArray(), 0, 3);
        assertArrayEquals(expected, b);
    }

    public void testBasicWLine_S() {
        String input = "YWJjZGVmCmdoaWpr";
        byte[] b = Base64.decodeFast(input, 0, input.length());
        byte[] expected = ("abcdef\n" + "ghijk").getBytes(StandardCharsets.UTF_8);
        assertArrayEquals(expected, b);
    }

    public void testBasicWOLine_S() {
        String input = "YWJjZGVmZ2hpams=";
        byte[] b = Base64.decodeFast(input, 0, input.length());
        byte[] expected = ("abcdef" + "ghijk").getBytes(StandardCharsets.UTF_8);
        assertArrayEquals(expected, b);
    }

    public void testMultiLinev() {
        String input = "YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd4eXphYmNkZWZnaGlqa2xtbm9wcXJzdHV2d3h5emFiY2RlZmdoaWprbG1ub3BxcnN0dXZ3eHl6YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd4eXphYmNkZWZnaGlqa2xtbm9wcXJzdHV2d3h5eg==";
        byte[] b = Base64.decodeFast(input, 0, input.length());
        byte[] expected = ("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz").getBytes(StandardCharsets.UTF_8);
        assertArrayEquals(expected, b);
    }

    public void testMultiLineBreak_S() {
        String input = ("YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh\r\r" +
                "YWFhYWFhYWFhYWFhYWFhYWFhYWFhYQ==");
        byte[] b = Base64.decodeFast(input, 0, input.length());
        byte[] expected = ("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa").getBytes(StandardCharsets.UTF_8);
        assertArrayEquals(expected, b);
    }


    public void testCheckLenZero_S_ONLY() {
        String input = "";
        byte[] b = Base64.decodeFast(input);
        byte[] expected = new byte[0];
        assertArrayEquals(expected, b);
    }

    public void testOffsetTrim1_S_ONLY() {
        String input= "***ABC";
        byte[] b = Base64.decodeFast(input);
        byte[] expected = Base64.decodeFast("ABC".toCharArray(), 0, 3);
        assertArrayEquals(expected, b);
    }

    public void testOffsetTrim2_S_ONLY() {
        String input = "###***ABC";
        byte[] b = Base64.decodeFast(input);
        byte[] expected = Base64.decodeFast("ABC".toCharArray(), 0, 3);
        assertArrayEquals(expected, b);
    }

    public void testOffsetTrim3_S_ONLY() {
        String input = "###***ABC";
        byte[] b = Base64.decodeFast(input);
        byte[] expected = Base64.decodeFast("ABC".toCharArray(), 0, 3);
        assertArrayEquals(expected, b);
    }

    public void testOffsetTrimEnd_S_ONLY() {
        String input = "ABC***";
        byte[] b = Base64.decodeFast(input);
        byte[] expected = Base64.decodeFast("ABC".toCharArray(), 0, 3);
        assertArrayEquals(expected, b);
    }

    public void testBasicWLine_S_ONLY() {
        String input = "YWJjZGVmCmdoaWpr";
        byte[] b = Base64.decodeFast(input);
        byte[] expected = ("abcdef\n" + "ghijk").getBytes(StandardCharsets.UTF_8);
        assertArrayEquals(expected, b);
    }

    public void testBasicWOLine_S_ONLY() {
        String input = "YWJjZGVmZ2hpams=";
        byte[] b = Base64.decodeFast(input);
        byte[] expected = ("abcdef" + "ghijk").getBytes(StandardCharsets.UTF_8);
        assertArrayEquals(expected, b);
    }

    public void testMultiLinev_ONLY() {
        String input = "YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd4eXphYmNkZWZnaGlqa2xtbm9wcXJzdHV2d3h5emFiY2RlZmdoaWprbG1ub3BxcnN0dXZ3eHl6YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd4eXphYmNkZWZnaGlqa2xtbm9wcXJzdHV2d3h5eg==";
        byte[] b = Base64.decodeFast(input);
        byte[] expected = ("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz").getBytes(StandardCharsets.UTF_8);
        assertArrayEquals(expected, b);
    }

    public void testMultiLineBreak_S_ONLY() {
        String input = ("YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh\r\r" +
                "YWFhYWFhYWFhYWFhYWFhYWFhYWFhYQ==");
        byte[] b = Base64.decodeFast(input);
        byte[] expected = ("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa").getBytes(StandardCharsets.UTF_8);
        assertArrayEquals(expected, b);
    }



}
