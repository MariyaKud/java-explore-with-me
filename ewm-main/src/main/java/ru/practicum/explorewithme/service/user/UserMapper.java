package ru.practicum.explorewithme.service.user;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.dto.in.create.NewUserRequestDto;
import ru.practicum.explorewithme.dto.out.UserDto;
import ru.practicum.explorewithme.model.User;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getName());
    }

    public User fromDto(NewUserRequestDto dto) {
        return new User(null, dto.getName(), dto.getEmail());
    }
}
