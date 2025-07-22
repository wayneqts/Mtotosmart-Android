package com.app.mtotosmart.model;

public class Profile {
    boolean isGudo, isLowAge, isMale;
    String name;

    public Profile(boolean isGudo, boolean isLowAge, boolean isMale, String name) {
        this.isGudo = isGudo;
        this.isLowAge = isLowAge;
        this.isMale = isMale;
        this.name = name;
    }

    public boolean isGudo() {
        return isGudo;
    }

    public void setGudo(boolean gudo) {
        isGudo = gudo;
    }

    public boolean isLowAge() {
        return isLowAge;
    }

    public void setLowAge(boolean lowAge) {
        isLowAge = lowAge;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
