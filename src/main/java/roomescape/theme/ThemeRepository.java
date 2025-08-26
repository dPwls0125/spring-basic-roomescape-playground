package roomescape.theme;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository extends CrudRepository<Theme, Long> {
    List<Theme> findAll();

    Optional<Theme> findById(long id);

    void deleteById(long id);
}
