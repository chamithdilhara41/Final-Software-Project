package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Buyer {
    private String BuyerId;
    private String BuyerName;
    private String BuyerAddress;
    private String BuyerContactOffice;
    private String BuyerContactManager;
}
