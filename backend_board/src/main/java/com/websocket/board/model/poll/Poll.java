package com.websocket.board.model.poll;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.websocket.board.model.postit.Postit;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Poll implements Serializable {

    @Id
    private String pollId;

    @Column(name = "\"left\"")
    private String left;
    private String top;
    private String question;

//    @OneToMany(mappedBy = "poll")
//    @JsonManagedReference
//    @Builder.Default
//    private List<Answer> answers = new ArrayList<>();

    private boolean multipleVotes;
    private int totalVotes;

//    @OneToMany(mappedBy = "poll")
//    @JsonManagedReference
//    @Builder.Default
//    private List<String> userVoted = new ArrayList<>();
    private boolean isSetAll;
    private boolean isEnd;

}
