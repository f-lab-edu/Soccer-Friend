package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soccerfriend.dto.PointChargePayment;
import soccerfriend.dto.PointChargePayment.PayerType;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.mapper.PointChargePaymentMapper;

import static soccerfriend.dto.PointChargePayment.PayerType.*;
import static soccerfriend.exception.ExceptionInfo.*;

@Service
@RequiredArgsConstructor
public class PointChargePaymentService {

    private final PointChargePaymentMapper mapper;
    private final MemberService memberService;
    private final StadiumOwnerService stadiumOwnerService;

    /**
     * point 충전정보를 기록합니다.
     *
     * @param payerType          point를 충전하는 주체의 종류
     * @param id                 point를 충전하는 주체의 id
     * @param pointChargePayment point 충전결제정보
     */
    @Transactional
    public void chargePoint(PayerType payerType, int id, PointChargePayment pointChargePayment) {
        if (!pay(payerType, id, pointChargePayment)) {
            throw new BadRequestException(PAYMENT_FAIL);
        }
        if (payerType == MEMBER) {
            PointChargePayment memberPayment = PointChargePayment.builder()
                                                                 .payerType(MEMBER)
                                                                 .payerId(id)
                                                                 .price(pointChargePayment.getPrice())
                                                                 .pg(pointChargePayment.getPg())
                                                                 .paymentType(pointChargePayment.getPaymentType())
                                                                 .build();

            mapper.insert(memberPayment);

            memberService.increasePoint(id, pointChargePayment.getPrice());
        }
        else if (payerType == STADIUM_OWNER) {
            PointChargePayment stadiumOwnerPayment = PointChargePayment.builder().
                                                                       payerType(STADIUM_OWNER)
                                                                       .payerId(id)
                                                                       .price(pointChargePayment.getPrice())
                                                                       .pg(pointChargePayment.getPg())
                                                                       .paymentType(pointChargePayment.getPaymentType())
                                                                       .build();

            mapper.insert(stadiumOwnerPayment);
            stadiumOwnerService.increasePoint(id, pointChargePayment.getPrice());
        }
        else {
            throw new BadRequestException(PAYER_TYPE_NOT_EXIST);
        }
    }

    /**
     * 결제를 진행합니다. 이는 가상으로 결제를 하는 메소드를 구현한것입니다.
     *
     * @param payerType          point를 충전하는 주체의 종류
     * @param id                 point를 충전하는 주체의 id
     * @param pointChargePayment point 충전결제정보
     */
    public boolean pay(PayerType payerType, int id, PointChargePayment pointChargePayment) {
        // 가상으로 결제로직 구현. 현재 결제에 성공했다고 가정
        return true;
    }
}
