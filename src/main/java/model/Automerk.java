package model;

public enum Automerk {
    Audi,
    Volkswagen,
    Volvo,
    BMW,
    Mercedes,
    Toyota,
    Seat,
    Opel,
    Saab,
    Honda;

    public static Automerk getAutoMerk(String merk){
        for(Automerk automerk : Automerk.values()){
            if(automerk.name().toLowerCase().equals(merk.toLowerCase()))
                return automerk;
        }
        return null;
    }
}
