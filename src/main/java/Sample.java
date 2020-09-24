import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Sample {
    public static void main(String[] args) {
        System.out.println(Objects.requireNonNull(RestFactory.get()).get("url"));
        System.out.println(Objects.requireNonNull(RestFactory.post()).get("url"));
        System.out.println(Objects.requireNonNull(RestFactory.put()).get("url"));
        System.out.println(Objects.requireNonNull(RestFactory.delete()).get("url"));
        System.out.println(Objects.requireNonNull(RestFactory.patch()).get("url"));
    }
}

class RestFactory{
    private final static String baseUrl = "https://httpbin.org";

    public static JSONObject get(){
        String uri = baseUrl + "/get";

        try{
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-Auth-Token", "temp");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return new JSONObject(response.toString());
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static JSONObject post(){
        String uri = baseUrl + "/post";

        try{
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("X-Auth-Token", "temp");
            conn.setDoOutput(true);

            String parameters = "";
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(parameters);
            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return new JSONObject(response.toString());
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static JSONObject put(){
        String uri = baseUrl + "/put";

        try{
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("PUT");
            conn.setRequestProperty("X-Auth-Token", "temp");
            conn.setDoOutput(true);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return new JSONObject(response.toString());
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static JSONObject delete(){
        String uri = baseUrl + "/delete";

        try{
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("X-Auth-Token", "temp");
            conn.setDoOutput(true);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return new JSONObject(response.toString());
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static JSONObject patch(){
        String uri = baseUrl + "/patch";

        allowMethods("PATCH");
        try{
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("PATCH");
            conn.setRequestProperty("X-Auth-Token", "temp");
            conn.setDoOutput(true);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return new JSONObject(response.toString());
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    private static void allowMethods(String... methods) {
        try {
            Field methodsField = HttpURLConnection.class.getDeclaredField("methods");

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

            methodsField.setAccessible(true);

            String[] oldMethods = (String[]) methodsField.get(null);
            Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
            methodsSet.addAll(Arrays.asList(methods));
            String[] newMethods = methodsSet.toArray(new String[0]);

            methodsField.set(null/*static field*/, newMethods);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
