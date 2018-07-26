package com.spring.data.db.one.to.one.controller;

import com.spring.data.db.one.to.one.entities.Klass;
import com.spring.data.db.one.to.one.repositories.KlassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/jpa/v3/classes")
public class KlassController {

    private KlassRepository klassRepository;

    public KlassController() {
    }

    @Autowired
    public KlassController(KlassRepository klassRepository) {
        this.klassRepository = klassRepository;
    }

    public KlassRepository getKlassRepository() {
        return klassRepository;
    }

    public void setKlassRepository(KlassRepository klassRepository) {
        this.klassRepository = klassRepository;
    }

    @PostMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Klass> save(@RequestBody Klass klass){
        Klass klz=klassRepository.save(klass);
        klz.getLeader().setKlass(klz);
        return new ResponseEntity<Klass>(klz,HttpStatus.OK);
    }
}
