package com.book.web_todo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoEntity {
    private String id; // 오브젝트의 아이디
    private String userId; // 오브젝트를 생성한 사용자의 아이디
    private String title; // Todo 타이틀
    private boolean done;
}
