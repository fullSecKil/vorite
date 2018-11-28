package com.zui.vorite.details;

import com.zui.vorite.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dusk
 */
@Service
public class CustomUserService implements UserDetailsService {

    private UserService userService;

    @Autowired
    void getUserService(UserService userService){
        this.userService = userService;
    }

    final static private Logger log = LoggerFactory.getLogger(CustomUserService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.zui.vorite.pojo.User proveUser = userService.getUser(username);
        List<GrantedAuthority> grantedAuthorities = Arrays.stream(proveUser.getRoles().split(",")).map(r->new SimpleGrantedAuthority("ROLE_"+r)).collect(Collectors.toList());
        return new User(proveUser.getUsername(), proveUser.getPassword(), grantedAuthorities);
    }
}
