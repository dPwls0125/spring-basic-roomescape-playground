package roomescape.reservation;

import org.springframework.stereotype.Service;
import roomescape.auth.LoginMember;

import java.util.List;

@Service
public class ReservationService {
    private ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public ReservationResponse save(ReservationRequest reservationRequest, LoginMember loginMember) {
        reservationRequest = replaceNameIfEmpty(reservationRequest, loginMember);
        Reservation reservation = reservationDao.save(reservationRequest);
        return new ReservationResponse(reservation.getId(), reservationRequest.getName(), reservation.getTheme().getName(), reservation.getDate(), reservation.getTime().getTime());
    }

    private ReservationRequest replaceNameIfEmpty(ReservationRequest request, LoginMember loginMember) {
        String requestName = request.getName();
        if (requestName == null || requestName.isBlank()) {
            return new ReservationRequest(loginMember.getName(), request.getDate(), request.getTheme(), request.getTime());
        }
        return request;
    }

    public void deleteById(Long id) {
        reservationDao.deleteById(id);
    }

    public List<ReservationResponse> findAll() {
        return reservationDao.findAll().stream()
                .map(it -> new ReservationResponse(it.getId(), it.getName(), it.getTheme().getName(), it.getDate(), it.getTime().getTime()))
                .toList();
    }
}
