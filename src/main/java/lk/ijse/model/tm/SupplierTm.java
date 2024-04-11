package lk.ijse.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class SupplierTm {
    private int supplierId;
    private String supplierName;
    private String supplierAddress;
    private String supplierContact;
    private String supplierGender;
}
