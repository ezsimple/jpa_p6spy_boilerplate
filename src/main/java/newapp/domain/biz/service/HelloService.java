package newapp.domain.biz.service;

import lombok.RequiredArgsConstructor;
import newapp.domain.entity.ShopEntity;
import newapp.domain.dao.HelloDao;
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
  public ShopEntity addShop() throws Exception {

    ShopEntity shop = new ShopEntity();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
    String now = LocalDateTime.now().format(formatter);
    shop.setName("가계_"+now);
    shop.setAddress("서울_"+now);

    return shopRepository.save(shop);
  }

  @Transactional
  public ShopEntity modShop() throws Exception {

    long id = shopRepository.count();
    ShopEntity shop = shopRepository.findById(id).orElseThrow(()-> new Exception("데이터 없음"));

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
    String now = LocalDateTime.now().format(formatter);
    shop.setName("가계_"+now);
    shop.setAddress("서울_"+now);

    return shopRepository.save(shop);
  }
}
