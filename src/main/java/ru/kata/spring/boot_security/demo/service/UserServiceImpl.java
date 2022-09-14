package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional(readOnly = true)
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
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setId(user.getId());
        user1.setUsername(user.getUsername());
        userRepository.saveAndFlush(user1);
    }

    @Override
    @Transactional(readOnly = true)

    public User getUser(int id) {
        return userRepository.getById(id);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        User user1 = userRepository.getUserByUsername(user.getUsername());
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        user1.setName(user.getName());
        user1.setSurname(user.getSurname());
        user1.setAddress(user.getAddress());
        user1.setRoles(user.getRoles());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setId(user.getId());
        user1.setUsername(user.getUsername());
        return userRepository.saveAndFlush(user1);
    }

    @Override
    @Transactional(readOnly = true)

    public User getByUsername(String email) {
        return userRepository.getUserByUsername(email);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userRepository.delete(userRepository.getById(id));
    }


}
//