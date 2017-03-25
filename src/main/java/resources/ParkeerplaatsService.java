package resources;

import model.Auto;
import model.Automerk;
import model.JsonConverter;
import model.Parkeerplaats;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Arrays;
import java.util.Date;

@Path("/parkeerplaats")
public class ParkeerplaatsService {
    @GET
    @Produces("application/json")
    public String getParkeerplaats(){
        JsonObjectBuilder job = Json.createObjectBuilder();
        Parkeerplaats parkeerplaats = JsonConverter.getParkeerplaats();
        job.add("Totaal" , parkeerplaats.getTotaal());
        job.add("Bezet" , parkeerplaats.getBezet());
        job.add("Vrij" , parkeerplaats.getVrij());
        return job.build().toString();
    }

    @GET
    @Path("/nieuweAuto/{kenteken}/{automerk}")
    @Produces("application/json")
    public String nieuweAuto(@PathParam("kenteken") String kenteken, @PathParam("automerk")String automerk){
        JsonObjectBuilder job = Json.createObjectBuilder();
        Automerk merk = Automerk.getAutoMerk(automerk);
        if(merk == null) {
            String message = automerk + " is geen geldig automerk. Geldige automerken zijn:";
            for (Automerk amerk : Automerk.values()) {
                message += amerk.toString() + ", ";
            }
            job.add("message", message.substring(0, message.length() - 2));
            return job.build().toString();
        }
        Auto nieuweAuto = new Auto(kenteken, new Date(), merk);
        String message = null;
        try {
            JsonConverter.getParkeerplaats().voegAutoToe(nieuweAuto);
            message = "Nieuwe auto toegevoegd.";
        } catch (Exception e) {
            message = e.getMessage();
        }
        job.add("message", message);
        return job.build().toString();
    }

    @GET
    @Path("/getGeparkeerdeAutos")
    @Produces("application/json")
    public String getGeparkeerdeAutos(){
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for(Auto auto : JsonConverter.getParkeerplaats().getAutos()){
            JsonObjectBuilder job = Json.createObjectBuilder();
            job.add("kenteken", auto.getKenteken());
            job.add("merk", auto.getMerk().toString());
            Date date = auto.getAankomst();
            job.add("aankomst", date == null ? "" : date.toString());
            date = auto.getVertrek();
            job.add("vertrek", date == null ? "" : date.toString());
            jab.add(job);
        }
        return jab.build().toString();
    }


    @GET
    @Path("/verwijderAuto/{kenteken}")
    @Produces("application/json")
    public String verwijderAuto(@PathParam("kenteken") String kenteken){
        JsonObjectBuilder job = Json.createObjectBuilder();
        String message = null;
        try {
            JsonConverter.getParkeerplaats().verwijderAuto(kenteken);
            message = "Auto met kenteken " + kenteken + " heeft de parkeerplaats verlaten.";
        } catch (Exception e) {
            message = e.getMessage();
        }
        job.add("message", message);
        return job.build().toString();
    }

}
