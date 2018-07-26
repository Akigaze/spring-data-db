package com.spring.data.db.one.to.one.entities;

import javax.persistence.*;

@Entity
@Table(name = "tb_leader")
public class Leader {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String gender;
    @OneToOne(fetch = FetchType.LAZY,mappedBy = "class_id")
    private Klass klass;

    public Leader() {
    }

    public Leader(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Klass getKlass() {
        return klass;
    }

    public void setKlass(Klass klass) {
        this.klass = klass;
    }
}
