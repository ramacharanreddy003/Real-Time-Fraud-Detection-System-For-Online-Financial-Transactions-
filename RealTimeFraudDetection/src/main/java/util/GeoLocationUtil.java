package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GeoLocationUtil {

    public static String getLocationFromLatLng(String lat, String lng) {

        try {
            String urlStr =
                "https://nominatim.openstreetmap.org/reverse"
                + "?format=json"
                + "&lat=" + lat
                + "&lon=" + lng
                + "&zoom=10";

            URL url = new URL(urlStr);
            HttpURLConnection con =
                (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.setRequestProperty(
                "User-Agent",
                "FraudDetectionProject/1.0");

            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(con.getInputStream()));

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            String json = response.toString();

            // 🔎 VERY SIMPLE SAFE EXTRACTION
            String city = extract(json, "\"city\":\"", "\"");
            if (city == null)
                city = extract(json, "\"town\":\"", "\"");
            if (city == null)
                city = extract(json, "\"village\":\"", "\"");

            String state = extract(json, "\"state\":\"", "\"");
            String country = extract(json, "\"country\":\"", "\"");

            if (city == null) city = "Puttur";
            if (state == null) state = "Unknown";
            if (country == null) country = "India";

            return city + ", " + state + ", " + country;

        } catch (Exception e) {
            // 🚨 NEVER crash transaction
            return "India (location unavailable)";
        }
    }

    private static String extract(String text, String start, String end) {
        int s = text.indexOf(start);
        if (s == -1) return null;
        s += start.length();
        int e = text.indexOf(end, s);
        if (e == -1) return null;
        return text.substring(s, e);
    }

    /* IP fallback */
    public static String getLocationFromIP(String ip) {
        if (ip == null ||
            ip.equals("127.0.0.1") ||
            ip.equals("0:0:0:0:0:0:0:1")) {
            return "Hyderabad, Telangana, India";
        }
        return "India (IP based)";
    }
}
