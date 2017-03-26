package com.bgh.myopeninvoice.db.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by bcavlin on 14/03/17.
 */
@Entity
@Table(name = "ATTACHMENT", schema = "INVOICE", catalog = "INVOICEDB")
public class AttachmentEntity implements Serializable {
    private Integer attachmentId;
    private Integer invoiceId;
    private byte[] file;
    private String filename;
    private InvoiceEntity invoiceByInvoiceId;

    @Id
    @GeneratedValue
    @Column(name = "ATTACHMENT_ID", nullable = false)
    public Integer getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }

    @Basic
    @Column(name = "INVOICE_ID", nullable = false)
    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    @Lob
    @Column(name = "FILE", nullable = false)
    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @Basic
    @Column(name = "FILENAME", nullable = false, length = 255)
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttachmentEntity that = (AttachmentEntity) o;

        if (attachmentId != null ? !attachmentId.equals(that.attachmentId) : that.attachmentId != null) return false;
        if (invoiceId != null ? !invoiceId.equals(that.invoiceId) : that.invoiceId != null) return false;
        if (!Arrays.equals(file, that.file)) return false;
        if (filename != null ? !filename.equals(that.filename) : that.filename != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = attachmentId != null ? attachmentId.hashCode() : 0;
        result = 31 * result + (invoiceId != null ? invoiceId.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(file);
        result = 31 * result + (filename != null ? filename.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "INVOICE_ID", referencedColumnName = "INVOICE_ID", nullable = false, insertable = false, updatable = false)
    public InvoiceEntity getInvoiceByInvoiceId() {
        return invoiceByInvoiceId;
    }

    public void setInvoiceByInvoiceId(InvoiceEntity invoiceByInvoiceId) {
        this.invoiceByInvoiceId = invoiceByInvoiceId;
    }

    @Override
    public String toString() {
        return "AttachmentEntity{" +
                "attachmentId=" + attachmentId +
                ", invoiceId=" + invoiceId +
                ", filename='" + filename + '\'' +
                '}';
    }
}