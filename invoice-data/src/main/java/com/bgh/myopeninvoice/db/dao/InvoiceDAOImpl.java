package com.bgh.myopeninvoice.db.dao;

import com.bgh.myopeninvoice.db.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by bcavlin on 21/03/17.
 */
@Repository("invoiceDAO")
public class InvoiceDAOImpl implements InvoiceDAO {

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private TaxRepository taxRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private ContactsRepository contactsEntity;

    @Autowired
    private CompaniesRepository companiesRepository;

    @Override
    public ContactsRepository getContactsRepository() {
        return contactsEntity;
    }

    @Override
    public CompaniesRepository getCompaniesRepository() {
        return companiesRepository;
    }

    @Override
    public TaxRepository getTaxRepository() {
        return taxRepository;
    }

    @Override
    public UsersRepository getUsersRepository() {
        return usersRepository;
    }

    @Override
    public RolesRepository getRolesRepository() {
        return rolesRepository;
    }

    @Transactional
    @Override
    public void saveUserRolesEntity(UsersEntity usersEntity, List<RolesEntity> targetRoles) {
        List<UserRoleEntity> add = new ArrayList<>();
        List<UserRoleEntity> remove = new ArrayList<>();
        //first add new roles if not already there
        for (RolesEntity targetRole : targetRoles) {
            boolean found = false;
            for (UserRoleEntity currentRole : usersEntity.getUserRolesByUserId()) {
                if(currentRole.getRolesByRoleId().getRoleId().equals(targetRole.getRoleId())){
                    found = true;
                }
            }
            if(!found){
                UserRoleEntity newRole = new UserRoleEntity();
                newRole.setDateAssigned(new Date());
                newRole.setUsersByUserId(usersEntity);
                newRole.setRolesByRoleId(targetRole);
                add.add(newRole);
            }
        }
        //then delete those that are extra
        for (UserRoleEntity currentRole : usersEntity.getUserRolesByUserId()) {
            boolean found = false;
            for (RolesEntity targetRole : targetRoles) {
                if(currentRole.getRolesByRoleId().getRoleId().equals(targetRole.getRoleId())){
                    found = true;
                }
            }
            if(!found){
                remove.add(currentRole);
            }
        }

        for (UserRoleEntity userRoleEntity : remove) {
            userRoleRepository.delete(userRoleEntity.getUserRoleId());
        }
        for (UserRoleEntity userRoleEntity : add) {
            userRoleRepository.save(userRoleEntity);
        }
    }
}