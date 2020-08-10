package com.websocket.board.model.calendar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Event implements Serializable {
    private String name;
    private String content;
    private Date start;
    private Date end;
}
