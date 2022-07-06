package com.book.web_todo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/****
 * 모델과 엔티티를 한 클래스에 구현
 * 비즈니스 데이터를 담는 역할과 데이터베이스의 테이블과 스키마를 표현하는 두 역할
 */
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
