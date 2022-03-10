package newapp.domain.dao;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import newapp.domain.entity.QShopEntity;
import newapp.domain.entity.ShopEntity;
import newapp.domain.repository.ShopRepository;
import newapp.global.common.support.QueryDslSupport;
import newapp.global.util.ParamUtil;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ShopDao extends QueryDslSupport {

  private final ShopRepository shopRepository; // DI

  QShopEntity qShopEntity = QShopEntity.shopEntity;

  public String findByName(String name) {
    return shopRepository.findByName(name);
  }

  public JPAQuery<ShopEntity> selectByName(Map<String, Object> param) {

    String name = ParamUtil.getParam(param, "name");

    JPAQuery<ShopEntity> query = jpaQuery
      .selectFrom(qShopEntity)
      // .where(likeOpt(qShopEntity.name, name))
      .orderBy(qShopEntity.id.desc())
      ;

    return query;
  }

}
