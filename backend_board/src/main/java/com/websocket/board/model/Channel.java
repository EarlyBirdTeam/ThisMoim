package com.websocket.board.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.websocket.board.model.calendar.Scheduler;
import com.websocket.board.model.kanban.Kanban;
import com.websocket.board.model.postit.Postit;
import com.websocket.board.model.user.UserChannel;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Channel implements Serializable {

    //private static final long serialVersionUID = 6494678977089006639L;

//    @GeneratedValue(generator="system-uuid")
//    @GenericGenerator(name="system-uuid", strategy = "uuid")
//    @Column(length = 36)
    @Id
    private String channelId;
    private String channelName;
    private Long userCount; // 채팅방 인원수
    private Long idCount;

    @OneToMany(mappedBy = "channel")
    @JsonManagedReference
    @Builder.Default
    private List<Postit> postitList = new ArrayList<>();

    @OneToOne(mappedBy = "channel")
    @JsonManagedReference
    private Scheduler scheduler;

    @OneToOne(mappedBy = "channel")
    @JsonManagedReference
    private Kanban kanban;

//    @OneToMany(mappedBy = "channel")
//    @JsonManagedReference
//    @Builder.Default
//    private List<Member> memberList = new ArrayList<>();

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "board_user_id")
//    @JsonBackReference
//    private BoardUser boardUser;

    @OneToMany(mappedBy = "channel")
    @JsonManagedReference
    @Builder.Default
    private List<UserChannel> userList = new ArrayList<>();

    public Channel(String channelName) {
        this.channelName = channelName;
    }

    public Channel(String channelId, long idCount, String channelName, List<Postit> postitList) {
        this.channelId = channelId;
        this.idCount = idCount;
        this.channelName = channelName;
        this.postitList = postitList;
    }

}
