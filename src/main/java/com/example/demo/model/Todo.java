package com.example.demo.model;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data   //@Data 어노테이션은  @Getter + @Setter + @ToString + @EqualsAndHashCode 세개를 다 합친 개사기 패키지
@NoArgsConstructor // 기본 생성자 자동 생성  // 모든 필드를 받는 생성자 자동 생성
@AllArgsConstructor
public class Todo {
    private Long id;

    @NotBlank(message = "제목을 입력하세요.") // null 빈 문자열 공백 x
    @Size(max = 10, message = "제목은 최대 10 글자까지 가능함.")

    private String title;
    @Size(max = 1000, message = "설명은 최대 1000자 까지 가능함." )

    private String description;

    @NotBlank(message = "우선 순위를 선택하세요.")
    private String priority;

    private Boolean completed;
}
