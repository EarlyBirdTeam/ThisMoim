package com.websocket.board.service;

import com.websocket.board.repo.CircleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CircleService {
    @Autowired
    CircleRepository circleRepository;

}
