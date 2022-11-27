package soccerfriend.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club {

    private int id;

    @NotNull
    @Size(min = 2, max = 8)
    private String name;

    private int leader;

    @NonNull
    private int addressId;

    private int point;

    @NonNull
    @Min(0)
    private int monthlyFee;

    @NonNull
    @Size(min = 1, max = 31)
    private int paymentDay;

    private int bulletinNum;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    public Club(String name, int addressId, int monthlyFee, int paymentDay) {
        this.name = name;
        this.addressId = addressId;
        this.monthlyFee = monthlyFee;
        this.paymentDay = paymentDay;
    }
}
