import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Automerk;
import model.Parkeerplaats;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import resources.EchoService;
import resources.ParkeerplaatsService;

import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestParkeerplaatsService {
    EchoService echoService = new EchoService();
    ParkeerplaatsService parkeerplaatsService = new ParkeerplaatsService();
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    String kenteken = "ab-12-34";
    String merk = "BMW";

    @Test
    public void t1_Echo(){
        String message = "hello";
        assertTrue(echoService.echoMessage(message).contains(message));
    }

    @Test
    public void t2_getParkeerplaats(){//kijk of totaal =  bezet + vrij
        Parkeerplaats p = gson.fromJson(parkeerplaatsService.getParkeerplaats(), Parkeerplaats.class);
        assertTrue(p.getTotaal() == (p.getBezet() + p.getVrij()));
    }

    @Test()
    public void t3_testFoutMerk(){
        String merk = "abc";
        assertTrue(parkeerplaatsService.nieuweAuto(kenteken, merk).contains(merk + " is geen geldig automerk"));
    }

    @Test
    public void t4_testFoutKenteken(){
        String kenteken = "123-ab-1";
        assertTrue(parkeerplaatsService.nieuweAuto(kenteken, Automerk.values()[0].name()).contains("Kenteken " + kenteken + " is geen geldig kenteken"));
    }

    @Test
    public void t5_testNieuweAuto(){
        assertTrue(parkeerplaatsService.nieuweAuto(kenteken, merk).contains("Nieuwe auto toegevoegd."));
    }

    @Test
    public void t6_testNieuweAutoZelfdeKenteken(){
        assertTrue(parkeerplaatsService.nieuweAuto(kenteken, merk).contains("Auto met kenteken " + kenteken + " staat al geparkeerd op deze parkeerplaats."));
    }

    @Test
    public void t7_testGetGeparkeerdeAutos(){
        String response = parkeerplaatsService.getGeparkeerdeAutos();
        assertTrue(response.contains(kenteken) && response.contains(merk));
    }

    @Test
    public void t8_testVerwijderAutoVerkeerdKenteken(){
        String kentekentemp = "12-ab-12";
        assertTrue(parkeerplaatsService.verwijderAuto(kentekentemp).contains("Er is geen auto geparkeerd met het kenteken " + kentekentemp));
    }

    @Test
    public void t9_verwijderAuto(){
        assertTrue(parkeerplaatsService.verwijderAuto(kenteken).contains("Auto met kenteken " + kenteken + " heeft de parkeerplaats verlaten."));
    }
}
