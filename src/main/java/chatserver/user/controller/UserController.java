package chatserver.user.controller;

import chatserver.user.model.User;
import chatserver.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public User createUser(@RequestBody String username) {
        return userService.createUser(username);
    }

}
