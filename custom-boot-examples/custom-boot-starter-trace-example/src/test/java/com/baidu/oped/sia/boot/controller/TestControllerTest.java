package com.baidu.oped.sia.boot.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.baidu.oped.sia.boot.Application;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Calendar;
import java.util.UUID;

/**
 * Created by mason on 11/29/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(value = {Application.class})
@WebAppConfiguration
public class TestControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @org.junit.Test
    public void testHome() throws Exception {
        String traceId = UUID.randomUUID().toString();
        this.mockMvc.perform(get("/home").header("X-IGITRAS-TRACE-ID", traceId)).andExpect(status().isOk())
                .andExpect(header().string("X-IGITRAS-TRACE-ID", traceId));

        long traceTimestamp = Calendar.getInstance().getTimeInMillis();
        this.mockMvc.perform(
                get("/home").header("X-IGITRAS-TRACE-ID", traceId).header("X-IGITRAS-TRACE-TIMESTAMP", traceTimestamp)
                        .header("X-IGITRAS-TRACE-SOURCE-SEQ", 3)).andDo(print()).andExpect(status().isOk())
                .andExpect(header().string("X-IGITRAS-TRACE-ID", traceId))
                .andExpect(header().longValue("X-IGITRAS-TRACE-TIMESTAMP", traceTimestamp))
                .andExpect(header().string("X-IGITRAS-TRACE-SOURCE-IP", "0:0:0:0:0:0:0:1"))
                .andExpect(header().longValue("X-IGITRAS-TRACE-SOURCE-SEQ", 3));
    }
}