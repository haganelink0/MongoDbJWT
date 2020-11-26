package com.itacademy.MongoDbJWT.dto.response;

import com.itacademy.MongoDbJWT.dto.Game;
import org.springframework.data.annotation.Id;

public class GameResponse implements Game {

    private String gameId;
    private Integer firstDice;
    private Integer secondDice;
    private String result;

    public GameResponse() {
    }

    public GameResponse(String gameId, Integer firstDice, Integer secondDice, String result) {
        this.gameId = gameId;
        this.firstDice = firstDice;
        this.secondDice = secondDice;
        this.result = result;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Integer getFirstDice() {
        return firstDice;
    }

    public void setFirstDice(Integer firstDice) {
        this.firstDice = firstDice;
    }

    public Integer getSecondDice() {
        return secondDice;
    }

    public void setSecondDice(Integer secondDice) {
        this.secondDice = secondDice;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
