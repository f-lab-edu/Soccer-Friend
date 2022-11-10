package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import soccerfriend.dto.OrderInfo;
import soccerfriend.exception.exception.NoPermissionException;
import soccerfriend.service.AuthorizeService;
import soccerfriend.service.OrderInfoService;

import static soccerfriend.exception.ExceptionInfo.NO_ORDER_PERMISSION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-infos")
public class OrderInfoController {

    private final OrderInfoService orderInfoService;
    private final AuthorizeService authorizeService;

    @PostMapping
    public void order(@RequestBody OrderInfo orderInfo) {
        int memberId = authorizeService.getMemberId();
        orderInfoService.order(memberId, orderInfo);
    }

    @GetMapping("/{id}")
    public OrderInfo getOrderInfoById(@PathVariable int id) {
        int memberId = authorizeService.getMemberId();
        OrderInfo orderInfo = orderInfoService.getOrderInfoById(id);
        if (memberId != orderInfo.getMemberId()) {
            throw new NoPermissionException(NO_ORDER_PERMISSION);
        }

        return orderInfo;
    }
}
