package ru.practicum.explorewithme.service.user;

import ru.practicum.explorewithme.dto.in.create.NewUserRequestDto;
import ru.practicum.explorewithme.dto.out.UserDto;

public interface UserService {

    UserDto createUser(NewUserRequestDto userDto);
}
