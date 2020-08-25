package com.ukrainer.infostroy.socket;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.ukrainer.infostroy.dao.TableDao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/actions")
public class UserWebSocket {

    Session session;

    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        try {
            final Map<String, Long> tableMap = new HashMap<String, Long>();

            BinaryLogClient client = new BinaryLogClient("localhost", 3306, "root", "qwer1234");

            client.registerEventListener(event -> {
                EventData data = event.getData();

                System.out.println(" --- " + data);
                 if(data instanceof UpdateRowsEventData) {
                    UpdateRowsEventData eventData = (UpdateRowsEventData)data;

                     try {
                         Thread.sleep(5000);
                         String writer = TableDao.getAllRows();
                         System.out.println(writer);
                         synchronized (session) {
                             if (session.isOpen()) {
                                 session.getBasicRemote().sendText(writer);
                             }
                         }
                     } catch (IOException | InterruptedException e) {
                         e.getCause();
                     }
                }
            });

            client.connect();
        } catch (IOException e) {
           e.getCause();
        }

    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        String.format("Session %s closed because of %s", session.getId(), closeReason);
        if(session.isOpen()){
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("onMessage::From=" + session.getId() + " Message=" + message);
    }

    @OnError
    public void onError(Throwable t) {
        System.out.println("onError::" + t.getMessage());
    }

}
