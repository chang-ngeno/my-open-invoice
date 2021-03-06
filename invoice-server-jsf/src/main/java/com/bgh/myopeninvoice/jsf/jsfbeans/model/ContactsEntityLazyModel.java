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

package com.bgh.myopeninvoice.jsf.jsfbeans.model;

import com.bgh.myopeninvoice.db.repository.InvoiceDAO;
import com.bgh.myopeninvoice.db.model.ContactsEntity;
import com.bgh.myopeninvoice.db.model.QContactsEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by bcavlin on 17/03/17.
 */
public class ContactsEntityLazyModel extends LazyDataModel<ContactsEntity> {

    private static Logger logger = LoggerFactory.getLogger(ContactsEntityLazyModel.class);

    private List<ContactsEntity> contactsEntityList;

    private InvoiceDAO invoiceDAO;

    private Set<String> contactsFilter;

    public ContactsEntityLazyModel(InvoiceDAO invoiceDAO, Set<String> contactsFilter) {
        this.invoiceDAO = invoiceDAO;
        this.contactsFilter = contactsFilter;
    }

    @Override
    public List<ContactsEntity> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        contactsEntityList = new ArrayList<>();

        PageRequest pageRequest = new PageRequest(first * pageSize, pageSize);

        Sort.Direction direction = null;
        if (sortOrder == SortOrder.ASCENDING) {
            direction = Sort.Direction.ASC;
        } else if (sortOrder == SortOrder.DESCENDING) {
            direction = Sort.Direction.DESC;
        }
        try {
            if (direction != null && sortField != null) {
                pageRequest = new PageRequest(
                        first * pageSize,
                        pageSize,
                        new Sort(new Sort.Order(direction, sortField)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        BooleanExpression predicate = null;

        if (contactsFilter != null &&  contactsFilter.size() > 0){
            for (String contactId : contactsFilter) {
                if (predicate == null) {
                    predicate = QContactsEntity.contactsEntity.contactId.eq(Integer.valueOf(contactId));
                } else {
                    predicate = predicate.or(QContactsEntity.contactsEntity.contactId.eq(Integer.valueOf(contactId)));
                }
            }
        }

        Page<ContactsEntity> contactsEntityPage = null;

        if (predicate == null) {
            contactsEntityPage = invoiceDAO.getContactsRepository().findAll(pageRequest);
        } else {
            contactsEntityPage = invoiceDAO.getContactsRepository().findAll(predicate, pageRequest);
        }

        setRowCount((int) contactsEntityPage.getTotalElements());
        setPageSize(getPageSize());

        for (ContactsEntity contactsEntity : contactsEntityPage) {
            contactsEntityList.add(contactsEntity);
        }
        return contactsEntityList;
    }

    @Override
    public Object getRowKey(ContactsEntity object) {
        return object.getContactId();
    }

    @Override
    public ContactsEntity getRowData(String rowKey) {
        Integer id = Integer.valueOf(rowKey);
        if (contactsEntityList != null) {
            for (ContactsEntity contactsEntity : contactsEntityList) {
                if (id.equals(contactsEntity.getContactId())) {
                    return contactsEntity;
                }
            }
        }
        return null;
    }
}
