package com.websocket.board.model.kanban;

import com.websocket.board.model.kanban.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Column implements Serializable {
    private String columnTitle;
    private List<Task> tasks;
}
