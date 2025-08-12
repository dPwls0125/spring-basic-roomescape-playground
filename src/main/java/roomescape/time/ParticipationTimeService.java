package roomescape.time;

import org.springframework.stereotype.Service;
import roomescape.reservation.Reservation;
import roomescape.reservation.ReservationDao;

import java.util.List;

@Service
public class ParticipationTimeService {
    private ParticipationTimeDao participationTimeDao;
    private ReservationDao reservationDao;

    public ParticipationTimeService(ParticipationTimeDao participationTimeDao, ReservationDao reservationDao) {
        this.participationTimeDao = participationTimeDao;
        this.reservationDao = reservationDao;
    }

    public List<AvailableTime> getAvailableTime(String date, Long themeId) {
        List<Reservation> reservations = reservationDao.findByDateAndThemeId(date, themeId);
        List<ParticipationTime> participationTimes = participationTimeDao.findAll();

        return participationTimes.stream()
                .map(time -> new AvailableTime(
                        time.getId(),
                        time.getTime(),
                        reservations.stream()
                                .anyMatch(reservation -> reservation.getTime().getId().equals(time.getId()))
                ))
                .toList();
    }

    public List<ParticipationTime> findAll() {
        return participationTimeDao.findAll();
    }

    public ParticipationTime save(ParticipationTime participationTime) {
        return participationTimeDao.save(participationTime);
    }

    public void deleteById(Long id) {
        participationTimeDao.deleteById(id);
    }
}
