package com.ccb.job.core.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xuxueli on 17/4/28.
 */
public class CcbJobLogger {
    private static Logger logger = LoggerFactory.getLogger("ccb-job logger");
    private static SimpleDateFormat ccbJobLoggerFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * append log
     *
     * @param appendLog
     */
    public static void log(String appendLog) {

        // logFileName
        String logFileName = CcbJobFileAppender.contextHolder.get();
        if (logFileName==null || logFileName.trim().length()==0) {
            return;
        }
        // "yyyy-MM-dd HH:mm:ss [ClassName]-[MethodName]-[LineNumber]-[ThreadName] log";
        StackTraceElement[] stackTraceElements = new Throwable().getStackTrace();
        StackTraceElement callInfo = stackTraceElements[1];

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(ccbJobLoggerFormat.format(new Date())).append(" ")
            .append("["+ callInfo.getClassName() +"]").append("-")
            .append("["+ callInfo.getMethodName() +"]").append("-")
            .append("["+ callInfo.getLineNumber() +"]").append("-")
            .append("["+ Thread.currentThread().getName() +"]").append(" ")
            .append(appendLog!=null?appendLog:"");
        String formatAppendLog = stringBuffer.toString();

        // appendlog
        CcbJobFileAppender.appendLog(logFileName, formatAppendLog);

        logger.warn("[{}]: {}", logFileName, formatAppendLog);
    }

}
