package roomescape.reservation;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    Optional<Reservation> findById(Long id);

    List<Reservation> findByMemberId(Long memberId);
}
