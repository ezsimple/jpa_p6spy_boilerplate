package newapp.domain.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import newapp.domain.entity.QShopEntity;
import newapp.domain.entity.ShopEntity;
import newapp.global.support.QueryDslSupport;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
@RequiredArgsConstructor
public class ShopDao extends QueryDslSupport {

  private final ShopRepository shopRepository; // DI

  QShopEntity qShopEntity = QShopEntity.shopEntity;

  public String findByName(String name) {
    return shopRepository.findByName(name);
  }

  public JPAQuery<ShopEntity> selectByName(HashMap<String, Object> param) {

    String name = String.valueOf(param.get("name"));

    JPAQuery<ShopEntity> query = jpaQuery.select(
        Projections.bean(
          ShopEntity.class
          , qShopEntity.id
          , qShopEntity.name
          , qShopEntity.address
        )
      ).from(qShopEntity)
      .where(likeOpt(qShopEntity.name, name));

    return query;
  }

}
