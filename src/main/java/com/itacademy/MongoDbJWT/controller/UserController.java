package com.itacademy.MongoDbJWT.controller;

import com.itacademy.MongoDbJWT.controller.exceptions.*;
import com.itacademy.MongoDbJWT.dto.pojo.GamePojo;
import com.itacademy.MongoDbJWT.dto.pojo.UserPojo;
import com.itacademy.MongoDbJWT.dto.response.GameResponse;
import com.itacademy.MongoDbJWT.dto.response.UserPlayerResponse;
import com.itacademy.MongoDbJWT.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class UserController {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserRepository userRepository;

    public UserController(UserRepository userRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }



    @PostMapping("/players/{id}/games")
    public ResponseEntity<GameResponse> playGame(@PathVariable(value = "id") String id,
                                                 @RequestBody GamePojo game)
                                                                throws UserNotFound, InvalidJSON {
        if (!Operations.isValidGame(game)) {
            throw new InvalidJSON("Game not valid");
        }
        GameResponse newGame = new GameResponse();
        newGame.setFirstDice(game.getFirstDice());
        newGame.setSecondDice(game.getSecondDice());
        newGame.setGameId(game.getGameId());
        newGame.setResult(Operations.getResult(game));
        if (userRepository.findById(id).isEmpty()) {
            throw new UserNotFound("User not found");
        }
        UserPlayerResponse user = userRepository.findById(id).get();
        user.addGame(newGame);
        userRepository.save(user);

        return new ResponseEntity<>(newGame, HttpStatus.OK);

    }

    @DeleteMapping("/players/{id}")
    public HttpStatus deleteGameHistory(@PathVariable(value = "id") String id)
            throws UserNotFound, HistoryException {
        Optional<UserPlayerResponse> userResponse = userRepository.findById(id);
        if (userResponse.isEmpty()) {
            throw new UserNotFound("user with id: " + id + " not found.");
        }
        UserPlayerResponse user = userResponse.get();
        if (user.getGameList().isEmpty()) {
            throw new HistoryException();
        }
        user.getGameList().removeAll(user.getGameList());
        return  HttpStatus.OK;
    }

    @GetMapping("/players/{id}/games")

    public ResponseEntity<List<GameResponse>> seeGameHistory(@PathVariable(value = "id") String id)
            throws UserNotFound {
        if (userRepository.findById(id).isEmpty()) {
            throw new UserNotFound("user with id: " + id + " not found.");
        }
        return new ResponseEntity<>(userRepository.findById(id).get().getGameList(), HttpStatus.OK);
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<UserPlayerResponse> getUserById(@PathVariable(value = "id") String id) throws UserNotFound{
        Optional<UserPlayerResponse> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFound("User not found");
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);

    }

    @PostMapping("/players")
    public ResponseEntity<UserPlayerResponse> saveUser(@RequestBody UserPojo user) throws InvalidName, InvalidJSON {
        if (!Operations.isValid(user)) {
            throw new InvalidJSON();
        }
        UserPlayerResponse userDTO = new UserPlayerResponse();
        if (Operations.originalName(userRepository.findAll(), user.getName())) {
            userDTO.setName(user.getName());
            userDTO.setId(user.getId());
            userDTO.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userDTO.setRegistrationDate(Date.from(Instant.now()));
            userDTO.setGameList(new ArrayList<>());
            userDTO.setWinrate(0.0);

            userRepository.save(userDTO);
        } else {
            throw new InvalidName();
        }
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping("players/{id}")
    public ResponseEntity<UserPlayerResponse> changeName(@PathVariable(value = "id") String id,
                                                         @RequestParam(value = "newName") String newName)
            throws UserNotFound, InvalidName {
        Optional<UserPlayerResponse> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFound("user not found");
        }
        if (!Operations.originalName(userRepository.findAll(), newName)) {
            throw new InvalidName();
        }
        UserPlayerResponse updatedUser = new UserPlayerResponse();
        updatedUser.setId(user.get().getId());
        updatedUser.setName(newName);
        updatedUser.setGameList(user.get().getGameList());
        updatedUser.setWinrate(user.get().getWinrate());
        updatedUser.setRegistrationDate(user.get().getRegistrationDate());

        userRepository.save(updatedUser);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    @GetMapping("/players")
    public ResponseEntity<List<UserPlayerResponse>> seeAllUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/players/ranking")
    public Double getAverageWinrate() {
        Double result = 0.0;
        List<UserPlayerResponse> players = userRepository.findAll();
        for (UserPlayerResponse player : players) {
            result += player.getWinrate();
        }
        return result/players.size();
    }

    @GetMapping("/players/ranking/loser")
    public ResponseEntity<UserPlayerResponse> getWorsePlayer() {
        UserPlayerResponse user = new UserPlayerResponse();
        user.setWinrate(100.0);
        List<UserPlayerResponse> players = userRepository.findAll();
        for (UserPlayerResponse player : players) {
            if (player.getWinrate() < user.getWinrate()) {
                user.setWinrate(player.getWinrate());
                user.setGameList(player.getGameList());
                user.setRegistrationDate(player.getRegistrationDate());
                user.setName(player.getName());
                user.setId(player.getId());
            }
        }
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/players/ranking/winner")
    public ResponseEntity<UserPlayerResponse> getBestPlayer() {
        UserPlayerResponse user = new UserPlayerResponse();
        user.setWinrate(0.0);
        List<UserPlayerResponse> players = userRepository.findAll();
        for (UserPlayerResponse player : players) {
            if (player.getWinrate() > user.getWinrate()) {
                user.setWinrate(player.getWinrate());
                user.setGameList(player.getGameList());
                user.setRegistrationDate(player.getRegistrationDate());
                user.setName(player.getName());
                user.setId(player.getId());
            }
        }
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}
