package roomescape.waiting;

public record WaitingResponse(
        Long waitingId,
        String theme,
        String date,
        String time) {
}
