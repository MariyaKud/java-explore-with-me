package ru.practicum.explorewithme.controller.ewmadmin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.checkerframework.checker.index.qual.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.out.UserDto;
import ru.practicum.explorewithme.dto.in.create.NewUserRequestDto;
import ru.practicum.explorewithme.service.user.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers(@RequestParam(name = "ids", defaultValue = "") List<Long> ids,
                                  @RequestParam(name = "from", defaultValue = "0") Integer from,
                                  @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get users by ids {}, from={}, size={}", ids, from, size);
        return userService.getUsers(ids, from, size);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserDto createUser(@RequestBody @Valid NewUserRequestDto userDto) {
        log.info("Creating new user {}", userDto);
        return userService.createUser(userDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public boolean deleteUser(@PathVariable("userId") Long userId) {
        log.info("Delete user by id {}", userId);
        return userService.deleteUserById(userId);
    }
}
