package newapp.domain.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newapp.domain.repository.CustomerReqRepository;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CustomerReqDao {
    private final CustomerReqRepository customerReqRepository;
}
