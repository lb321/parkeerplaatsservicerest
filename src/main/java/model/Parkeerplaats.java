package model;

import java.util.ArrayList;
import java.util.List;


public class Parkeerplaats {
    private int totaal;
    private int vrij;
    private int bezet;
    private List<Auto> autos; //auto's die geparkeerd zijn.
    private List<Auto> geschiedenis; //geschiedenis met auto's, dit werkt niet vanwege tijdgebrek

    public Parkeerplaats(){
        this(10,10,0);
    }

    public Parkeerplaats(int totaal, int vrij, int bezet) {
        this(totaal,vrij,bezet, new ArrayList<Auto>());
    }

    public Parkeerplaats(int totaal, int vrij, int bezet, List<Auto> autos) {
        this(totaal, vrij, bezet, autos, new ArrayList<Auto>());
    }

    public Parkeerplaats(int totaal, int vrij, int bezet, List<Auto> autos, List<Auto> geschiedenis) {
        this.totaal = totaal;
        this.vrij = vrij;
        this.bezet = bezet;
        this.autos = autos;
        this.geschiedenis = geschiedenis;
    }

    private void updateParkeerplaats(){
        vrij = totaal - autos.size();
        bezet = autos.size();
    }

    public int getTotaal() {
        return totaal;
    }

    public int getVrij() {
        return vrij;
    }

    public int getBezet() {
        return bezet;
    }

    public List<Auto> getAutos() {
        return autos;
    }

    private boolean checkKenteken(String kenteken) throws Exception {
        if (kenteken.matches("\\S\\S-\\d\\d-\\d\\d|\\d\\d-\\d\\d-\\S\\S|\\d\\d-\\S\\S-\\d\\d|\\S\\S-\\S\\S-\\d\\d|\\d\\d-\\S\\S-\\S\\S|\\d\\d-\\S\\S\\S-\\d|\\d-\\S\\S\\S-\\d\\d|\\S\\S-\\d\\d\\d-\\S|\\S-\\d\\d\\d-\\S\\S|\\S\\S\\S-\\d\\d-\\S")) {
            return true;
        }
        throw new Exception("Kenteken " + kenteken + " is geen geldig kenteken.");
    }

    public void voegAutoToe(Auto auto) throws Exception {
        if(bezet < totaal){
            checkKenteken(auto.getKenteken());
            for(Auto a : autos){
                if(a.getKenteken().equals(auto.getKenteken())){
                    throw new Exception("Auto met kenteken " + a.getKenteken() + " staat al geparkeerd op deze parkeerplaats.");
                }
            }
            autos.add(auto);
            updateParkeerplaats();
            JsonConverter.parkeerplaatsToJson(this);
        } else
            throw new Exception("De parkeerplaats is vol.");
    }

    public void verwijderAuto(String kenteken) throws Exception {
        if(bezet > 0){
            Auto auto = null;
            for(Auto a: autos){
                if(a.getKenteken().equals(kenteken)){
                    auto = a;
                    break;
                }
            }
            if(auto != null && checkKenteken(kenteken)) {
                autos.remove(auto);
                geschiedenis.add(auto);
                updateParkeerplaats();
                JsonConverter.parkeerplaatsToJson(this);
            } else
                throw new Exception("Er is geen auto geparkeerd met het kenteken " + kenteken);
        } else
            throw new Exception("Er zijn geen auto's op de parkeerplaats.");
    }

    @Override
    public String toString() {
        return "Parkeerplaats{" +
                "totaal=" + totaal +
                ", vrij=" + vrij +
                ", bezet=" + bezet +
                ", autos=" + autos +
                ", geschiedenis=" + geschiedenis +
                '}';
    }
}
