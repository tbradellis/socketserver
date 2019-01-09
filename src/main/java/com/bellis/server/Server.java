package com.bellis.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int MAX_CONNECTIONS = 5;
    private static int PORT = 8085;

    public static void main(String[] args)  {
        if (args.length > 0) {
            try {
                PORT = Integer.parseInt(args[0]);
            }catch(NumberFormatException e){
                System.out.println("Invalid port number. Attempting with default 8085");
            }
        }
        ExecutorService executeLogging = Executors.newSingleThreadExecutor();
        try{
            executeLogging.submit(new DigitLogService());
        }finally {
            executeLogging.shutdown();
        }


        ExecutorService executor = Executors.newFixedThreadPool(5);
        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            System.out.println("Listening on " + PORT);
            while(true) {
                System.out.println("Listening...");
                executor.submit(new ServerConnectionHandler(serverSocket.accept()));
            }

        }catch(IOException e){
            System.err.println("Unable to listen on port " + PORT);
            System.exit(-1);
        }finally {
            executor.shutdown();
        }
    }
}
