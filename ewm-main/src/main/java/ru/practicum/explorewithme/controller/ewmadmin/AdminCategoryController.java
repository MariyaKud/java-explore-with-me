package ru.practicum.explorewithme.controller.ewmadmin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.practicum.explorewithme.dto.output.CategoryDto;
import ru.practicum.explorewithme.dto.input.create.NewCategoryDto;
import ru.practicum.explorewithme.service.category.AdminCategoryService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final AdminCategoryService categoryService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        log.info("Creating new category {}", newCategoryDto);
        return categoryService.createCategory(newCategoryDto);
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@PathVariable Long catId,
                                      @RequestBody @Valid NewCategoryDto updCategoryDto) {
        log.info("Patch category with id {}, category {}", catId, updCategoryDto);
        return categoryService.updateCategory(catId, updCategoryDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public boolean deleteCategory(@PathVariable Long catId) {
        log.info("Delete category by id {}", catId);
        return categoryService.deleteCategoryById(catId);
    }
}
