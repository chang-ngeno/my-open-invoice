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

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by bcavlin on 14/03/17.
 */
@Entity
@Table(name = "COMPANIES", schema = "INVOICE", catalog = "INVOICEDB")
public class CompaniesEntity implements Serializable {
    private Integer companyId;
    private String companyName;
    private String shortName;
    private String addressLine1;
    private String addressLine2;
    private String phone1;
    private Boolean ownedByMe;
    private String businessNumber;
    private byte[] content;
    private Collection<CompanyContactEntity> companyContactsByCompanyId;
    private Collection<ContractsEntity> contractsByCompanyId;
    private Collection<ContractsEntity> contractsByCompanyId_0;
    private Collection<InvoiceEntity> invoicesByCompanyId;
    private Integer weekStart;

    @Id
    @GeneratedValue
    @Column(name = "COMPANY_ID", nullable = false)
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Basic
    @Column(name = "COMPANY_NAME", nullable = false, length = 255, unique = true)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Basic
    @Column(name = "SHORT_NAME", nullable = false, length = 10, unique = true)
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Basic
    @Column(name = "ADDRESS_LINE1", nullable = false, length = 500)
    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    @Basic
    @Column(name = "ADDRESS_LINE2", nullable = true, length = 500)
    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    @Basic
    @Column(name = "PHONE1", nullable = true, length = 20)
    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    @Basic
    @Column(name = "OWNED_BY_ME", nullable = false)
    public Boolean getOwnedByMe() {
        return ownedByMe;
    }

    public void setOwnedByMe(Boolean ownedByMe) {
        this.ownedByMe = ownedByMe;
    }

    @Basic
    @Column(name = "BUSINESS_NUMBER", nullable = true, length = 30)
    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    @Lob
    @Column(name = "CONTENT", nullable = true)
    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Basic
    @Column(name = "WEEK_START", nullable = false)
    public Integer getWeekStart() {
        return weekStart;
    }

    public void setWeekStart(Integer weekStart) {
        this.weekStart = weekStart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompaniesEntity that = (CompaniesEntity) o;

        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (companyName != null ? !companyName.equals(that.companyName) : that.companyName != null) return false;
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;
        if (addressLine1 != null ? !addressLine1.equals(that.addressLine1) : that.addressLine1 != null) return false;
        if (addressLine2 != null ? !addressLine2.equals(that.addressLine2) : that.addressLine2 != null) return false;
        if (phone1 != null ? !phone1.equals(that.phone1) : that.phone1 != null) return false;
        if (ownedByMe != null ? !ownedByMe.equals(that.ownedByMe) : that.ownedByMe != null) return false;
        if (businessNumber != null ? !businessNumber.equals(that.businessNumber) : that.businessNumber != null)
            return false;
        if (!Arrays.equals(content, that.content)) return false;
        if (weekStart != null ? !weekStart.equals(that.weekStart) : that.weekStart != null) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = companyId != null ? companyId.hashCode() : 0;
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (addressLine1 != null ? addressLine1.hashCode() : 0);
        result = 31 * result + (addressLine2 != null ? addressLine2.hashCode() : 0);
        result = 31 * result + (phone1 != null ? phone1.hashCode() : 0);
        result = 31 * result + (ownedByMe != null ? ownedByMe.hashCode() : 0);
        result = 31 * result + (businessNumber != null ? businessNumber.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(content);
        result = 31 * result + (weekStart != null ? weekStart.hashCode() : 0);

        return result;
    }

    @Transient
    public String getContactsToList() {
        StringBuilder sb = new StringBuilder();
        for (CompanyContactEntity companyContactEntity : companyContactsByCompanyId) {
            if (sb.length() > 0) sb.append(",");
            sb.append(companyContactEntity.getContactsByContactId().getContactId());
        }
        return sb.toString();
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "companiesByCompanyId")
    public Collection<CompanyContactEntity> getCompanyContactsByCompanyId() {
        return companyContactsByCompanyId;
    }

    public void setCompanyContactsByCompanyId(Collection<CompanyContactEntity> companyContactsByCompanyId) {
        this.companyContactsByCompanyId = companyContactsByCompanyId;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "companiesByContractSignedWith")
    public Collection<ContractsEntity> getContractsByCompanyId() {
        return contractsByCompanyId;
    }

    public void setContractsByCompanyId(Collection<ContractsEntity> contractsByCompanyId) {
        this.contractsByCompanyId = contractsByCompanyId;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "companiesByContractSignedWithSubcontract")
    public Collection<ContractsEntity> getContractsByCompanyId_0() {
        return contractsByCompanyId_0;
    }

    public void setContractsByCompanyId_0(Collection<ContractsEntity> contractsByCompanyId_0) {
        this.contractsByCompanyId_0 = contractsByCompanyId_0;
    }

    @OneToMany(mappedBy = "companiesByCompanyTo")
    public Collection<InvoiceEntity> getInvoicesByCompanyId() {
        return invoicesByCompanyId;
    }

    public void setInvoicesByCompanyId(Collection<InvoiceEntity> invoicesByCompanyId) {
        this.invoicesByCompanyId = invoicesByCompanyId;
    }

    public Integer calculateWeekEnd() {
        return weekStart == 1 ? 7 : weekStart - 1;
    }

    @Override
    public String toString() {
        return "CompaniesEntity{" +
                "companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", phone1='" + phone1 + '\'' +
                ", ownedByMe=" + ownedByMe +
                ", businessNumber='" + businessNumber + '\'' +
                ", weekStart='" + weekStart + '\'' +
                '}';
    }
}
