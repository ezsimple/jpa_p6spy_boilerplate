package newapp.domain.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newapp.domain.entity.ProjectEntity;
import newapp.domain.repository.ProjectRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ProjectDao {

    private final ProjectRepository projectRepository;

    public Optional<ProjectEntity> findById(Long projNo) {
        return projectRepository.findById(projNo);
    }

    public ProjectEntity save(ProjectEntity projectEntity) {
        return projectRepository.saveAndFlush(projectEntity);
    }
}
