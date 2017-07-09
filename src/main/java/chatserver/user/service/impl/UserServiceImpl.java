package chatserver.user.service.impl;

import chatserver.configuration.CachingConfig;
import chatserver.user.exception.UserAlreadyExistsException;
import chatserver.user.exception.UserNotFoundException;
import chatserver.user.model.User;
import chatserver.user.repository.UserRepository;
import chatserver.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(String name) {
        boolean existsByName = userRepository.existsByName(name);
        if (existsByName) {
            throw new UserAlreadyExistsException();
        }
        return userRepository.save(new User(name));
    }

    @Override
    @Cacheable(value = CachingConfig.USERS)
    public User getUserByName(String name) {
        return userRepository.findByName(name).orElseThrow(UserNotFoundException::new);
    }
}
