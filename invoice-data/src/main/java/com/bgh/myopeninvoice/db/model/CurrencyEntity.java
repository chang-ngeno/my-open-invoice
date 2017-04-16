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

package com.bgh.myopeninvoice.db.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by bcavlin on 07/04/17.
 */
@Entity
@Table(name = "CURRENCY", schema = "INVOICE", catalog = "INVOICEDB")
public class CurrencyEntity implements Serializable {
    private Integer ccy;
    private String name;
    private String description;
    private Collection<ContractsEntity> contractsByCcy;
    private Collection<InvoiceEntity> invoicesByCcy;
    private Collection<InvoiceItemsEntity> invoiceItemsByCcy;

    @Id
    @GeneratedValue
    @Column(name = "CCY_ID", nullable = false)
    public Integer getCcy() {
        return ccy;
    }

    public void setCcy(Integer ccy) {
        this.ccy = ccy;
    }

    @Basic
    @Column(name = "NAME", nullable = false, length = 10)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "DESCRIPTION", nullable = false, length = 100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrencyEntity that = (CurrencyEntity) o;

        if (ccy != null ? !ccy.equals(that.ccy) : that.ccy != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ccy != null ? ccy.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "currencyByCcy")
    public Collection<ContractsEntity> getContractsByCcy() {
        return contractsByCcy;
    }

    public void setContractsByCcy(Collection<ContractsEntity> contractsByCcy) {
        this.contractsByCcy = contractsByCcy;
    }

    @OneToMany(mappedBy = "currencyByCcy")
    public Collection<InvoiceEntity> getInvoicesByCcy() {
        return invoicesByCcy;
    }

    public void setInvoicesByCcy(Collection<InvoiceEntity> invoicesByCcy) {
        this.invoicesByCcy = invoicesByCcy;
    }

    @Override
    public String toString() {
        return "CurrencyEntity{" +
                "ccy=" + ccy +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}