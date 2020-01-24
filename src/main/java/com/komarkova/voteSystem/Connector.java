package com.komarkova.voteSystem;


import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

public class Connector {

    private Socket socket;
    public Connector(String ip, int port) throws IOException {
        socket = new Socket(ip,port);
    }
    public String API(String login, String tId) throws IOException {
        JSONObject jo = new JSONObject();
        jo.put("login", login);
        jo.put("tId", tId);
        jo.put("isNotRegistered", "");
        return Send(jo.toString());
    }
    public String getPermission(String login, String tId, String isNotRegistered) throws IOException{
        JSONObject jo = new JSONObject();
        jo.put("login", login);
        jo.put("tId", tId);
        jo.put("isNotRegistered", isNotRegistered);
        return Send(jo.toString());
    }
    public String registration(String login, String tId, String isNotRegistered, String name, String surname, String photo) throws IOException {
        JSONObject jo = new JSONObject();
        jo.put("login", login);
        jo.put("tId", tId);
        jo.put("isNotRegistered", isNotRegistered);
        jo.put("name", name);
        jo.put("surname", surname);
        jo.put("photo", photo);
        return Send(jo.toString());
    }
    private String Send(String massage) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.write(massage+"\n");
        writer.flush();
        StringBuilder s = new StringBuilder();

        while (true){
            if (reader.ready()){
                s.append(reader.readLine());
                break;
            }
        }
        return s.toString();
    }
}