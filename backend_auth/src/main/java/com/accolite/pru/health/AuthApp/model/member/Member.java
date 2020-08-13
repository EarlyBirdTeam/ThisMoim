package com.accolite.pru.health.AuthApp.model.member;


import com.accolite.pru.health.AuthApp.model.User;
import com.accolite.pru.health.AuthApp.model.audit.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "MEMBER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member extends DateAudit {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "MEMBER_ID")
    private String memberId;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 36, name = "MEMBER_ROLE")
    private MemberRoleName memberRoleName;

    @Column(length = 36, name = "ATTENDANCE")
    private int attendance;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(name = "MemberRoleMapping", joinColumns = {
//            @JoinColumn(name = "MEMBER_ID", referencedColumnName = "MEMBER_ID")}, inverseJoinColumns = {
//            @JoinColumn(name = "MEMBERROLE_ID", referencedColumnName = "MEMBERROLE_ID")})
//    private Set<MemberRole> memberRoles = new HashSet<>();

    @Column
    private String channel;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
