package com.staskaledin.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.staskaledin.main.Game;
import com.staskaledin.main.GameRunner;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by stas on 06.02.15.
 */

@Path("/api/do_game")
public class DoGame {


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response Calculate(String body) throws Exception{
        GameRunner gr = getGameRunner(body);

        Response response = Response
                .status(200)
                .entity(gr.run().getJsonStat().toString())
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "*")
                .build();

        return response;

    }

    public GameRunner getGameRunner(String body) throws JSONException {
        JSONObject data = new JSONObject(body);

        int countCooperate = data.getInt("cooperate");
        int countSwear = data.getInt("swear");
        int countRandom = data.getInt("random");
        float cooperateProb = (float) data.getDouble("probability");
        int countGoodV = data.getInt("goodv");
        int countWithMemory = data.getInt("withmemory");
        int countEpochs = data.getInt("epochs");


        Game game = new Game.Builder()
                .setCountCooperate(countCooperate)
                .setCountSwear(countSwear)
                .setCountRandom(countRandom)
                .setCooperateProbability(cooperateProb)
                .setCountGoodV(countGoodV)
                .setCountWithMemory(countWithMemory)
                .build();

        return new GameRunner(game, countEpochs);
    }
}
