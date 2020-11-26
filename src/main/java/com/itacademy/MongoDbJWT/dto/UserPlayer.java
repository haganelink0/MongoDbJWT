package com.itacademy.MongoDbJWT.dto;

import com.itacademy.MongoDbJWT.dto.response.GameResponse;

public interface UserPlayer {

    public void addGame(GameResponse game);

    public void deleteGame(GameResponse game);
}
