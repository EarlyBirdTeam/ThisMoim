package com.websocket.board.model.Group;

import com.websocket.board.model.audit.DateAudit;
import com.websocket.board.model.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "CIRCLE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Circle extends DateAudit {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "CIRCLE_ID")
    private Long circleId;

    @OneToMany(mappedBy = "circle")
    private List<Member> members = new ArrayList<>();

}
