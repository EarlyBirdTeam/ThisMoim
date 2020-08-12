package com.websocket.board.controller;

import com.websocket.board.service.CircleService;
import com.websocket.board.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group")
public class GroupController {
    @Autowired
    MemberService memberService;
    @Autowired
    CircleService circleService;


}
