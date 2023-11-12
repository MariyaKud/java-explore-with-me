package ru.practicum.explorewithme.service.user;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.dto.input.create.NewUserRequestDto;
import ru.practicum.explorewithme.dto.output.UserDto;
import ru.practicum.explorewithme.model.User;

import java.util.ArrayList;
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
        List<UserDto> target = new ArrayList<>();
        users.forEach(u -> target.add(toDto(u)));
        return target;
    }
}
