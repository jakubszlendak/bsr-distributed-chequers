package bsr.project.checkers.logger;

public class Logs {
    
    private static final LogLevel CONSOLE_LEVEL = LogLevel.ALL;
    private static final LogLevel SHOW_TRACE_DETAILS_LEVEL = LogLevel.DEBUG;
    private static final boolean SHOW_EXCEPTIONS_TRACE = true;
    
    // console text formatting characters
    private static String C_RESET = "\033[0m";
    private static String C_BOLD = "\033[1m";
    private static String C_DIM = "\033[2m";
    private static String C_ITALIC = "\033[3m";
    private static String C_UNDERLINE = "\033[4m";
    
    private static String C_BLACK = "\033[30m";
    private static String C_RED = "\033[31m";
    private static String C_GREEN = "\033[32m";
    private static String C_YELLOW = "\033[33m";
    private static String C_BLUE = "\033[34m";
    private static String C_MAGENTA = "\033[35m";
    private static String C_CYAN = "\033[36m";
    private static String C_WHITE = "\033[37m";
    
    public static void error(String message) {
        log(message, LogLevel.ERROR, "[ERROR] " + C_RESET);
    }
    
    public static void error(Throwable ex) {
        log(ex.getMessage(), LogLevel.ERROR, C_BOLD + C_RED + "[EXCEPTION - " + ex.getClass().getName() + "] " + C_RESET);
        printExceptionStackTrace(ex);
    }
    
    public static void errorUncaught(Throwable ex) {
        log(ex.getMessage(), LogLevel.FATAL, C_BOLD + C_RED + "[UNCAUGHT EXCEPTION - " + ex.getClass().getName() + "] " + C_RESET);
        printExceptionStackTrace(ex);
    }
    
    public static void fatal(String message) {
        log(message, LogLevel.FATAL, C_BOLD + C_RED + "[ERROR] " + C_RESET);
        System.exit(1);
    }
    
    public static void fatal(Throwable ex) {
        String e = ex.getClass().getName() + " - " + ex.getMessage();
        printExceptionStackTrace(ex);
        fatal(e);
    }
    
    public static void warn(String message) {
        log(message, LogLevel.WARN, C_BOLD + C_YELLOW + "[warn] " + C_RESET);
    }
    
    public static void info(String message) {
        log(message, LogLevel.INFO, C_BOLD + C_BLUE + "[info] " + C_RESET);
    }
    
    public static void debug(String message) {
        log(message, LogLevel.DEBUG, C_BOLD + C_GREEN + "[debug] " + C_RESET);
    }
    
    public static void trace(String message) {
        log(message, LogLevel.TRACE, C_GREEN + "[trace] " + C_RESET);
    }
    
    
    private static void log(String message, LogLevel level, String logPrefix) {
        
        if (level.lowerOrEqual(CONSOLE_LEVEL)) {
            
            String consoleMessage;
            if (level.higherOrEqual(SHOW_TRACE_DETAILS_LEVEL)) {
                final int stackTraceIndex = 4;
                
                StackTraceElement ste = Thread.currentThread().getStackTrace()[stackTraceIndex];
                
                String methodName = ste.getMethodName();
                String fileName = ste.getFileName();
                int lineNumber = ste.getLineNumber();
                
                consoleMessage = logPrefix + methodName + "(" + fileName + ":" + lineNumber + "): " + message;
            } else {
                consoleMessage = logPrefix + message;
            }
            
            System.out.println(consoleMessage);
        }
    }
    
    public static void printStackTrace() {
        int i = 0;
        for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
            i++;
            if (i <= 3) continue;
            String methodName = ste.getMethodName();
            String fileName = ste.getFileName();
            int lineNumber = ste.getLineNumber();
            String consoleMessage = "[trace] STACK TRACE " + (i - 3) + ": " + methodName + "(" + fileName + ":" + lineNumber + ")";
            System.out.println(consoleMessage);
        }
    }
    
    private static void printExceptionStackTrace(Throwable ex) {
        if (SHOW_EXCEPTIONS_TRACE) {
            ex.printStackTrace();
        }
    }
    
}
