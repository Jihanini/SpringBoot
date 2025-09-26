package com.example.demo.todo.model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/view/todos")
public class Todo {
    private Long id; //ID
    private String title; //제목
    private String description; //설명
    private String priority; //우선순위
    private boolean completed; //완료 여부

    //기본 생성자
    public Todo() {}


    //생성자 (객체를 만들 때 초기 상태를 한 번에 세팅하기 위해서) 생성함
    public Todo(Long id, String title, String description, String priority, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.completed = completed;
    }

        // Getter(값 꺼내오기)/Setter(값 넣기) (읽기(get) / 쓰기(set))
        public Long getId() { return id; } // 필드 값을 반환하는 메서드
        public void setId(Long id) { this.id = id; } // 외부에서  id 값을 지정할때 사용하는 메서드

        //Title 필드 get, set
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        //description 필드 get, set
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        //priority 필드 get, set
        public String getPriority() { return priority; }
        public void setPriority(String priority) { this.priority = priority; }
        //completed 필드 get, set
        public boolean isCompleted() { return completed; }
        public void setCompleted(boolean completed) { this.completed = completed; }





}


// 이렇게도 작성이 가능 (다들 저렇게 쓰던데)
//lombok 라이브러리를 사용

//package com.example.todo.model;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data   //@Data 어노테이션은  @Getter + @Setter + @ToString + @EqualsAndHashCode 세개를 다 합친 개사기 패키지
//@NoArgsConstructor // 기본 생성자 자동 생성  // 모든 필드를 받는 생성자 자동 생성
//@AllArgsConstructor
//public class Todo {
//    private Long id;
//    private String title;
//    private String description;
//    private String priority;
//    private boolean completed;
//}
