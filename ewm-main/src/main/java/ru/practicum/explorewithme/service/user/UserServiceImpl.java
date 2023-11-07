package ru.practicum.explorewithme.service.user;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.dto.in.create.NewUserRequestDto;
import ru.practicum.explorewithme.dto.out.UserDto;
import ru.practicum.explorewithme.exeption.NotFoundException;
import ru.practicum.explorewithme.model.QUser;
import ru.practicum.explorewithme.model.User;
import ru.practicum.explorewithme.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
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
        Pageable pageable = PageRequest.of(from > 0 ? from  / size : 0, size, Sort.by("id"));

        BooleanExpression byUserIds = QUser.user.id.in(ids);
        Iterable<User> foundUsers = userRepository.findAll(byUserIds,pageable);
        return userMapper.mapToUserDto(foundUsers);
    }
}
