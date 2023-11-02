package ru.practicum.explorewithme.controller.ewmadmin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import ru.practicum.explorewithme.dto.out.CategoryDto;
import ru.practicum.explorewithme.dto.in.create.NewCategoryDto;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    @PostMapping
    public CategoryDto createCategory(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        log.info("Creating new category {}", newCategoryDto);
        return null;
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@PathVariable Long catId,
                                      @RequestBody @Valid NewCategoryDto newCategoryDto) {
        log.info("Patch category with id {}, category {}", catId, newCategoryDto);
        return null;
    }

    @DeleteMapping("/{catId}")
    public boolean deleteCategory(@PathVariable Long catId) {
        log.info("Delete category by id {}", catId);
        return true;
    }
}
