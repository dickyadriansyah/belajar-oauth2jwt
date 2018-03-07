package com.dicky.authserver.security;

import com.dicky.authserver.entity.Role;
import com.dicky.authserver.entity.User;
import com.dicky.authserver.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Component
@Transactional
public class CustomUserDetailsService implements UserDetailsService{

    private final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(final String login){
        log.debug("Authenticating {}", login);

        String lowerCase = login.toLowerCase();

        User userFromEntity;
        userFromEntity = userRepository.findByUsernameCaseInsensitive(lowerCase);

        if(userFromEntity == null){
            throw new UsernameNotFoundException("Maaf Pengguna dengan user \"+lowerCase+\n" +
                    "\"tidak terdaftar di database");

        }else if(!userFromEntity.isActivated()){
            throw new CustomUserNotActiveException("Maaf pengguna dengan user "+lowerCase+
                    "status tidak aktive harap hubungi admin");
        }

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(Role role: userFromEntity.getRoleList()){
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRole_name());
            grantedAuthorities.add(grantedAuthority);
        }

        return new org.springframework.security.core.userdetails.User(userFromEntity.getUsername(), userFromEntity.getPassword(), grantedAuthorities);
    }
}
