package ru.otus.ms.userservice;

import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.ms.common.model.user.User;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserServiceController {

    private final UserService userService;

    @GetMapping
    public List<User> getUsers(@RequestParam (value = "ids", required = false) List<String> ids) {
        return ObjectUtils.isEmpty(ids) ? userService.getActiveUsers() : userService.getUsersByIds(ids);
    }


}
