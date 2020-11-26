package com.itacademy.MongoDbJWT.controller;

import com.itacademy.MongoDbJWT.dto.pojo.GamePojo;
import com.itacademy.MongoDbJWT.dto.pojo.UserPojo;
import com.itacademy.MongoDbJWT.dto.response.GameResponse;
import com.itacademy.MongoDbJWT.dto.response.UserPlayerResponse;

import java.util.List;

public class Operations {
    public static boolean originalName(List<UserPlayerResponse> users, String name) {
        boolean test = true;
        if (!name.equalsIgnoreCase("anonymous")) {
            for (UserPlayerResponse user: users) {
                if (user.getName().equalsIgnoreCase(name)) {
                    test = false;
                }
            }
        }
        return test;
    }

    public static boolean isValid(UserPojo user) {
        boolean answer = true;
        if (user.getId() == null) {
            answer = false;
        }
        if (user.getName() == null) {
            answer = false;
        }
        return answer;
    }

    public static boolean isValidGame(GamePojo game) {
        boolean answer = true;
        if (game.getFirstDice() < 1 || game.getFirstDice() > 6) {
            answer = false;
        }
        if (game.getGameId() == null) {
            answer = false;
        }
        if (game.getSecondDice() < 1 || game.getSecondDice() > 6) {
            answer = false;
        }
        return answer;
    }

    public static String getResult(GamePojo game) {
        if (game.getFirstDice()+game.getSecondDice() == 7) {
            return "WIN";
        } else {
            return "Result.";
        }
    }

    public static double updateWinRate(UserPlayerResponse user) {
        List<GameResponse> games = user.getGameList();
        double wins = 0.0;
        double loses = 0.0;
        for (GameResponse game : games) {
            if (game.getResult().equalsIgnoreCase("win")){
                wins += 1.0;
            } else {
                loses += 1.0;
            }
        }
        return (wins*100)/(wins+loses);
    }
}
