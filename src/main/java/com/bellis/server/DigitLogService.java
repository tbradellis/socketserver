package com.bellis.server;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DigitLogService implements Runnable {


    @Override
    public void run() {
        final Logger LOGGER = Logger.getLogger("DigitLog");
        Handler fileHandler = null;
        try{
            fileHandler = new FileHandler("./server.log");
            LOGGER.addHandler(fileHandler);
            int preUnique = 0;
            int preDuplicate = 0;
            while(true){
                int totalUnique = CollectorService.uniqueCount();
                int unique = CollectorService.uniqueCount();
                int duplicate = CollectorService.duplicate.intValue();
                LOGGER.log(Level.INFO, "New List Report: ", CollectorService.printList());
                String message = MessageFormat.format("Total: Unique = {0}; Since last: Unique = {1} Duplicates = {1}; ",
                    totalUnique , unique - preUnique, duplicate - preDuplicate );
                System.out.println(message);
                preUnique = unique;
                preDuplicate = duplicate;
                Thread.sleep(10000);
            }

        }catch(IOException | InterruptedException e){
            LOGGER.log(Level.SEVERE, "IOException or FileHandler Error");
        }finally{
            fileHandler.close();
        }
    }
}
