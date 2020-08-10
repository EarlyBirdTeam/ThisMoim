package com.websocket.board.model.calendar;

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
public class Calendar implements Serializable {

    private String left;
    private String top;
    private List<Event> events;

}
