package ru.practicum.explorewithme.controller.ewmpublic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.checkerframework.checker.index.qual.Positive;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.out.CategoryDto;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam(name = "from", defaultValue = "0") Integer from,
                                           @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get categories from {}, size={}", from, size);
        return null;
    }

    @GetMapping("/{catId}")
    public CategoryDto findByCategory(@PathVariable Long catId) {
        log.info("Find category by id {}", catId);
        return null;
    }
}
