package com.websocket.board.service;

import com.websocket.board.model.SocketBoardMessage;
import com.websocket.board.model.calendar.Calendar;
import com.websocket.board.model.crud.CRUDModule;
import com.websocket.board.model.crud.CRUDType;
import com.websocket.board.model.kanban.Kanban;
import com.websocket.board.model.postit.Postit;
import org.apache.tomcat.jni.Poll;

public interface DBSyncService {

    void postitDBSync(SocketBoardMessage board);
    void postitDeleteSync(SocketBoardMessage board);

    boolean crudModuleSync(CRUDModule crudModule);
    boolean postitSync(CRUDType crudType, Postit postit);
    boolean calendarSync(CRUDType crudType, Calendar calendar);
    boolean pollSync(CRUDType crudType, Poll poll);
    boolean kanbanSync(CRUDType crudType, Kanban kanban);

    void channelDBIdCountSync(String channelId, Long idCount);

}
