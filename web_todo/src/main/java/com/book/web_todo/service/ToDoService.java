package com.book.web_todo.service;

import org.springframework.stereotype.Service;

@Service // 서비스 레이어임을 알려주느 어노테이션
public class ToDoService {

    public String testService() {
        return "Test Service";
    }
}
