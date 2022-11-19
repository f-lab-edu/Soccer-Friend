package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import soccerfriend.dto.OrderInfo;
import soccerfriend.exception.exception.NoPermissionException;
import soccerfriend.service.LoginService;
import soccerfriend.service.OrderInfoService;

import static soccerfriend.exception.ExceptionInfo.NO_ORDER_PERMISSION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-infos")
public class OrderInfoController {

    private final OrderInfoService orderInfoService;
    private final LoginService loginService;

    /**
     * 사용자가 주문을 진행합니다.
     *
     * @param orderInfo 주문 정보
     */
    @PostMapping
    public void order(@RequestBody OrderInfo orderInfo) {
        int memberId = loginService.getMemberId();
        orderInfoService.order(memberId, orderInfo);
    }

    /**
     * id를 통해 주문정보를 조회합니다.
     *
     * @param id 주문정보의 id
     * @return 주문 정보
     */
    @GetMapping("/{id}")
    public OrderInfo getOrderInfoById(@PathVariable int id) {
        int memberId = loginService.getMemberId();
        OrderInfo orderInfo = orderInfoService.getOrderInfoById(id);
        if (memberId != orderInfo.getMemberId()) {
            throw new NoPermissionException(NO_ORDER_PERMISSION);
        }

        return orderInfo;
    }
}
