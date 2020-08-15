package com.websocket.board.model.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    //@Column(nullable = false, name = "user_id")
    private String userId;

    private String email;
    private String username;
    private String nickname;
    private String token;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    @Builder.Default
    private List<UserChannel> userChannels = new ArrayList<>();

}
