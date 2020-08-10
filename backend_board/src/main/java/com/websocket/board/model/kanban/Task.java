package com.websocket.board.model.kanban;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Task implements Serializable {
    private String taskTitle;
    private String taskContents;
    private String taskAssigner;
}
