package io.kryptoblocks.msa.common.util;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.filter.LevelFilter;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.spi.FilterReply;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.LoggerFactory;
 


// TODO: Auto-generated Javadoc
/**
 * The Class LoggerUtil.
 */
public class LoggerUtil {
	
	/**
	 * Creates the logger for.
	 *
	 * @param name the name
	 * @param file the file
	 * @return the logger
	 */
	public static Logger createLoggerFor(String name, String file) {
				
		Logger templateLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("app");
        LoggerContext loggerContext = templateLogger.getLoggerContext();
        
        PatternLayoutEncoder patternLayoutEncoder = new PatternLayoutEncoder();              
        patternLayoutEncoder.setContext(loggerContext);
        patternLayoutEncoder.setPattern("%msg%n");
        patternLayoutEncoder.start();
        
        TimeBasedRollingPolicy timeBasedRollingPolicy = new TimeBasedRollingPolicy();
        timeBasedRollingPolicy.setContext( loggerContext );
        timeBasedRollingPolicy.setFileNamePattern( name + "-%d{yyyy-MM-dd}.log" );
        timeBasedRollingPolicy.setMaxHistory( 30 );
        
        // Set up the filters to ensure things get split as expected
        LevelFilter levelFilter = new LevelFilter();
        levelFilter.setContext( loggerContext );
        levelFilter.setLevel( Level.INFO );
        levelFilter.setOnMatch( FilterReply.ACCEPT );
        levelFilter.setOnMismatch( FilterReply.DENY );
        
     // Set up the trace and error appenders
        RollingFileAppender rollingFileAppender = new RollingFileAppender();
        rollingFileAppender.setContext(loggerContext);
        rollingFileAppender.setName(name);
        rollingFileAppender.setFile(file);
        rollingFileAppender.setEncoder(patternLayoutEncoder);
        rollingFileAppender.setRollingPolicy( timeBasedRollingPolicy );
        rollingFileAppender.addFilter( levelFilter );
        timeBasedRollingPolicy.setParent( rollingFileAppender );
        
     // Start everything
        timeBasedRollingPolicy.start();
        levelFilter.start(); 
        rollingFileAppender.start();
        
     // attach the rolling file appenders to the logger
        Logger logger = (ch.qos.logback.classic.Logger) loggerContext.getLogger(name);
        logger.addAppender(rollingFileAppender);       
        return logger;
        
  }
}
