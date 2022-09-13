package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
@Transactional
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        User user1 = new User();
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        user1.setName(user.getName());
        user1.setSurname(user.getSurname());
        user1.setAddress(user.getAddress());
        user1.setRoles(user.getRoles());
        user1.setFn(user.getFn());
        user1.setId(user.getId());
        user1.setUsername(user.getUsername());
        userRepository.saveAndFlush(user1);
    }
@Transactional
    @Override
    public User getUser(int id) {
        return userRepository.getById(id);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        User user1 = new User();
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        user1.setName(user.getName());
        user1.setSurname(user.getSurname());
        user1.setAddress(user.getAddress());
        user1.setRoles(user.getRoles());
        user1.setFn(user.getFn());
        user1.setId(user.getId());
        user1.setUsername(user.getUsername());
        return userRepository.saveAndFlush(user);
    }
@Transactional
    @Override
    public User getByUsername(String email) {
        return userRepository.getUserByUsername(email);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userRepository.delete(userRepository.getById(id));
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with email" + username + " not found"));
        }
        return new org.springframework.security.core.userdetails
                .User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}
