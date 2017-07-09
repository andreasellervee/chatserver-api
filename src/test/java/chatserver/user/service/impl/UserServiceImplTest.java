package chatserver.user.service.impl;

import chatserver.user.exception.UserAlreadyExistsException;
import chatserver.user.exception.UserNotFoundException;
import chatserver.user.model.User;
import chatserver.user.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class UserServiceImplTest {

    public static final String ANDREAS = "Andreas";

    @Autowired
    private UserService userService;

    private User andreas;

    @Before
    public void setup() {
        andreas = userService.createUser(ANDREAS);
    }

    @Test
    public void createUser() throws Exception {
        assertNotNull(andreas.getId());
        assertEquals(ANDREAS, andreas.getName());
    }

    @Test
    public void createUser_nameAlreadyExists() {
        try {
            userService.createUser(ANDREAS);
        } catch (Exception e) {
            assertTrue(e instanceof UserAlreadyExistsException);
        }
    }

    @Test
    public void getUserByName() {
        User userByName = userService.getUserByName(ANDREAS);

        assertEquals(andreas.getId(), userByName.getId());
        assertEquals(andreas.getName(), userByName.getName());
    }

    @Test
    public void getUserByName_notExists() {
        try {
            userService.getUserByName("Does_Not_Exist");
        } catch (Exception e) {
            assertTrue(e instanceof UserNotFoundException);
        }
    }

}