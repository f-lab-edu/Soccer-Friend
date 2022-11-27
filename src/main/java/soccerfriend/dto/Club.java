package soccerfriend.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club {

    private int id;

    @NotNull
    @Size(min = 2, max = 20)
    private String name;

    private int leader;

    @NotNull
    @Min(0)
    @Max(25)
    private int addressId;

    private int point;

    @NotNull
    @Min(0)
    private int monthlyFee;

    @NotNull
    @Min(0)
    @Max(28)
    private int paymentDay;

    private int bulletinNum;

    @Builder
    public Club(String name, int addressId, int monthlyFee, int paymentDay) {
        this.name = name;
        this.addressId = addressId;
        this.monthlyFee = monthlyFee;
        this.paymentDay = paymentDay;
    }
}
