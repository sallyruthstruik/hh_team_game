package com.staskaledin.test;

import com.staskaledin.api.DoGame;
import com.staskaledin.main.GameRunner;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

public class DoGameTest {

    private DoGame game;
    private String body;

    @Before
    public void setUp() throws Exception {
        game = new DoGame();
        body = "{\"cooperate\": 1, \"swear\": 1, \"random\": 1, \"probability\": 1, \"goodv\": 1, \"withmemory\": 1, \"epochs\": 2}";
    }

    @Test
    public void testCalculate() throws Exception {

        Response response = game.Calculate(body);

        MultivaluedMap<String, Object> headers = response.getHeaders();

        assertEquals("*", headers.getFirst("Access-Control-Allow-Origin"));
        assertEquals("*", headers.getFirst("Access-Control-Allow-Headers"));
    }

    @Test
    public void testGetGameRunner() throws Exception{
        GameRunner gr = game.getGameRunner(body);

        gr.run();

        JSONObject out = gr.getJsonStat();

        JSONArray summary = out.getJSONArray("summary");
        assertEquals(summary.getInt(0), 12);
        assertEquals(summary.getInt(1), 20);
    }
}