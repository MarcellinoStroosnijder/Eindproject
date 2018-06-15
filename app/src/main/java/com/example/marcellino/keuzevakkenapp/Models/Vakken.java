package com.example.marcellino.keuzevakkenapp.Models;

import android.os.Parcel;
import android.os.Parcelable;


public class Vakken implements Parcelable{

         public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
                public Vakken createFromParcel(Parcel in) {
                    return new Vakken(in);
                }

                public Vakken[] newArray(int size) {
                    return new Vakken[size];
                }
         };

    public Vakken(Parcel in){
        this.naam = in.readString();
        this.moduleLeider= in.readString();
        this.richting = in.readString();
        this.id = in.readInt();
        this.EC = in.readInt();
        this.periode = in.readInt();
        this.plekken = in.readInt();
        this.afkorting = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id.toString());
        dest.writeString(this.naam.toString());
        dest.writeString(this.moduleLeider.toString());
        dest.writeString(this.richting.toString());
        dest.writeString(this.id.toString());
        dest.writeString(this.EC.toString());
        dest.writeString(this.periode.toString());
        dest.writeString(this.plekken.toString());
        dest.writeString(this.afkorting.toString());
    }

    private Object naam;
    private Object moduleLeider;
    private Object richting;
    private Object id;
    private Object EC;
    private Object periode;
    private Object plekken;
    private Object afkorting;


    public Vakken(Object naam, Object moduleLeider, Object richting, Object id, Object EC, Object periode, Object plekken, Object afkorting){
        this.naam = naam;
        this.moduleLeider= moduleLeider;
        this.richting = richting;
        this.id = id;
        this.EC = EC;
        this.periode = periode;
        this.plekken = plekken;
        this.afkorting = afkorting;
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

    public Object getAfkorting() {
        return afkorting;
    }

    public String toString(){
        return this.getNaam() + " " + this.getAfkorting() + " " + this.getEC() + " " + this.getId() + " " + this.getModuleLeider() + " " + this.getPeriode() + " " + this.getPlekken() + " " + this.getRichting();
    }


}
