package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        storageDao.clear();
    }

    @Test
    void register_validUser_ok() {
        User user = new User("validLogin", "password", 19);
        registrationService.register(user);
        User actual = storageDao.get("validLogin");
        User expected = new User("validLogin", "password", 19);
        assertEquals(actual, expected);
    }

    @Test
    void register_boundUser_ok() {
        User user = new User("123456", "123456", 18);
        registrationService.register(user);
        User actual = storageDao.get("123456");
        User expected = new User("123456", "123456", 18);
        assertEquals(actual, expected);
    }

    @Test
    void register_sameUser_notOk() {
        User user1 = new User("ValidLogin", "password", 18);
        User user2 = new User("ValidLogin", "password", 18);
        storageDao.add(user1);
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_edgeUserLogin_notOk() {
        User user = new User("12345", "password", 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyUserLogin_notOk() {
        User user = new User("", "password", 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortUserLogin_notOk() {
        User user = new User("123", "password", 19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullUserLogin_notOk() {
        User user = new User(null, "password", 19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortUserPassword_notOk() {
        User user = new User("validLogin", "123", 19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_edgeUserPassword_notOk() {
        User user = new User("validLogin", "12345", 19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyUserPassword_notOk() {
        User user = new User("validLogin", "", 19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullUserPassword_notOk() {
        User user = new User("validLogin", null, 19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_underMinUserAge_notOk() {
        User user = new User("validLogin", "password", 17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeUserAge_notOk() {
        User user = new User("validLogin", "password", -17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullUserAge_notOk() {
        User user = new User("validLogin", "password", null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
