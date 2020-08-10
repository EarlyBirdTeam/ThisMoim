package com.websocket.board.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Calendar implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "calendar_id")
    private Long id;

    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private String title;

//    private boolean isCalendar;

    @Column(name = "\"left\"")
    private String left;
    @Column(name = "top")
    private String top;
    @Column(name = "frontCalendarId")
    private Long frontCalendarId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    @JsonBackReference
    private Channel channel;
}
