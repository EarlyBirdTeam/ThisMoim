package com.websocket.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Postit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "postit_id")
    private Long id;

    private String title;
    private String contents;
    @Column(name = "\"left\"")
    private String left;
    @Column(name = "top")
    private String top;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id")
    private Board board;
}
