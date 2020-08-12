package com.websocket.board.controller;

import com.websocket.auth.repo.UserRepo;
import com.websocket.board.repo.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private UserRepo userRepo;


}
