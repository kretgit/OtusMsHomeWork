package ru.otus.ms.userservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import ru.otus.ms.common.exception.CommonException;
import ru.otus.ms.common.model.user.User;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    @Autowired
    UserConfiguration userConfiguration;

    private final Map<String, User> userMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        userMap.put("U100", new User("U100", "Admin", "Admin"));
        userMap.put("U101", new User("U101", "Kretov", "Alexey"));
        userMap.put("U102", new User("U102", "John", "Doe"));
        userMap.put("U103", new User("U103", "Li", "Yang"));
        userMap.put("U104", new User("U104", "Bred", "Pit"));
        userMap.put("U105", new User("U105", "Tom", "Jerry"));
    }

    public List<User> getActiveUsers() {
        return userMap.values().stream()
                .filter(User::isActive)
                .collect(Collectors.toList());
    }

    public List<User> getUsersByIds(Collection<String> ids) {
        List<User> users = new ArrayList<>();
        ids.forEach(id -> {
            User user = userMap.get(id);
            if (user != null) {
                users.add(user);
            }
        });

        log.info("{} users retrieved", users.size());
        return users;
    }

    public User getUser(String id) {
        User user = userMap.get(id);
        if (ObjectUtils.isEmpty(user)) {
            throw new CommonException("Не найден пользователь с ID = " + id);
        }
        return user;
    }

    public String addUser(UserServiceController.UpdateUserRq rq) {
        checkCreateRequest(rq);

        User user = User.builder()
                .lastName(rq.getLastName())
                .firstName(rq.getFirstName())
                .email(rq.getEmail())
                .active(ObjectUtils.isEmpty(rq.getActive()) ? true : rq.getActive())
                .build();

        log.info("user created : {}", user);
        return "ok";
    }

    public User updateUser(String id, UserServiceController.UpdateUserRq rq) {
        User existingUser = getUser(id);
        checkUpdateRequest(rq);

        log.info("user {} updated", id);
        return null;
    }

    public void deleteUser(String id) {
        User userForDelete = getUser(id);
        log.info("user {} deleted", id);
    }

    private void checkCreateRequest(UserServiceController.UpdateUserRq rq) {
        int maxCount = userConfiguration.getMaxCount();

        if (ObjectUtils.isEmpty(rq.getFirstName()) || ObjectUtils.isEmpty(rq.getLastName()) || ObjectUtils.isEmpty(rq.getEmail())) {
            throw new CommonException("Заполните все необходимые параметры: ФИО, почта");
        }

        if (userMap.size() >= maxCount) {
            throw new CommonException("Достигнуто максимальное количество пользователей: " + maxCount);
        }

        checkUpdateRequest(rq);
    }

    private void checkUpdateRequest(UserServiceController.UpdateUserRq rq) {
        boolean firstNameExceeded = paramExceeded(rq.getFirstName());
        boolean lastNameExceeded = paramExceeded(rq.getLastName());
        boolean emailExceeded = paramExceeded(rq.getEmail());

        if (firstNameExceeded || lastNameExceeded || emailExceeded) {
            throw new CommonException(
                    "Длина параметра не должна превышать " + userConfiguration.getMaxLenParam() + " символов");
        }
    }

    private boolean paramExceeded(String param) {
        return !ObjectUtils.isEmpty(param) && param.length() >= userConfiguration.getMaxLenParam();
    }

}
