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


    @Override
    public String toString() {
        return "Specialisation{" +
                "id=" + id +
                ", en='" + en + '\'' +
                ", ar='" + ar + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Specialisation that = (Specialisation) o;
        return id.equals(that.id)||ar.equals(that.ar)||en.equals(that.en);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + en.hashCode();
        result = 31 * result + ar.hashCode();
        return result;
    }
}
