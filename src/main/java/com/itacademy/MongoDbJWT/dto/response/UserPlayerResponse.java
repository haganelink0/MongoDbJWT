package com.itacademy.MongoDbJWT.dto.response;


import com.itacademy.MongoDbJWT.dto.UserPlayer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "users")
public class UserPlayerResponse implements UserPlayer {

    @Id
    private String id;
    private String name;
    private String password;
    private Date registrationDate;
    private List<GameResponse> gameList;
    private Double winrate;

    public UserPlayerResponse() {
    }

    public UserPlayerResponse(String id, String name, String password, Date registrationDate, List<GameResponse> gameList, Double winrate) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.registrationDate = registrationDate;
        this.gameList = gameList;
        this.winrate = winrate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<GameResponse> getGameList() {
        return gameList;
    }

    public void setGameList(List<GameResponse> gameList) {
        this.gameList = gameList;
    }

    public Double getWinrate() {
        return winrate;
    }

    public void setWinrate(Double winrate) {
        this.winrate = winrate;
    }

    @Override
    public void addGame(GameResponse game) {
        this.gameList.add(game);
    }

    @Override
    public void deleteGame(GameResponse game) {
        this.gameList.remove(game);

    }
}
