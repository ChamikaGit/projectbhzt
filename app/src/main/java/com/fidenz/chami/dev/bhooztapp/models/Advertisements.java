package com.fidenz.chami.dev.bhooztapp.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by fidenz on 6/21/18.
 */


@DatabaseTable(tableName = "advertistmnets")
public class Advertisements {

    @DatabaseField(columnName ="id",generatedId = true)
    private int id;

    @DatabaseField(columnName ="Ad_header")
    private String Adheader;

    @DatabaseField(columnName ="Ad_body")
    private String Adbody;

    @DatabaseField(columnName ="Ad_linkname")
    private String Adlinkname;

    @DatabaseField(columnName ="Ad_link")
    private String Adlink;




    public Advertisements() {
    }

    public Advertisements(int id, String adheader, String adbody, String adlinkname, String adlink) {
        this.id = id;
        this.Adheader = adheader;
        this.Adbody = adbody;
        this.Adlinkname=adlinkname;
        this.Adlink = adlink;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdheader() {
        return Adheader;
    }

    public void setAdheader(String adheader) {
        Adheader = adheader;
    }

    public String getAdbody() {
        return Adbody;
    }

    public void setAdbody(String adbody) {
        Adbody = adbody;
    }

    public String getAdlink() {
        return Adlink;
    }

    public void setAdlink(String adlink) {
        Adlink = adlink;
    }

    public String getAdlinkname() {return Adlinkname;}

    public void setAdlinkname(String adlinkname) {Adlinkname = adlinkname;}
    //    @Override
//    public String toString() {
//        return "Advertisements{" +
//                "id=" + id +
//                ", Adheader='" + Adheader + '\'' +
//                ", Adbody='" + Adbody + '\'' +
//                ", Adlink='" + Adlink + '\'' +
//                '}';
//    }
}
