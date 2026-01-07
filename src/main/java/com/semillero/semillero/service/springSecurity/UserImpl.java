package com.semillero.semillero.service.springSecurity;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.semillero.semillero.models.User;
import com.semillero.semillero.repository.IUserRepository;
import com.semillero.semillero.repository.IUserRoleRepository;

@Service
public class UserImpl implements UserDetailsService {

    private final IUserRepository userRepository;
    private final IUserRoleRepository userRoleRepository;    

    public UserImpl(IUserRepository userRepository, IUserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.getByUserName(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        var userRols = userRoleRepository.getRolesByUser(user.getId());

        return new UserDetailsImpl(user, userRols);


    }

}
