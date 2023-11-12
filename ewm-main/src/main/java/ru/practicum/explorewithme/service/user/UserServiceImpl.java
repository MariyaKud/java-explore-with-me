package ru.practicum.explorewithme.service.user;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.dto.input.create.NewUserRequestDto;
import ru.practicum.explorewithme.dto.output.UserDto;
import ru.practicum.explorewithme.exeption.NotFoundException;
import ru.practicum.explorewithme.model.QUser;
import ru.practicum.explorewithme.model.User;
import ru.practicum.explorewithme.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements AdminUserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto createUser(NewUserRequestDto userDto) {
        final User newUser = userMapper.fromDto(userDto);
        userRepository.save(newUser);
        return userMapper.toDto(newUser);
    }

    @Override
    @Transactional
    public Boolean deleteUserById(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId, User.class));
        userRepository.delete(user);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from > 0 ? from  / size : 0, size);

        BooleanBuilder builder = new BooleanBuilder();
        if (!ids.isEmpty()) {
            builder.and(QUser.user.id.in(ids));
        }
        Iterable<User> foundUsers = userRepository.findAll(builder, pageable);
        return userMapper.mapToUserDto(foundUsers);
    }
}
