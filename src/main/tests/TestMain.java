import model.Automerk;

import java.util.Arrays;

public class TestMain {
    /*public static void main(String[] args) {
        String kenteken = "ABC-12-A";
        System.out.println(
                kenteken.matches("\\S\\S-\\d\\d-\\d\\d|\\d\\d-\\d\\d-\\S\\S|\\d\\d-\\S\\S-\\d\\d|\\S\\S-\\S\\S-\\d\\d|\\d\\d-\\S\\S-\\S\\S|\\d\\d-\\S\\S\\S-\\d|\\d-\\S\\S\\S-\\d\\d|\\S\\S-\\d\\d\\d-\\S|\\S-\\d\\d\\d-\\S\\S|\\S\\S\\S-\\d\\d-\\S")
        );
        String message = "";
        for(Automerk merk : Automerk.values()){
            message += merk.toString() + ", ";
        }

        System.out.println(message.substring(0, message.length() - 2));
    }*/

    public static void main(String[] args) {
        System.out.println(Automerk.getAutoMerk("honda"));

    }
}
