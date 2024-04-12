package lk.ijse.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class BuyerTm {
    private String BuyerId;
    private String BuyerName;
    private String BuyerAddress;
    private String BuyerContactOffice;
    private String BuyerContactManager;
}
