package com.websocket.board.model;

import com.websocket.board.util.Point;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Postit {

    private Long id;
    // 좌표필드
    private String left;
    private String top;
//    private String boardId; // 보드 번호
//    private String writer; // 작성자
    private String title;
    private String contents; // 포스트잇 텍스트
}