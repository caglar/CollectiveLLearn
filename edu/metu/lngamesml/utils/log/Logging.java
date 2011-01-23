package edu.metu.lngamesml.utils.log;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: Oct 24, 2010
 * Time: 9:20:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class Logging {

    private static Logger logger = Logger.getLogger("categorizationgames");

    private static boolean DebuggingMode = true;

    private Logging() {
    }

    public static boolean isDebuggingMode() {
        return DebuggingMode;
    }

    public static void setDebuggingMode(boolean debuggingMode) {
        DebuggingMode = debuggingMode;
    }

    public static void info(String message) {
        if (DebuggingMode) {
            System.out.println("INFO:" + message);
            logger.info(message);
        }
    }

    public static void log(Level level, String message) {
        System.out.println(message);
        logger.log(level, message);
    }

    public static void warning(String message) {
        System.err.println("WARNING:" + message);
        logger.warning(message);
    }

    public static void error(String message) {
        System.err.println("ERROR:" + message);
        logger.severe(message);
        System.exit(-1);
    }
}
