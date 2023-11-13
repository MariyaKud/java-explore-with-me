package ru.practicum.service.user;

import ru.practicum.dto.input.create.NewUserRequestDto;
import ru.practicum.dto.output.UserDto;

import java.util.List;

public interface AdminUserService {

    /**
     * Добавление нового пользователя
     *
     * @param userDto Данные добавляемого пользователя
     * @return 201 Пользователь зарегистрирован
     * 400 Запрос составлен не корректно
     * 409 Нарушение целостности данных
     */
    UserDto createUser(NewUserRequestDto userDto);

    /**
     * Удаление пользователя
     *
     * @param userId идентификатор пользователя
     * @return 204 пользователь удален
     * 404 пользователь не найден
     */
    Boolean deleteUserById(long userId);

    /**
     * Возвращает информацию обо всех пользователях (учитываются параметры ограничения выборки),
     * либо о конкретных (учитываются указанные идентификаторы)
     *
     * @param ids  id пользователей
     * @param from количество элементов, которые нужно пропустить для формирования текущего набора
     * @param size количество элементов в наборе
     * @return 200 Пользователи найдены
     * 400 Запрос составлен некорректно
     */
    List<UserDto> getUsers(List<Long> ids, Integer from, Integer size);
}
