package lk.ijse.model;

public class Supplier {
    private String supplierId;
    private String supplierName;
    private String supplierAddress;
    private String supplierContact;
    private String supplierGender;

    public Supplier() {
    }

    public Supplier(String supplierId, String supplierName, String supplierAddress, String supplierContact, String supplierGender) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.supplierAddress = supplierAddress;
        this.supplierContact = supplierContact;
        this.supplierGender = supplierGender;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getSupplierContact() {
        return supplierContact;
    }

    public void setSupplierContact(String supplierContact) {
        this.supplierContact = supplierContact;
    }

    public String getSupplierGender() {
        return supplierGender;
    }

    public void setSupplierGender(String supplierGender) {
        this.supplierGender = supplierGender;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierId='" + supplierId + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", supplierAddress='" + supplierAddress + '\'' +
                ", supplierContact='" + supplierContact + '\'' +
                ", supplierGender='" + supplierGender + '\'' +
                '}';
    }
}
