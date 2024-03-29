package com.kanevsky.services;

import com.kanevsky.entities.PlayerEntity;
import com.kanevsky.exceptions.IngestException;
import com.kanevsky.utils.PlayerColumnUtils;
import com.kanevsky.views.PlayerView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExtractorService implements IExtractorService {

    @Value("${batch.insert.size}")
    private int batchSize;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    @Transactional
    public long ingestInputStream(InputStream inputStream) throws IngestException {
//        var columnNames = List.of("playerID", "birthYear", "birthMonth", "birthDay", "birthCountry", "birthState", "birthCity", "deathYear", "deathMonth", "deathDay", "deathCountry", "deathState", "deathCity", "nameFirst", "nameLast", "nameGiven", "weight", "height", "bats", "throws", "debut", "finalGame", "retroID", "bbrefID");
        Map<String, String> csvColumnNameToDbColumnName = PlayerColumnUtils.getCsvColumnNameToDbColumnName();

        String insertSql = "INSERT INTO player (" + csvColumnNameToDbColumnName.values().stream().collect(Collectors.joining(",")) + ") " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        long count = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             PreparedStatement preparedStatement = jdbcTemplate.getDataSource().getConnection().prepareStatement(insertSql)) {

            List<String> columnNamesAccordingToTheirOrderInInsertQuery = new ArrayList<>(csvColumnNameToDbColumnName.keySet());
            Map<Integer, Integer> csvColumnIndexMappingToQueryIndex = buildCsvColumnIndexMappingToQueryIndex(reader.readLine(), columnNamesAccordingToTheirOrderInInsertQuery);

            final Map<String, Integer> dbColumnNameToSQLTypes = PlayerColumnUtils.getColumnSQLTypes();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineData = line.split(","); // Assuming CSV fields are comma-separated
                var dbColumnNamesAccordingToTheirOrderInInsertQuery = new ArrayList<>(csvColumnNameToDbColumnName.values());
                addLineToPreparedStatement(lineData, csvColumnIndexMappingToQueryIndex, preparedStatement, dbColumnNamesAccordingToTheirOrderInInsertQuery, dbColumnNameToSQLTypes);
                count++;
                executeIntermediateBatchIfReachedCapacity(preparedStatement, count);
            }

            executeRemainderBatch(preparedStatement);

        } catch (IOException |
                 SQLException e) {
            log.error("Batch insert failed", e);
            throw new IngestException("Batch insert failed", e);
        }
        return count;
    }

    private void addLineToPreparedStatement(String[] lineData, Map<Integer, Integer> mapping, PreparedStatement preparedStatement, List<String> columnNames, Map<String, Integer> columnNameToSQLTypes) throws SQLException {
        Map<Integer, Function> sqlTypeToMapper = Map.of(
                Types.VARCHAR, Function.identity(),
                Types.INTEGER, (s) -> Integer.parseInt(s.toString()),
                Types.DATE, (s) -> LocalDate.parse(s.toString()),
                Types.OTHER, Function.identity());

        for (int i = 0; i < lineData.length; i++) {
            String cellValue = lineData[mapping.get(i)];
            String columnName = columnNames.get(i);
            Integer sqlType = columnNameToSQLTypes.get(columnName);

            if (cellValue == null || cellValue.isBlank()) {
                preparedStatement.setNull(i + 1, sqlType);
            } else {
                preparedStatement.setObject(i + 1, sqlTypeToMapper.get(sqlType).apply(lineData[i]), sqlType);
            }
        }

        preparedStatement.addBatch();
    }

    private Map<Integer, Integer> buildCsvColumnIndexMappingToQueryIndex(String headline, List<String> columnNames) {
        var csvHeadlineColumns = Arrays.asList(headline.split(",")); // Assuming CSV fields are comma-separated
        Map<Integer, Integer> result = new HashMap<>();
        for (int queryColumnIndex = 0; queryColumnIndex < columnNames.size(); queryColumnIndex++) {
            String queryColumnName = columnNames.get(queryColumnIndex);
            int headlineColumnIndex = csvHeadlineColumns.indexOf(queryColumnName);
            result.put(headlineColumnIndex, queryColumnIndex);
        }
        return result;
    }

    private void executeIntermediateBatchIfReachedCapacity(PreparedStatement preparedStatement, long count) throws SQLException {
        if (count % batchSize == 0) {
            preparedStatement.executeBatch();
//            jdbcTemplate.getDataSource().getConnection().commit();
        }
    }

    private void executeRemainderBatch(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.executeBatch();
//        jdbcTemplate.getDataSource().getConnection().commit();
        jdbcTemplate.getDataSource().getConnection().setAutoCommit(true);
    }
}
