package com.websocket.board.model;

import com.websocket.board.model.scheduler.Scheduler;
import com.websocket.board.model.crud.CRUDModule;
import com.websocket.board.model.kanban.Kanban;
import com.websocket.board.model.poll.Poll;
import com.websocket.board.model.postit.Postit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocketBoardMessage implements Serializable {

    // 채널 자체 정보
    private String channelId;       // 채널 아이디
    private long userCount;         // 현재 참여하고 있는 사용자 수
    private List<String> memberList;// 채널 참여 멤버 닉네임 리스트

    // 모듈 관련 정보
    private long idCount;           // 프론트 생성 아이디 카운트
    private List<Postit> postitList;// 포스트잇 리스트 객체
    private Boolean isKanban;       // 칸반 생성 여부
    private Kanban kanban;          // 칸반 객체
    private Scheduler scheduler;    // 스케줄러 객체
    private List<Poll> poll;        // 투표 리스트 객체

    // DB 동기화를 위한 필드
    private CRUDModule crudModule;  // 수행 CRUD 메소드 종류
    private Boolean isDelete;       // Delete 수행여부

}
