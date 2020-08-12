package com.websocket.board.model.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "MEMBER_ROLE")
public class MemberRole {
    @Id
    @Column(name = "MEMBERROLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MEMBERROLE_NAME")
    @Enumerated(EnumType.STRING)
    @NaturalId
    private MemberRoleName memberRoleName;

    @ManyToMany(mappedBy = "memberRoles", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Member> memberList = new HashSet<>();
}
