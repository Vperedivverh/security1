package ru.kata.spring.boot_security.demo.service;



import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service("UserServiceImpl")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUser(int id) {
        return userRepository.getById(id);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
//        User createdUser = getUser(user.getId());
//        createdUser.setId(user.getId());
//        createdUser.setName(user.getName());
//        createdUser.setSurname(user.getSurname());
//        createdUser.setAddress(user.getAddress());
//        createdUser.setFn(user.getFn());
//        createdUser.setSurname(user.getUsername());
//        createdUser.setPassword(user.getPassword()  );
//        createdUser.setRoles(user.getRoles());
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User getByUsername(String email) {
        return userRepository.findByUsername(email);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userRepository.delete(userRepository.getById(id));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with email" + username + " not found"));
        }
        return new org.springframework.security.core.userdetails
                .User(user.getUsername(), user.getPassword(), rolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> rolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r->new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }


}
