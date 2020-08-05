package com.websocket.board.dto;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Channel implements Serializable {

    //private static final long serialVersionUID = 6494678977089006639L;

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length = 36)
    private String channelId;
    private String channelName;
    private long userCount; // 채팅방 인원수

    @OneToMany(mappedBy = "channel")
    private List<Postit> postitList = new ArrayList<>();

    public Channel(String channelName) {
        this.channelName = channelName;
    }

    public Channel(String channelId, String channelName, List<Postit> postitList) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.postitList = postitList;
    }

}
