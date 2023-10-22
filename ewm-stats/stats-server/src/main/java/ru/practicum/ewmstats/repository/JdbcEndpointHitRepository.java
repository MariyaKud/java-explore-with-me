package ru.practicum.ewmstats.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import ru.practicum.dto.ViewStatsDto;
import ru.practicum.ewmstats.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcEndpointHitRepository implements EndpointHitRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public EndpointHit save(EndpointHit hit) {

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("app", hit.getApp());
        map.addValue("uri", hit.getUri());
        map.addValue("ip", hit.getIp());
        map.addValue("timestamp", hit.getTimestamp());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        final String sqlQuery = "insert into hits (app, uri, ip, timestamp) " +
                                "values (:app, :uri, :ip, :timestamp)";

        jdbcOperations.update(sqlQuery, map, keyHolder);
        hit.setId((Long) keyHolder.getKeyList().get(0).get("id"));

        return hit;
    }

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime startData, LocalDateTime endData, List<String> uris, Boolean unique) {
        String sqlQuery;
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("startData", startData);
        map.addValue("endData", endData);


        if (uris.isEmpty() && unique) {
            sqlQuery = "select app, uri, count(DISTINCT ip) as hits " +
                    "from hits as h " +
                    "where h.timestamp >= :startData and h.timestamp <= :endData " +
                    "GROUP BY app, uri " +
                    "ORDER BY count(*) desc";

        } else if (uris.isEmpty() && !unique) {
            sqlQuery = "select app, uri, count(*) as hits " +
                    "from hits as h " +
                    "where h.timestamp >= :startData and h.timestamp <= :endData " +
                    "GROUP BY app, uri " +
                    "ORDER BY count(*) desc";

        } else if (!uris.isEmpty() && unique) {
            sqlQuery = "select app, uri, count(DISTINCT ip) as hits " +
                    "from hits as h " +
                    "where h.timestamp >= :startData and h.timestamp <= :endData and uri in (:uris) " +
                    "GROUP BY app, uri " +
                    "ORDER BY count(*) desc";

            map.addValue("uris", uris);

        } else {
            sqlQuery = "select app, uri, count(*) as hits " +
                       "from hits as h " +
                       "where h.timestamp >= :startData and h.timestamp <= :endData and uri in (:uris) " +
                       "GROUP BY app, uri " +
                       "ORDER BY count(*) desc";

            map.addValue("uris", uris);
        }

        List<ViewStatsDto> viewStatsDtos = new ArrayList<>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(sqlQuery, map);
        while (rowSet.next()) {
            viewStatsDtos.add(mapRowToViewStats(rowSet));
        }

        return viewStatsDtos;
    }

    private ViewStatsDto mapRowToViewStats(SqlRowSet rs) {
        return new ViewStatsDto(rs.getString("app"),
                rs.getString("uri"),
                rs.getInt("hits"));
    }
}
