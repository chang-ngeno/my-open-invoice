/*
 * Copyright 2017 Branislav Cavlin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bgh.myopeninvoice.db.service;

/**
 * Created by bcavlin on 23/10/16.
 */


import com.bgh.myopeninvoice.db.model.UserRoleEntity;
import com.bgh.myopeninvoice.db.model.UsersEntity;
import com.bgh.myopeninvoice.db.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

/**
 * Created by bcavlin on 27/11/14.
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private UsersRepository usersRepository;

    private String passwordHashForEncryption;

    @Autowired
    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Verifying username [{}]", username);

        UserDetails userDetails = null;

        final Optional<UsersEntity> user = usersRepository.findByUsername(username);

        if (!user.isPresent()) {
            logger.error("Username [{}] not found!", username);
            throw new UsernameNotFoundException("Username or password have not been found");
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("Username [{}] has been found", user.toString());
            }

            if (!user.get().getEnabled()) {
                logger.error("Username [{}] has been disabled", username);
                throw new DisabledException("Username has been disabled");
            }

            Collection<GrantedAuthority> authorities = new ArrayList<>();

            if (user.get().getUserRolesByUserId().size() == 0) {
                logger.error("Username [{}] does not have any roles assigned", username);
                throw new AuthenticationServiceException("User does not have any roles assigned");
            } else {
                for (UserRoleEntity userRoleEntity : user.get().getUserRolesByUserId()) {
                    logger.info("Authority [{}] added for user [{}]", userRoleEntity.getRolesByRoleId()
                            .getRoleName(), username);
                    authorities.add(new SimpleGrantedAuthority(userRoleEntity.getRolesByRoleId().getRoleName()));
                }
            }

            userDetails = new User(user.get().getUsername(),
                    user.get().getPassword(),
                    user.get().getEnabled(),
                    true,
                    true,
                    true,
                    authorities);
        }

        return userDetails;
    }

    public void updateLastLoginDate(User user){
        final Optional<UsersEntity> byUsername = usersRepository.findByUsername(user.getUsername());
        if(byUsername.isPresent()) {
            byUsername.get().setLastLogged(new Date());
            usersRepository.save(byUsername.get());
        }

    }

    public String getPasswordHashForEncryption() {
        return passwordHashForEncryption;
    }

    public void setPasswordHashForEncryption(String passwordHashForEncryption) {
        this.passwordHashForEncryption = passwordHashForEncryption;
    }
}

