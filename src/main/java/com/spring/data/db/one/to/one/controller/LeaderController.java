package com.spring.data.db.one.to.one.controller;

import com.spring.data.db.one.to.one.entities.Klass;
import com.spring.data.db.one.to.one.entities.Leader;
import com.spring.data.db.one.to.one.repositories.LeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/jpa/v4/leaders")
public class LeaderController {

    private LeaderRepository leaderRepository;

    public LeaderController() {
    }

    @Autowired
    public LeaderController(LeaderRepository leaderRepository) {
        this.leaderRepository = leaderRepository;
    }

    public LeaderRepository getLeaderRepository() {
        return leaderRepository;
    }

    public void setLeaderRepository(LeaderRepository leaderRepository) {
        this.leaderRepository = leaderRepository;
    }

    @PostMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Leader> save(@RequestBody Leader leader){
        Leader ldr=leaderRepository.save(leader);
        return new ResponseEntity<Leader>(ldr,HttpStatus.OK);
    }
}
