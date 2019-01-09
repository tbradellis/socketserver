package com.bellis.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ServerConnectionHandler implements Runnable{
    private final int INPUT_LENGTH = 9;
    Socket socket = null;

    CollectorService collectorService = new CollectorService();
    public ServerConnectionHandler(Socket socket){
        this.socket = socket;
    }
    public void run(){
        //For resources that implement autocloseable, ensures that resources will be closed..
        try(
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()))
        ) {
            String input;
            while((input = in.readLine()) != null ){
                if(input.length() != INPUT_LENGTH){
                    out.println("Enter " + INPUT_LENGTH + " digit integer.");
                }else{
                    try{
                        int number = Integer.parseInt(input);
                        if(!collectorService.addToList(number)){
                            collectorService.duplicate.incrementAndGet();
                        }
                    }catch(NumberFormatException e){
                        out.println("Not and Integer. Enter " + INPUT_LENGTH + " digit integer.");
                    }
                }
            }

            //not part of try with resources, so needs to be closed
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}





