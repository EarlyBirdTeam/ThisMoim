package com.websocket.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Postit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name = "postit_id")
    private Long id;

    private String title;
    private String contents;
    @Column(name = "\"left\"")
    private String left;
    @Column(name = "top")
    private String top;
    @Column(name = "front_postit_id")
    private Long frontPostitId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "channel_id")
    private Channel channel;
}
