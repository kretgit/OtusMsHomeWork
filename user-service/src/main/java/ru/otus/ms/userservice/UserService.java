package ru.otus.ms.userservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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

}
