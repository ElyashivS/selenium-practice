import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class JsonFile {
    public static void readJsonFile() {

        Object jp;
        try {
            jp = new JSONParser().parse(new FileReader("src/config/config.json"));
            JSONObject jo = (JSONObject) jp;
            String user = (String) jo.get("username");
            String pass = (String) jo.get("password");
            System.out.println(user);
            System.out.println(pass);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        readJsonFile();
    }
}
