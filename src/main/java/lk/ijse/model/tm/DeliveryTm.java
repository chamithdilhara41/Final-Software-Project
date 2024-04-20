package lk.ijse.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class DeliveryTm {
    private String deliveryId;
    private String date;
    private String orderId;
    private String vehicleNo;
}
