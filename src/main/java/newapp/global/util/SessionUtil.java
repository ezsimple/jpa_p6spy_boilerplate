package newapp.global.util;

import lombok.RequiredArgsConstructor;
import newapp.domain.dao.ProjectDao;
import newapp.domain.dao.UserDao;
import newapp.domain.entity.ProjectEntity;
import newapp.domain.entity.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SessionUtil {

    private final UserDao userDao;
    private final ProjectDao projectDao;

    /**
     * SessionCreationPolicy.ALWAYS 상황에서만 유효합니다.
     * @return
     */
    public String getUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails)principal).getUsername();
        }
        return principal.toString();
    }

    public String getUserNm() {
        String userId = getUserId();
        Optional<UserEntity> entity = userDao.findByUserId(userId);
        if (!entity.isPresent()) return "";
        return entity.get().getUsername();
    }

    public String getUserProjectNm() {
        String userId = getUserId();
        Optional<UserEntity> entity = userDao.findByUserId(userId);
        if (entity.isPresent()) {
            Optional<ProjectEntity> proj = projectDao.findById(entity.get().getProjectEntity().getProjNo());
            String projNm = proj.isPresent() ? proj.get().getProjNm() : "";
            return projNm;
        }
        return "";
    }

}
