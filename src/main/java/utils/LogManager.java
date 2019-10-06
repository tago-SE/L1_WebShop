package utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogManager {


    // We are running Tomcat, so we need to write to the tomcat writeable directory "webapps"
    // Make sure that the file "dblogs.log" exists inside the directory and that
    // you have access rights to this folder.
    // Apache Software Foundation/TomcatX/webapps/ ...
    private static final String FILE_PATH = "..//webapps//Projects//L1_WebShop//dblogs.log";

    private static Logger logger = null;

    public static Logger getLogger() {
        if (logger == null) {
            logger = Logger.getLogger("MyLog");
            FileHandler fh = null;
            try {
                fh = new FileHandler(FILE_PATH, true);
                logger.addHandler(fh);
                SimpleFormatter formatter = new SimpleFormatter();
                fh.setFormatter(formatter);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return logger;
    }
}
