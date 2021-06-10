package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class Action {
    /**
     * Returns the name of the action, used to match the request in the map
     * @return the name of the action
     */
    public abstract String getName();

    /**
     * Returns the name of the view (usually a jsp) used to render the output.
     * @param request the request
     * @return the name of the view
     */
    public String performGet(HttpServletRequest request) {
        // Normally, we would return null to get a 404 error, but for easier debugging...
        request.setAttribute("message",
                "No implementation of performGet() for action " + getName());
        return "action-error-message.jsp";
    }

    /**
     * Returns the name of the view (usually a jsp) used to render the output.
     * @param request the request
     * @return the name of the view
     */
    public String performPost(HttpServletRequest request) {
        // Normally, we would return null to get a 404 error, but for easier debugging...
       request.setAttribute("message",
                "No implementation of performPost() for action " + getName());
        return "action-error-message.jsp";
    }

    /**
     * Class methods to manage dispatching to Actions
     */
    private static Map<String, Action> hash = new HashMap<>();

    /**
     * Adds an action to the map.
     * @param a the action to be added to the map.
     */
    public static void add(Action a) {
        synchronized (hash) {
            if (hash.get(a.getName()) != null) {
                throw new AssertionError("Two actions with the same name ("
                        + a.getName() + "): " + a.getClass().getName()
                        + " and " + hash.get(a.getName()).getClass().getName());
            }

            hash.put(a.getName(), a);
        }
    }

    /**
     * Looks the action up in the map and then executes it.
     * @param name the name of the action
     * @param request the request to process
     * @return the name of the view
     */
    public static String perform(String name, HttpServletRequest request) {
        Action a;
        synchronized (hash) {
            a = hash.get(name);
        }

        if (a == null) {
            // Normally, we would return null to get a 404 error, but for easier debugging...
            request.setAttribute("message", "No action class found for \"" + name + "\"");
            return "action-error-message.jsp";
        }
        
        if ("GET".equals(request.getMethod())) {
            setupCsrfToken(request);
            return a.performGet(request);
        }

        if ("POST".equals(request.getMethod())) {
            // if (validCsrfToken(request)) {
            return a.performPost(request);
            // }
            
            // // Normally, we would send back an error message, but for easier debugging...
            // request.setAttribute("message",
            //         "Missing or invalid CSRF token (with POST request) for \"" + name + "\"");
            // return "action-error-message.jsp";
        }
        
        // Normally, we would return null to get a 404 error, but for easier debugging...
        request.setAttribute("message",
                "Unexpected HTTP Method (\"" + request.getMethod() + "\") for \"" + name + "\"");
        return "action-error-message.jsp";
    }

    private static void setupCsrfToken(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sessionToken = (String) session.getAttribute("csrfToken");
        if (sessionToken != null) {
            return;
        }
        
        long longToken = ThreadLocalRandom.current().nextLong();
        String stringToken = Long.toHexString(longToken);

        session.setAttribute("csrfToken", stringToken);
    }

    private static boolean validCsrfToken(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sessionToken = (String) session.getAttribute("csrfToken");
        if (sessionToken == null) {
            return false;
        }
        
        String requestToken = request.getParameter("csrfToken");
        if (requestToken == null) {
            return false;
        }
        
        return sessionToken.equals(requestToken);
    }
}
