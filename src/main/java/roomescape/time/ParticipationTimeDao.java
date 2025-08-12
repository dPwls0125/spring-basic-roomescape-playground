package roomescape.time;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ParticipationTimeDao {
    private final JdbcTemplate jdbcTemplate;

    public ParticipationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ParticipationTime> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM time WHERE deleted = false",
                (rs, rowNum) -> new ParticipationTime(
                        rs.getLong("id"),
                        rs.getString("time_value")));
    }

    public ParticipationTime save(ParticipationTime participationTime) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO time(time_value) VALUES (?)", new String[]{"id"});
            ps.setString(1, participationTime.getTime());
            return ps;
        }, keyHolder);

        return new ParticipationTime(keyHolder.getKey().longValue(), participationTime.getTime());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("UPDATE time SET deleted = true WHERE id = ?", id);
    }
}
