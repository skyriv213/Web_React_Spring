package com.book.web_todo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO<T>  { // TodoDTO뿐만 아니라 다른 모델의 DTO도 받아오기 위해 java 제네릭 사용
    private String error;
    private List<T> data; // Todo를 하나만 반환하는 것이 아닌 리스트를 반환하는 경우가 더 많기에 데이터를 리스트로 반환하도록 작성
}
