package ru.elagin.hibernate.response;

import ru.elagin.hibernate.models.Auto;

import java.util.List;

public class AutoResponse {

    private String error;
    private Auto auto;
    private List<Auto> autoList;


    public List<Auto> getAutoList() {
        return autoList;
    }

    public void setAutoList(List<Auto> autoList) {
        this.autoList = autoList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }
}