package com.kristina.telephony.server.http;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kristina.telephony.server.CallService;
import com.kristina.telephony.shared.TelephonyException;

public class CallHttpServlet extends HttpServlet {
    private final CallService callService = CallService.getInstance();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //считывание запроса
        StringBuilder body = new StringBuilder();
        String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            body.append(line);
        }
        
        String json = body.toString();
        String phoneNumber = extractPhoneNumber(json);
        
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        try {
            callService.addIncomingCall(phoneNumber);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (TelephonyException e) {
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
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    private String extractPhoneNumber(String json) {
        String key = "\"phoneNumber\"";
        int keyIndex = json.indexOf(key);
        if (keyIndex == -1) return null;
        
        int colonIndex = json.indexOf(':', keyIndex);//ищем индекс :
        if (colonIndex == -1) return null;//если нет :
        
        int startQuote = json.indexOf('"', colonIndex); //первая кавычка у номера
        if (startQuote == -1) return null;
        
        int endQuote = json.indexOf('"', startQuote + 1);
        if (endQuote == -1) return null;
        
        return json.substring(startQuote + 1, endQuote);
    }   
}
