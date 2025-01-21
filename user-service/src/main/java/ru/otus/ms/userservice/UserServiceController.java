package ru.otus.ms.userservice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import ru.otus.ms.common.CommonController;
import ru.otus.ms.common.model.user.User;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserServiceController extends CommonController {

    private final UserService userService;

    @RequiredArgsConstructor
    @Getter
    @Setter
    public static class UpdateUserRq {
        private final String lastName;
        private final String firstName;
        private final String email;
        private final Boolean active;
    }

    @GetMapping
    public List<User> getUsers(@RequestParam (value = "ids", required = false) List<String> ids) {
        return ObjectUtils.isEmpty(ids) ? userService.getActiveUsers() : userService.getUsersByIds(ids);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") String id) {
        return userService.getUser(id);
    }

    @PostMapping
    public String addUser(@RequestBody UpdateUserRq rq) {
        return userService.addUser(rq);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") String id, @RequestBody UpdateUserRq rq) {
        return userService.updateUser(id, rq);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
    }


}
