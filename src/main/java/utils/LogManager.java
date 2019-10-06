package utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogManager {


    // NOTICE: The file FILE_PATH must exists and be accessible by the program.
    // Should not be a dynamic link...
    private static final String FILE_PATH = "C:\\Users\\tiago\\Desktop\\Repositories\\L1_WebShop\\dblogs.log";
    //
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
