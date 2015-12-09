package com.baidu.oped.sia.boot.exception;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by mason on 12/8/15.
 */
public class ExceptionArgsBuilderTest {

    @org.junit.Test
    public void testWith() throws Exception {
        Object[] args = ExceptionArgsBuilder.get().with("test").args();
        assertEquals(1, args.length);
        assertEquals("test", args[0]);

        args = ExceptionArgsBuilder.get().with("test", "tes").args();
        assertEquals(2, args.length);
        assertEquals("test", args[0]);
    }

    @org.junit.Test
    public void testAnd() throws Exception {
        Object[] args = ExceptionArgsBuilder.get().and("test").args();
        assertEquals(1, args.length);
        assertEquals("test", args[0]);

        args = ExceptionArgsBuilder.get().and("test", "test").args();
        assertEquals(1, args.length);
        assertEquals("test and test", args[0]);

        args = ExceptionArgsBuilder.get().and("test", "test", "test").args();
        assertEquals(1, args.length);
        assertEquals("test, test and test", args[0]);
    }

    @org.junit.Test
    public void testAnd2() throws Exception {
        Object[] args = ExceptionArgsBuilder.get().and(Arrays.asList("test")).args();
        assertEquals(1, args.length);
        assertEquals("test", args[0]);

        args = ExceptionArgsBuilder.get().and(Arrays.asList("test", "test")).args();
        assertEquals(1, args.length);
        assertEquals("test and test", args[0]);

        args = ExceptionArgsBuilder.get().and(Arrays.asList("test", "test", "test")).args();
        assertEquals(1, args.length);
        assertEquals("test, test and test", args[0]);
    }

    @org.junit.Test
    public void testOr() throws Exception {
        Object[] args = ExceptionArgsBuilder.get().or("test").args();
        assertEquals(1, args.length);
        assertEquals("test", args[0]);

        args = ExceptionArgsBuilder.get().or("test", "test").args();
        assertEquals(1, args.length);
        assertEquals("test or test", args[0]);

        args = ExceptionArgsBuilder.get().or("test", "test", "test").args();
        assertEquals(1, args.length);
        assertEquals("test, test or test", args[0]);
    }

    @org.junit.Test
    public void testOr2() throws Exception {
        Object[] args = ExceptionArgsBuilder.get().or(Arrays.asList("test")).args();
        assertEquals(1, args.length);
        assertEquals("test", args[0]);

        args = ExceptionArgsBuilder.get().or(Arrays.asList("test", "test")).args();
        assertEquals(1, args.length);
        assertEquals("test or test", args[0]);

        args = ExceptionArgsBuilder.get().or(Arrays.asList("test", "test", "test")).args();
        assertEquals(1, args.length);
        assertEquals("test, test or test", args[0]);
    }

    @org.junit.Test
    public void testRange() throws Exception {
        Object[] args = ExceptionArgsBuilder.get().range(null, null).args();
        assertEquals(1, args.length);
        assertEquals("(-∞, +∞)", args[0]);

        args = ExceptionArgsBuilder.get().range(null, 5).args();
        assertEquals(1, args.length);
        assertEquals("(-∞, 5]", args[0]);

        args = ExceptionArgsBuilder.get().range(4, null).args();
        assertEquals(1, args.length);
        assertEquals("[4, +∞)", args[0]);

        args = ExceptionArgsBuilder.get().range(4, 5).args();
        assertEquals(1, args.length);
        assertEquals("[4, 5]", args[0]);
    }


    @org.junit.Test
    public void testJoin() throws Exception {
        String join = ExceptionArgsBuilder.get().join("and", "test");
        assertEquals("test", join);

        join = ExceptionArgsBuilder.get().join("and", "test", "test");
        assertEquals("test and test", join);

        join = ExceptionArgsBuilder.get().join("and", "test", "test", "test");
        assertEquals("test, test and test", join);
    }

    @org.junit.Test
    public void testJoin2() throws Exception {
        String join = ExceptionArgsBuilder.get().join("and", Arrays.asList("test"));
        assertEquals("test", join);

        join = ExceptionArgsBuilder.get().join("and", Arrays.asList("test", "test"));
        assertEquals("test and test", join);

        join = ExceptionArgsBuilder.get().join("and", Arrays.asList("test", "test", "test"));
        assertEquals("test, test and test", join);
    }

    @org.junit.Test
    public void testArgs() throws Exception {
        Object[] args = ExceptionArgsBuilder.get().args();
        assertEquals(0, args.length);

        args = ExceptionArgsBuilder.get().and("test").args();
        assertEquals(1, args.length);
        assertEquals("test", args[0]);
    }


}