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

package com.bgh.myopeninvoice.db.domain;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

/**
 * Created by bcavlin on 14/03/17.
 */
@Data
@Entity
@Table(name = "USER", schema = "INVOICE")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID", nullable = false)
    private Integer userId;

    @Basic
    @Column(name = "USERNAME", nullable = false, length = 50)
    private String username;

    @Basic
    @Column(name = "PASSWORD_HASH", nullable = true, length = 200)
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_LOGGED_DATE", nullable = false)
    private Date lastLogged;

    @Basic
    @Column(name = "ENABLED", nullable = false)
    private Boolean enabled;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "userByUserId")
    private Collection<ContactEntity> contactByUserId;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "userByUserId")
    private Collection<UserRoleEntity> userRoleByUserId;

}