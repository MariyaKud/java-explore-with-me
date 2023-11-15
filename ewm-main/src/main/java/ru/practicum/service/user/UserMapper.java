package ru.practicum.service.user;

import org.springframework.stereotype.Component;
import ru.practicum.dto.input.create.NewUserRequestDto;
import ru.practicum.dto.output.UserDto;
import ru.practicum.model.User;

import java.util.LinkedList;
import java.util.List;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public User fromDto(NewUserRequestDto dto) {
        return new User(null, dto.getName(), dto.getEmail());
    }

    public List<UserDto> mapToUserDto(Iterable<User> users) {
        List<UserDto> target = new LinkedList<>();
        users.forEach(u -> target.add(toDto(u)));
        return target;
    }
}
