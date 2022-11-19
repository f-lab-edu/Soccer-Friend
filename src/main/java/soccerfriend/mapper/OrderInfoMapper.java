package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import soccerfriend.dto.OrderInfo;

@Mapper
public interface OrderInfoMapper {

    public void insert(OrderInfo orderInfo);

    public OrderInfo getOrderInfoById(int id);

    public OrderInfo getOrderInfoByOrderId(String orderId);

    public boolean isIdExist(int id);

    public boolean isOrderIdExist(String orderId);

    public void setConfirmedTrue(int id);
}
