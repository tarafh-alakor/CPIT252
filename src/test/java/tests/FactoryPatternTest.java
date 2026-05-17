package tests;

import factory.UserFactory;
import model.*;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Tests the Factory Method Pattern.
 */
public class FactoryPatternTest {

    /*
     * UserFactory should be abstract.
     */
    @Test
    public void testUserFactoryIsAbstract() {

        assertTrue(
                Modifier.isAbstract(
                        UserFactory.class.getModifiers()
                )
        );
    }

    /*
     * User interface should exist.
     */
    @Test
    public void testUserIsInterface() {

        assertTrue(
                Modifier.isInterface(
                        User.class.getModifiers()
                )
        );
    }

    /*
     * UserFactory should contain createUser method.
     */
    @Test
    public void testFactoryMethodExists() {

        boolean passed = false;

        for (Method method
                : UserFactory.class.getDeclaredMethods()) {

            if (method.getName()
                    .equals("createUser")) {

                assertEquals(
                        1,
                        method.getParameterCount()
                );

                assertEquals(
                        User.class,
                        method.getReturnType()
                );

                passed = true;
            }
        }

        assertTrue(passed);
    }

    /*
     * Factory should create DoctorUser.
     */
    @Test
    public void testCreateDoctorUser() {

        UserFactory factory
                = UserFactory.forRole("Doctor");

        User user
                = factory.createUser("Sarah");

        assertNotNull(user);

        assertTrue(
                user instanceof DoctorUser
        );

        assertEquals(
                "Doctor",
                user.getRole()
        );
    }

    /*
     * Factory should create ParentUser.
     */
    @Test
    public void testCreateParentUser() {

        UserFactory factory
                = UserFactory.forRole("Parent");

        User user
                = factory.createUser("Tarafh");

        assertNotNull(user);

        assertTrue(
                user instanceof ParentUser
        );

        assertEquals(
                "Parent",
                user.getRole()
        );
    }

    /*
     * Factory should create TeacherUser.
     */
    @Test
    public void testCreateTeacherUser() {

        UserFactory factory
                = UserFactory.forRole(
                        "School Teacher"
                );

        User user
                = factory.createUser("Reem");

        assertTrue(
                user instanceof TeacherUser
        );
    }

    /*
     * Factory should reject invalid role.
     */
    @Test
    public void testInvalidRole() {

        assertThrows(
                IllegalArgumentException.class,
                () -> UserFactory.forRole(
                        "WrongRole"
                )
        );
    }

    /*
     * Factory should reject empty name.
     */
    @Test
    public void testEmptyName() {

        UserFactory factory
                = UserFactory.forRole("Doctor");

        assertThrows(
                IllegalArgumentException.class,
                () -> factory.createUser("")
        );
    }
}
