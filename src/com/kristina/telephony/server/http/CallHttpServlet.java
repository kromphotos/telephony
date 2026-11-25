package com.kristina.telephony.server.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kristina.telephony.server.CallService;
import com.kristina.telephony.shared.TelephonyException;

public class CallHttpServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CallHttpServlet.class.getName());
    private final CallService callService = CallService.getInstance();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //считывание запроса
        StringBuilder body = new StringBuilder();
        String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            body.append(line);
        }
        try {
            CallRequest callRequest = gson.fromJson(body.toString(), CallRequest.class);
            
            if (callRequest == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String phoneNumber = callRequest.getPhoneNumber();
            
            if (phoneNumber == null || phoneNumber.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            callService.addIncomingCall(phoneNumber);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (JsonSyntaxException e) {
            LOGGER.warning("Invalid JSON: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (TelephonyException e) {
            LOGGER.warning("Business error: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.length() <= 1) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String phoneNumber = pathInfo.substring(1);//чтобы без слэша было

        try {
            callService.cancelIncomingCall(phoneNumber);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (TelephonyException e) {
            LOGGER.warning("Business error: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
