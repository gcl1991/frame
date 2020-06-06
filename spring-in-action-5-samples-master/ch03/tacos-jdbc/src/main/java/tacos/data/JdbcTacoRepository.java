package tacos.data;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import tacos.Ingredient;
import tacos.Taco;

@Repository
public class JdbcTacoRepository implements TacoRepository {

  private JdbcTemplate jdbc;

  // 为什么此处没有自动连线注解,此注解不写目前看来也可以实现自动注入,所以何时用Autowired
  @Autowired
  public JdbcTacoRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  @Override
  public Taco save(Taco taco) {
    System.out.println("接收到taco："+taco);
    long tacoId = saveTacoInfo(taco);
    taco.setId(tacoId);

    for (Ingredient ingredient : taco.getIngredients()) {
      saveIngredientToTaco(ingredient, tacoId);
    }
    return taco;
  }

  private long saveTacoInfo(Taco taco) {
    taco.setCreatedAt(new Date());
    PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
            "insert into Taco (name, createdAt) values (?, ?)",
            Types.VARCHAR,
            Types.TIMESTAMP
    ); // 使用给定的SQL和JDBC类型创建一个新工厂
    PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
            Arrays.asList(
                    taco.getName(),
                    new Timestamp(taco.getCreatedAt().getTime())
            )
    );// 为给定参数返回一个新的PreparedStatementCreator
    System.out.println("getCreatedAt:"+taco.getCreatedAt()); // Mon May 11 21:51:13 CST 2020
    System.out.println("getTime:"+taco.getCreatedAt().getTime());//1589205073272
    System.out.println("Timestamp:"+new Timestamp(taco.getCreatedAt().getTime()));//2020-05-11 21:51:13.272

    // KeyHolder接口的标准实现，用于保存自动生成的键（可能由JDBC插入语句返回）。为每个插入操作创建此类的实例，并将其传递给相应的JdbcTemplate或 SqlUpdate方法。
    KeyHolder keyHolder = new GeneratedKeyHolder(); // 使用默认列表创建一个新的GeneratedKeyHolder
    jdbc.update(psc, keyHolder);

    return keyHolder.getKey().longValue();
  }

  private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
    jdbc.update(
        "insert into Taco_Ingredients (taco, ingredient) values (?, ?)",
        tacoId,
        ingredient.getId()
    );
  }

}
