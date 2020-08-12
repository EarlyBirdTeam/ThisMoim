package com.websocket.board.model.member;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Member")
public class Member {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", allocationSize = 1)
    private Long id;

    @NaturalId
    @Column(name = "ROLE")
    @NotBlank(message = "member role")
    private String email;

    @Column(name = "ATTENDANCE")
    @NotBlank(message = "attendance count")
    private int attendance;

    private String userId;


}
