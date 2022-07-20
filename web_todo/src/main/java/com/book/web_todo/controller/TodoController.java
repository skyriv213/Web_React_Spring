package com.book.web_todo.controller;

import com.book.web_todo.dto.ResponseDTO;
import com.book.web_todo.dto.TodoDTO;
import com.book.web_todo.model.TodoEntity;
import com.book.web_todo.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private ToDoService service;

    @GetMapping("/test")
    public ResponseEntity<?> testTodo() {
        String str = service.testService();
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto) {
        try {

            // TodoEntity로 변환한다
            TodoEntity entity = TodoDTO.toEntity(dto);
            // id를 null로 초기화, 생성 당시에는 id가 없어야 하기 때문
            entity.setId(null);
            // 임시 사용자 아이디 설정, 이 부분은 4장에서 인증과 인가에서 수정 예정
            // 현재 인증과 인가 기능이 없으므로 한 사용자(temporary-user)만 로그인 업이 사용할 수 있는 애플리케이션
            entity.setUserId(userId);

            // 서비스를 이용해 Todo 엔티티 생성
            List<TodoEntity> entities = service.create(entity);

            // 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            // TodoDTO 리스트를 이용해 ResponseDTO를 초기화
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            // ResponseDTO를 리턴
            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            // 혹시 예외가 있는 경우 dto 대신 error에 메세지 넣어서 수행
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId) {

        // 서비스 메서드의 retrieve() 메서드를 사용해서 Todo 리스트를 가져온다
        List<TodoEntity> entities = service.retrieve(userId);

        // 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        // 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화 한다
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        // ResponseDTO를 리턴
        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userId,@RequestBody TodoDTO dto) {

        // dto를 entity로 변환
        TodoEntity entity = TodoDTO.toEntity(dto);

        // id를 temporaryUserId로 초기화, 4장에서 인증과 인가에서 수정예정
        entity.setUserId(userId);

        // 서비스를 이용해 entity를 update
        List<TodoEntity> entities = service.update(entity);

        // 자바 스트림을 이용해서 리턴된 entity 리스트를 TodoDTO 리스트로 변환
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        // 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        // ResponseDTO 반환
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(
            @AuthenticationPrincipal String userId
            ,@RequestBody TodoDTO dto) {
        try {
            TodoEntity entity = TodoDTO.toEntity(dto);
            entity.setUserId(userId);
            List<TodoEntity> entities = service.delete(entity);
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
