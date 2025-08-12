package roomescape.time;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class ParticipationTimeController {
    private ParticipationTimeService participationTimeService;

    public ParticipationTimeController(ParticipationTimeService participationTimeService) {
        this.participationTimeService = participationTimeService;
    }

    @GetMapping("/times")
    public List<ParticipationTime> list() {
        return participationTimeService.findAll();
    }

    @PostMapping("/times")
    public ResponseEntity<ParticipationTime> create(@RequestBody ParticipationTime participationTime) {
        if (participationTime.getTime() == null || participationTime.getTime().isEmpty()) {
            throw new RuntimeException();
        }

        ParticipationTime newParticipationTime = participationTimeService.save(participationTime);
        return ResponseEntity.created(URI.create("/times/" + newParticipationTime.getId())).body(newParticipationTime);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        participationTimeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available-times")
    public ResponseEntity<List<AvailableTime>> availableTimes(@RequestParam String date, @RequestParam Long themeId) {
        return ResponseEntity.ok(participationTimeService.getAvailableTime(date, themeId));
    }
}
