package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soccerfriend.dto.OrderInfo;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.mapper.OrderInfoMapper;

import static soccerfriend.exception.ExceptionInfo.ORDER_INFO_NOT_EXIST;
import static soccerfriend.utility.CodeGenerator.getOrderIdRandomly;

@Service
@RequiredArgsConstructor
public class OrderInfoService {

    private final OrderInfoMapper mapper;
    private final MemberService memberService;

    /**
     * 새로운 주문정보를 추가합니다.
     *
     * @param memberId  주문을 하는 member의 id
     * @param orderInfo 새로 추가할 주문정보
     */
    public void order(int memberId, OrderInfo orderInfo) {
        OrderInfo newOrderInfo = OrderInfo.builder()
                                          .memberId(memberId)
                                          .amount(orderInfo.getAmount())
                                          .orderId(getOrderIdRandomly())
                                          .orderName(orderInfo.getOrderName())
                                          .confirmed(false)
                                          .build();

        mapper.insert(newOrderInfo);
    }

    /**
     * 해당 id의 주문정보가 조회하는지 확인합니다.
     *
     * @param id 주문정보의 id
     * @return 주문정보 존재유무
     */
    public boolean isIdExist(int id) {
        return mapper.isIdExist(id);
    }

    /**
     * 해당 orderId의 주문정보가 조회하는지 확인합니다.
     *
     * @param orderId 주문정보의 orderId
     * @return 주문정보 존재유무
     */
    public boolean isOrderIdExist(String orderId) {
        return mapper.isOrderIdExist(orderId);
    }

    /**
     * 해당 id의 주문정보를 반환합니다.
     *
     * @param id 주문정보의 id
     * @return 해당 id의 주문정보
     */
    public OrderInfo getOrderInfoById(int id) {
        if (!isIdExist(id)) {
            throw new BadRequestException(ORDER_INFO_NOT_EXIST);
        }

        return mapper.getOrderInfoById(id);
    }

    /**
     * 해당 orderId의 주문정보를 반환합니다.
     *
     * @param orderId 주문정보의 orderId
     * @return 해당 orderId의 주문정보
     */
    public OrderInfo getOrderInfoByOrderId(String orderId) {
        if (!isOrderIdExist(orderId)) {
            throw new BadRequestException(ORDER_INFO_NOT_EXIST);
        }

        return mapper.getOrderInfoByOrderId(orderId);
    }

    /**
     * 해당 주문정보의 결제가 승인되었음을 기록합니다.
     *
     * @param id 주문정보의 id
     */
    @Transactional
    public void paymentSubmitted(int id) {
        if (!isIdExist(id)) {
            throw new BadRequestException(ORDER_INFO_NOT_EXIST);
        }

        OrderInfo orderInfo = getOrderInfoById(id);
        int memberId = orderInfo.getMemberId();
        mapper.setConfirmedTrue(id);
        memberService.increasePoint(memberId, orderInfo.getAmount());
    }
}
