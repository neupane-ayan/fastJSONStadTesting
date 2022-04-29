package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.util.TypeUtils;
import com.diffblue.deeptestutils.Reflector;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import java.util.Locale;

import static com.alibaba.fastjson.util.TypeUtils.fnv1a_64_magic_hashcode;
import static com.alibaba.fastjson.util.TypeUtils.fnv1a_64_magic_prime;
import static org.junit.Assert.assertThrows;

public class testJSONLexerBase {

    @Test
    public void test_0() throws Exception {
        JSONScanner lexer = new JSONScanner("\"value\":\"aa\\n\"");
        long hashCode = lexer.scanFieldSymbol("\"value\":".toCharArray());
        Assert.assertEquals(0, hashCode);
        Assert.assertEquals(JSONScanner.NOT_MATCH, lexer.matchStat());
    }
    @Test
    public void test_6() throws Exception {
        JSONScanner lexer = new JSONScanner("\"value\":\"aa\"}{");
        long hashCode = lexer.scanFieldSymbol("\"value\":".toCharArray());
        Assert.assertEquals(0, hashCode);
        Assert.assertEquals(JSONScanner.NOT_MATCH, lexer.matchStat());
    }
    @Test
    public void test_7() throws Exception {
        JSONScanner lexer = new JSONScanner("\"value\":\"aa\"");
        long hashCode = lexer.scanFieldSymbol("\"value\":".toCharArray());
        Assert.assertEquals(0, hashCode);
        Assert.assertEquals(JSONScanner.NOT_MATCH, lexer.matchStat());
    }
    @Test
    public void test_8() throws Exception {
        JSONScanner lexer = new JSONScanner("\"value\": \"MINUTES\",");
        long hashCode = lexer.scanFieldSymbol("\"value\":".toCharArray());
        Assert.assertEquals(189130438399835214L, hashCode);
        Assert.assertEquals(JSONScanner.VALUE, lexer.matchStat());
    }
    @Test
    public void test_9() throws Exception {
        JSONScanner lexer = new JSONScanner("\"value\":\"MINUTES\",");
        long hashCode = lexer.scanFieldSymbol("\"value\":".toCharArray());
        Assert.assertEquals(189130438399835214L, hashCode);
        Assert.assertEquals(JSONScanner.VALUE, lexer.matchStat());
    }
    @Test
    public void test_10() throws Exception {
        JSONScanner lexer = new JSONScanner("      \"value\":\"MINUTES\",");
        long hashCode = lexer.scanFieldSymbol("\"value\":".toCharArray());
        Assert.assertEquals(189130438399835214L, hashCode);
        Assert.assertEquals(JSONScanner.VALUE, lexer.matchStat());
    }
    @Test
    public void test_11() throws Exception {
        JSONScanner lexer = new JSONScanner("      \"value\":\"A\",");
        long hashCode = lexer.scanFieldSymbol("\"value\":".toCharArray());
        Assert.assertEquals(TypeUtils.fnv1a_64("A"), hashCode);
        Assert.assertEquals(JSONScanner.VALUE, lexer.matchStat());
    }

}
