package com.book.web_todo.service;

import com.book.web_todo.model.TodoEntity;
import com.book.web_todo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service // 서비스 레이어임을 알려주는 어노테이션
public class ToDoService {

    @Autowired
    private TodoRepository repository;


    public String testService() {
        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
        repository.save(entity);
        TodoEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }

    public List<TodoEntity> create(final TodoEntity entity) {
        validate(entity);
        repository.save(entity);
        log.info("Entity Id: {} is saved.", entity.getId());
        return repository.findByUserId(entity.getUserId());
    }

    public List<TodoEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);  // 레포지토리에서 아이디를 이용해 검색해서 반환
    }

    public List<TodoEntity> update(final TodoEntity entity) {
        // 넘겨받은 entity 유효성 검사
        validate(entity);

        // entity의 id를 이용, TodoEntity를 가져옴. 존재하지 않은 경우 업데이트 불가
        final Optional<TodoEntity> original = repository.findById(entity.getId());

        original.ifPresent(todo -> {
            //반환된 TodoEntity가 존재할 경우 값을 새 entity 값으로 덮어 씌운다
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());
            //repository에 새 값을 저장
            repository.save(todo);
        });
        // 람다에 익숙하지 않을 경우 사용
//        if (original.isPresent()) {
//            final TodoEntity todo = original.get();
//            todo.setTitle(entity.getTitle());
//            todo.setDone(entity.isDone());
//            repository.save(todo);
//        }

        return retrieve(entity.getUserId());

    }

    public List<TodoEntity> delete(final TodoEntity entity) {
        validate(entity);

        try {
            repository.delete(entity);
        } catch (Exception e) {
            log.error("error deleting entity", entity.getId(), e);
            throw new RuntimeException("error deleting entity" + entity.getId());
        }
        return retrieve(entity.getUserId());
    }

    private void validate(final TodoEntity entity) {
        if (entity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }
        if (entity.getUserId() == null) {
            log.warn("Unknown user");
            throw new RuntimeException("Unknown user");
        }
    }



}
