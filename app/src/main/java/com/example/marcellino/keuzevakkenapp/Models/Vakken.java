package com.example.marcellino.keuzevakkenapp.Models;

public class Vakken {

    private Object naam;
    private Object moduleLeider;
    private Object richting;
    private Object id;
    private Object EC;
    private Object periode;
    private Object plekken;

    public Vakken(Object naam, Object moduleLeider, Object richting, Object id, Object EC, Object periode, Object plekken){
        this.naam = naam;
        this.moduleLeider= moduleLeider;
        this.richting = richting;
        this.id = id;
        this.EC = EC;
        this.periode = periode;
        this.plekken = plekken;
    }

    public Object getNaam() {
        return naam;
    }

    public Object getModuleLeider() {
        return moduleLeider;
    }

    public Object getRichting() {
        return richting;
    }

    public Object getId() {
        return id;
    }

    public Object getEC() {
        return EC;
    }

    public Object getPeriode() {
        return periode;
    }

    public Object getPlekken() {
        return plekken;
    }

    public String toString(){
        return this.getNaam() + " " + this.getEC() + " " + this.getId() + " " + this.getModuleLeider() + " " + this.getPeriode() + " " + this.getPlekken() + " " + this.getRichting();
    }
}
