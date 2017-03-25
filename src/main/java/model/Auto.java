package model;

import java.util.Date;

public class Auto {
    private String kenteken;
    private Date aankomst;
    private Date vertrek;
    private Automerk merk;

    public Auto(String kenteken, Date aankomst, Automerk merk) {
        this.kenteken = kenteken;
        this.aankomst = aankomst;
        this.merk = merk;
    }

    public Auto(String kenteken, Date aankomst, Date vertrek, Automerk merk) {
        this.kenteken = kenteken;
        this.aankomst = aankomst;
        this.vertrek = vertrek;
        this.merk = merk;
    }

    public String getKenteken() {
        return kenteken;
    }

    public Date getAankomst() {
        return aankomst;
    }

    public Date getVertrek() {
        return vertrek;
    }

    public Automerk getMerk() {
        return merk;
    }
}
