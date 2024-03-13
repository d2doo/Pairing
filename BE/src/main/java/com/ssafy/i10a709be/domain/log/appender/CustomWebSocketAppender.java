package com.ssafy.i10a709be.domain.log.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.i10a709be.domain.log.dto.LogDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.ExecutionException;


@Slf4j
public class CustomWebSocketAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final WebSocketClient client = new StandardWebSocketClient();

    private static final WebSocketHandler handler = new TextWebSocketHandler();

    private static String ip;

    static{
        try{
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.info( "ip 조회간 에러 발생");
            log.debug( e.toString() );
        }
    }

    private static WebSocketSession session;

    static{
        try {
            session = client.execute( handler, "ws://localhost:8081/logs" ).get();
        } catch (InterruptedException e) {
            log.info("ws 생성간 인터럽트 발생");
            log.debug( e.toString() );
        } catch (ExecutionException e) {
            log.info("ws 생성간 에러 발생");
            log.debug( e.toString() );
        }
    }
    private static final String FORMAT_MESSAGE = "[%s] %s %s.%s:%d - %s";

    // @formatter:off
    protected void append(ILoggingEvent e) {
        if( !session.isOpen() ){
            refreshSession();
        }
        Date now = Date.from( Instant.now() );
        String content = formatLog( e );
        LogDto sendData = new LogDto( content, now, ip );
//        WebSocketMessage sendData = new TextMessage( msg );
        //로그는 매 순간 실시간이기에 반복적인 ObjectMapper 생성은 비효율적임. static으로 관리
//        ObjectMapper mapper = new ObjectMapper();


        try {
            String sendDataJson = mapper.writeValueAsString(sendData);
            log.info( "sendMsg: " + sendDataJson );
            TextMessage sendDataTextMessage = new TextMessage( sendDataJson );
            session.sendMessage( sendDataTextMessage );
        } catch (IOException ex) {
            log.info("WebSocket 데이터 송신 간 장애 발생");
            log.debug( e.toString() );
        }
    }


    private void refreshSession() {
        try {
            session = client.execute( handler, "ws://localhost:8081/logs" ).get();
        }catch (ExecutionException e) {
            log.info("ws 세션 생성간 장애 발생");
            log.debug( e.toString() );
        } catch (InterruptedException e) {
            log.info("ws 세션 생성간 인터럽트 발생");
            log.debug( e.toString() );
        }
    }

    private void refreshIp(){
        try{
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.info( "ip 조회간 에러 발생");
            log.debug( e.toString() );
        }
    }

    private String formatLog(ILoggingEvent event){
        StackTraceElement[] callerData = event.getCallerData();
        StackTraceElement stackTraceElement = callerData[0];
        String threadName = event.getThreadName();
        String level = event.getLevel().toString();
        String logger = event.getLoggerName();
        String msg = event.getFormattedMessage();
        // String className = stackTraceElement.getClassName();
        String method = stackTraceElement.getMethodName();
        int lineNumber = stackTraceElement.getLineNumber();

        return String.format(FORMAT_MESSAGE, threadName, level, logger, method, lineNumber, msg);
    }

}
