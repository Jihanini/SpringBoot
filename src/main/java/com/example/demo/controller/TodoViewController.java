package com.example.demo.controller;

import com.example.demo.exception.TodoNotFoundException;
import com.example.demo.model.Todo;
import com.example.demo.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/view/todos")
public class TodoViewController {

    private final TodoService service;

    public TodoViewController(TodoService service) {
        this.service = service;
    }

    //메인 페이지 (할일 목록)
    @GetMapping
    public String list(Model model) {
        List<Todo> todos = service.getAllTodos();
        long completedCount = service.filterByCompletion(true).size();
        long activeCount = service.filterByCompletion(false).size();
        int progressPercent = todos.isEmpty() ? 0 : (int) (completedCount * 100 / todos.size());

        model.addAttribute("todos", todos);
        model.addAttribute("completedCount", completedCount);
        model.addAttribute("activeCount", activeCount);
        model.addAttribute("progressPercent", progressPercent);
        return "main-improved";
    }

    //완료/미완료 필터링
    @GetMapping("/filter")
    public String filterByCompletion(boolean completed, Model model) {
        List<Todo> filtered = service.filterByCompletion(completed);
        model.addAttribute("todos", filtered);
        model.addAttribute("filterType", completed ? "완료된 할 일" : "미완료 할 일");
        model.addAttribute("count", filtered.size());
        return "main-improved";
    }

    // 우선순위별 정렬
    @GetMapping("/sort")
    public String sortByPriority(Model model) {
        List<Todo> sorted = service.sortByPriority();
        model.addAttribute("todos", sorted);
        model.addAttribute("sortType", "priority");
        model.addAttribute("count", sorted.size());
        return "main-improved";
    }

    // 제목 검색
    @GetMapping("/search")
    public String searchByTitle(@RequestParam String keyword, Model model) {
        List<Todo> result = service.searchByTitle(keyword);
        model.addAttribute("todos", result);
        model.addAttribute("searchkeyword", keyword);
        model.addAttribute("count", result.size());
        return "main-improved";
    }



    // correction 페이지
    @GetMapping("/correction")
    public String correctionPage() {
        return "correction";
    }

    //새할일 추가
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("todo", new Todo());
        return "form-improved";
    }

    // 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Todo todo = service.getTodoById(id)
                .orElseThrow(() -> new TodoNotFoundException(id)); // 의미 있는 예외 발생
        model.addAttribute("todo", todo);
        return "form-improved";
    }

    //저장 관리 (추가나 수정)
    @PostMapping
    public String save(@Valid @ModelAttribute Todo todo, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(e -> System.out.println(e));
            model.addAttribute("todo", todo);
            return "form-improved";
        }
        service.createTodo(todo); //id 가 있으면 update 처럼 동작해라
        return "redirect:/view/todos";
    }

    //삭제처리
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.deleteTodo(id);
        return "redirect:/view/todos";
    }


}
