package com.graduationproject.zakerly.core.models;

import io.realm.RealmObject;

public class Specialisation extends RealmObject {

    private Long id;
    private String en;
    private String ar;


    public Specialisation() {
    }

    public Specialisation(Long id, String en, String ar) {
        this.id = id;
        this.en = en;
        this.ar = ar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getAr() {
        return ar;
    }

    public void setAr(String ar) {
        this.ar = ar;
    }
}
