package com.itacademy.MongoDbJWT.dto.pojo;

public class GamePojo {

    private String gameId;
    private Integer firstDice;
    private Integer secondDice;


    public GamePojo() {
    }

    public GamePojo(String gameId, Integer firstDice, Integer secondDice) {
        this.gameId = gameId;
        this.firstDice = firstDice;
        this.secondDice = secondDice;

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
}
