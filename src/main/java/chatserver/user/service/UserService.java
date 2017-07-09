package chatserver.user.service;

import chatserver.user.model.User;

public interface UserService {

    User createUser(String name);

    User getUserByName(String name);

}
