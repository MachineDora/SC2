package com.example.cinema.data.sales;
import com.example.cinema.po.OrderForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Service
public interface OrderFormMapper {
    int insertOrderForm(OrderForm orderForm);
    int insertOrderFormAndTickets(@Param("orderFormId") int orderFormId,@Param("ticketId") List<Integer> ticketId);

    OrderForm selectOrderFormById(int id);

    List<OrderForm> selectOrderFormByUserId(int userId);

    int updateOrderFormState(@Param("orderFormId") int orderFormId,@Param("state") int state);
}
