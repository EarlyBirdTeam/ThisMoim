package com.websocket.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    //@Column(name = "channel_id")
    private String channelId;

    @OneToMany(mappedBy = "board")
    private List<Postit> postitList = new ArrayList<>();

    //@Lob
    //@Column(nullable = true)

}
