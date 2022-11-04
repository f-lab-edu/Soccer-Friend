package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import soccerfriend.dto.PointChargePayment;


@Mapper
public interface PointChargePaymentMapper {
    public void insert(PointChargePayment pointChargePayment);
}
