package com.example.marcellino.keuzevakkenapp.Models;

public class Vak {

    private String Afkorting;
    private String Moduleleider;
    private String Naam;
    private String Richting;
    private String Periode;
    private int EC;
    private int ID;
    private int Plaatsen;

    public Vak(){

    }

    public Vak(String afkorting, String moduleleider, String naam, String richting, String periode, int ec, int id, int plaatsen){

        Afkorting = afkorting;
        Moduleleider = moduleleider;
        Naam = naam;
        Richting = richting;
        Periode = periode;
        EC = ec;
        ID = id;
        Plaatsen = plaatsen;
    }

    public String getAfkorting() {
        return Afkorting;
    }

    public String getModuleleider() {
        return Moduleleider;
    }

    public String getNaam() {
        return Naam;
    }

    public String getRichting() {
        return Richting;
    }

    public String getPeriode() {
        return Periode;
    }

    public int getEC() {
        return EC;
    }

    public int getID() {
        return ID;
    }

    public int getPlaatsen() {
        return Plaatsen;
    }



}
