package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soccerfriend.dto.PointChargePayment;
import soccerfriend.dto.PointChargePayment.PayerType;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.mapper.PointChargePaymentMapper;

import static soccerfriend.dto.PointChargePayment.PayerType.*;
import static soccerfriend.exception.ExceptionInfo.PAYER_TYPE_NOT_EXIST;

@Service
@RequiredArgsConstructor
public class PointChargePaymentService {

    private final PointChargePaymentMapper mapper;

    public void chargePoint(PayerType payerType, int id, PointChargePayment pointChargePayment) {
        if (payerType == MEMBER) {
            PointChargePayment memberPayment = PointChargePayment.builder().payerType(MEMBER).payerId(id)
                                                                 .price(pointChargePayment.getPrice())
                                                                 .pg(pointChargePayment.getPg())
                                                                 .paymentType(pointChargePayment.getPaymentType())
                                                                 .build();

            mapper.insert(memberPayment);
        }
        else if (payerType == STADIUM_OWNER) {
            PointChargePayment memberPayment = PointChargePayment.builder().payerType(STADIUM_OWNER).payerId(id)
                                                                 .price(pointChargePayment.getPrice())
                                                                 .pg(pointChargePayment.getPg())
                                                                 .paymentType(pointChargePayment.getPaymentType())
                                                                 .build();

            mapper.insert(memberPayment);
        }
        else {
            throw new BadRequestException(PAYER_TYPE_NOT_EXIST);
        }
    }
}
