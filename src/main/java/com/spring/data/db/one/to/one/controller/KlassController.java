package com.spring.data.db.one.to.one.controller;

import com.spring.data.db.one.to.one.entities.Klass;
import com.spring.data.db.one.to.one.repositories.KlassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Klass>> findAll(){
        List<Klass> klasses=klassRepository.findAll();
        return new ResponseEntity<List<Klass>>(klasses,HttpStatus.OK);
    }

    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Klass> findById(@PathVariable("id") Long id){

        Optional<Klass> byId = klassRepository.findById(id);
        if (byId.isPresent()){
            Klass klass=byId.get();
            return new ResponseEntity<Klass>(klass,HttpStatus.OK);
        }else {
            return new ResponseEntity<Klass>(HttpStatus.NOT_FOUND);
        }
    }
}
