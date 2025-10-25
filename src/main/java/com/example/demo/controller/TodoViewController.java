package com.example.demo.controller;

import com.example.demo.exception.TodoNotFoundException;
import com.example.demo.model.Todo;
import com.example.demo.service.TodoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/view/todos")
public class TodoViewController {

    private final TodoService service;

    public TodoViewController(TodoService service) {
        this.service = service;
    }

    // 메인 페이지
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
        model.addAttribute("filterType", "전체"); //현재 필터 표시
        return "main-improved";
    }

    //검색 기능
    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model) {
        List<Todo> todos = service.searchByTitle(keyword);
        model.addAttribute("todos", todos);
        model.addAttribute("keyword", keyword);
        model.addAttribute("filterType", "검색 결과");
        model.addAttribute("completedCount", service.filterByCompletion(true).size());
        model.addAttribute("activeCount", service.filterByCompletion(false).size());
        return "main-improved";
    }

    // 완료 미완료 필터
    @GetMapping("/filter")
    public String filterByCompletion(@RequestParam boolean completed, Model model) {
        List<Todo> todos = service.filterByCompletion(completed);
        model.addAttribute("todos", todos);
        model.addAttribute("filterType", completed ? "완료됨" : "미완료");
        model.addAttribute("completedCount", service.filterByCompletion(true).size());
        model.addAttribute("activeCount", service.filterByCompletion(false).size());
        return "main-improved";
    }

    // 우선순위 정렬
    @GetMapping("/sort")
    public String sortByPriority(Model model) {
        List<Todo> sorted = service.sortByPriority();
        model.addAttribute("todos", sorted);
        model.addAttribute("filterType", "우선순위 정렬");
        model.addAttribute("completedCount", service.filterByCompletion(true).size());
        model.addAttribute("activeCount", service.filterByCompletion(false).size());
        return "main-improved";
    }


    // correction 페이지
    @GetMapping("/correction")
    public String correctionPage() {
        return "correction";
    }

    // 새 할일 추가 폼
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("todo", new Todo());
        return "form-improved";
    }

    // 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Todo todo = service.getTodoById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        model.addAttribute("todo", todo);
        return "form-improved";
    }

    // 저장 (추가/수정)
    @PostMapping
    public String save(@Valid @ModelAttribute Todo todo, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.warn("Validation errors: {}", bindingResult.getAllErrors());
            model.addAttribute("todo", todo);
            return "form-improved";
        }

        // id가 있으면 수정, 없으면 생성
        if (todo.getId() != null && service.existsById(todo.getId())) {
            service.updateTodo(todo.getId(), todo);
        } else {
            service.createTodo(todo);
        }
        return "redirect:/view/todos";
    }

    // 삭제
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.deleteTodo(id);
        return "redirect:/view/todos";
    }
}
