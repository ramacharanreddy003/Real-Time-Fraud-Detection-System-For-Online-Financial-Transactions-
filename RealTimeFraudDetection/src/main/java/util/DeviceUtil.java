package util;

import jakarta.servlet.http.HttpServletRequest;

public class DeviceUtil {

    public static String getDeviceType(HttpServletRequest request) {

        String agent = request.getHeader("User-Agent");
        if (agent == null) return "UNKNOWN";

        agent = agent.toLowerCase();

        if (agent.contains("mobile")) return "MOBILE";
        if (agent.contains("tablet")) return "TABLET";
        return "DESKTOP";
    }
}
