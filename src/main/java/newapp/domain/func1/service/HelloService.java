package newapp.domain.func1.service;

import lombok.RequiredArgsConstructor;
import newapp.domain.entity.ShopEntity;
import newapp.domain.func1.dao.HelloDao;
import newapp.domain.repository.ShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class HelloService {

  private final HelloDao helloDao;
  private final ShopRepository shopRepository;

  public List<ShopEntity> selectByName(Map<String, Object> param) {
    return helloDao.selectByName(param);
  }

  @Transactional
  public void addShop() {

    ShopEntity shop = new ShopEntity();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
    String now = LocalDateTime.now().format(formatter);
    shop.setName("가계_"+now);
    shop.setAddress("서울_"+now);

    shopRepository.save(shop);
  }
}
