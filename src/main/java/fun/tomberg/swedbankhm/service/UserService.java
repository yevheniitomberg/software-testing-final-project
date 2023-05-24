package fun.tomberg.swedbankhm.service;

import fun.tomberg.swedbankhm.entity.User;
import org.springframework.validation.BindingResult;

public interface UserService {
    User saveUser(User user);
    User editUser(User user);
    User disableUser(User user);
    User switchUserStatus(User user, BindingResult bindingResult, int id);
    User adminSaving(User user, User other);
    boolean isAlreadyExists(String email);
    User getCurrentUser();
    BindingResult getErrors(String checkMode, User user, BindingResult bindingResult);
}
