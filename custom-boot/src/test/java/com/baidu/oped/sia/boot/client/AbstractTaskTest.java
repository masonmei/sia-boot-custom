package com.baidu.oped.sia.boot.client;

import com.baidu.oped.sia.boot.client.http.Person;
import com.baidu.oped.sia.boot.exception.internal.RetryableException;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by mason on 12/29/15.
 */
public class AbstractTaskTest {

    private AbstractTask abstractTask;

    @Before
    public void setUp() throws Exception {
        Context<Person> context = new AbstractTaskContext<Person>() {
        };
        abstractTask = new AbstractTask<Person>(context) {
            @Override
            protected Person realExecute() throws RetryableException {
                return null;
            }
        };
    }

    @Test
    public void testContext() throws Exception {

    }

    @Test
    public void testExecute() throws Exception {

    }
}