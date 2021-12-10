package newapp.domain.func1.controller;

import lombok.RequiredArgsConstructor;
import newapp.domain.entity.ShopEntity;
import newapp.domain.func1.service.HelloService;
import newapp.global.util.ParamUtil;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class HelloController {

  private final HelloService helloService;

  @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ShopEntity> hello(HttpServletRequest req) {
    String name = req.getParameter("name");

    Map<String, Object> param = new HashMap<>();
    param.put("name", name);

    return helloService.selectByName(param);
  }

  @GetMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
  public String add(HttpServletRequest req) {

    helloService.addShop();

    JSONObject res = new JSONObject();
    res.put("result", "OK");
    return res.toString();
  }


}
