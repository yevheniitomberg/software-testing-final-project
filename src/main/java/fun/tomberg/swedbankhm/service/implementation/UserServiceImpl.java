package fun.tomberg.swedbankhm.service.implementation;

import fun.tomberg.swedbankhm.entity.User;
import fun.tomberg.swedbankhm.repository.UserRepository;
import fun.tomberg.swedbankhm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.LinkedList;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAndEnabledTrue(email);
        if (user == null) {
            throw new UsernameNotFoundException("User with this email not found!");
        }
        return user;
    }

    @Override
    public User saveUser(User user) {
        user.setEnabled(true);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User editUser(User user) {
        return userRepository.save(getCurrentUser().setForeignParams(user));
    }

    @Override
    public User disableUser(User user) {
        user.setEnabled(false);
        return userRepository.save(user);
    }

    @Override
    public User switchUserStatus(User user, BindingResult bindingResult, int id) {
        if (user.isEnabled()) {
            user.setEmail(userRepository.findById(id).get().getEmail());
            return disableUser(user);
        } else {
            LinkedList<User> users = userRepository.findAllByEmail(userRepository.findById(user.getId()).get().getEmail());
            for (User user1 : users) {
                if (user1.isEnabled()) {
                    bindingResult.addError(new ObjectError("switchStatusError", "Cannot switch status of user because user with this email is already active"));
                    return user;
                }
            }
        }
        user.setEnabled(true);
        return user;
    }

    @Override
    public User adminSaving(User user, User other) {
        if (!userRepository.findById(user.getId()).get().getPassword().equals(other.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(other.getPassword()));
            return userRepository.save(user);
        }
        return userRepository.save(user);
    }

    @Override
    public boolean isAlreadyExists(String email) {
        LinkedList<User> users = userRepository.findAllByEmail(email);
        if (users == null) {
            return false;
        } else {
            for (User user: users) {
                if (user.isEnabled()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public User getCurrentUser() {
        return userRepository.findByEmailAndEnabledTrue(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Override
    public BindingResult getErrors(String checkMode, User user, BindingResult bindingResult) {
        if (checkMode.equals("registration")) {
            if (!user.isPassesMatches()) {
                bindingResult.addError(new ObjectError("pass_not_eq", "Passwords do not match"));
            }
            if (isAlreadyExists(user.getEmail())) {
                bindingResult.addError(new ObjectError("email_already_exist", "This email is already registered"));
            }
        }
        return bindingResult;
    }
}
