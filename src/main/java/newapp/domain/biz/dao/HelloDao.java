package newapp.domain.biz.dao;

import lombok.RequiredArgsConstructor;
import newapp.domain.dao.ShopDao;
import newapp.domain.entity.ShopEntity;
import newapp.domain.repository.ShopRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class HelloDao {

  private final ShopRepository shopRepository;
  private final ShopDao shopDao;

  public List<ShopEntity> selectByName(Map<String, Object> param) {
    return shopDao.selectByName(param).fetch();
  }
}
