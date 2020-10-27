// tag::core[]
package tacos.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import tacos.Taco;
import tacos.Order;

@Repository
public class JdbcOrderRepository implements OrderRepository {

  private SimpleJdbcInsert orderInserter;
  private SimpleJdbcInsert orderTacoInserter;
  private ObjectMapper objectMapper;

  @Autowired
  public JdbcOrderRepository(JdbcTemplate jdbc) {
    this.orderInserter = new SimpleJdbcInsert(jdbc)
        .withTableName("Taco_Order")
        .usingGeneratedKeyColumns("id");

    this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
        .withTableName("Taco_Order_Tacos");

    this.objectMapper = new ObjectMapper();
  }

  @Override
  public Order save(Order order) {
    System.out.println("接收到Order："+order);
    order.setPlacedAt(new Date());
    long orderId = saveOrderDetails(order);
    order.setId(orderId);
    List<Taco> tacos = order.getTacos();
    for (Taco taco : tacos) {
      saveTacoToOrder(taco, orderId);
    }

    return order;
  }

  private long saveOrderDetails(Order order) {
    @SuppressWarnings("unchecked")
    Map<String, Object> values = objectMapper.convertValue(order, Map.class);
    // 泛型方法，返回类型与Map.Class相同，用于从给定值到给定值类型的实例进行两步转换的便捷方法
    // 类似于首先将给定值序列化为JSON，然后将JSON数据绑定为给定类型的值
    System.out.println("Before");
    values.entrySet().forEach(x->{
      System.out.println("key:"+x.getKey());
      System.out.println("value:"+x.getValue());
    });
    values.put("placedAt", order.getPlacedAt()); // 因为ObjectMapper会将Date属性转换为long
    System.out.println("After");
    values.entrySet().forEach(x->{
      System.out.println("key:"+x.getKey());
      System.out.println("value:"+x.getValue());
    });

    long orderId = orderInserter.executeAndReturnKey(values).longValue();
    return orderId;
  }

  private void saveTacoToOrder(Taco taco, long orderId) {
    Map<String, Object> values = new HashMap<>();
    values.put("tacoOrder", orderId);
    values.put("taco", taco.getId());
    orderTacoInserter.execute(values);
  }
}
