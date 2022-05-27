package com.example.saat.services;

import com.example.saat.models.User;
import com.example.saat.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//    private UserRepository userRepository;
//
//    public CustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
//        com.example.saat.models.User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with provided username: " + username));
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
//    }
//    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
//        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
//    }
//}
@Service
public class CustomUserDetailsService{
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User loadUserByUserName(String username) throws UsernameNotFoundException{
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with provided username: " + username));
        return new User(user.getId(), user.getName(), user.getUsername(), user.getPassword());
    }
}
