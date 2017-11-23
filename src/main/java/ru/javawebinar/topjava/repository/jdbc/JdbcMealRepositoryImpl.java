package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {
    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertMeal;

    @Autowired
    public JdbcMealRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertMeal = new SimpleJdbcInsert(dataSource).withTableName("meals").usingGeneratedKeyColumns("id");
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("dateTime", meal.getDateTime());
        map.addValue("description", meal.getDescription());
        map.addValue("calories", meal.getCalories());
        map.addValue("userId", userId);
        map.addValue("id", meal.getId());
        if (meal.isNew()){
            Number newKey = insertMeal.executeAndReturnKey(map);
            meal.setId(newKey.intValue());
        }else{
            namedParameterJdbcTemplate.update("update meals SET userid=:userId, dateTime=:dateTime," +
                    " description=:description, calories=:calories where id=:id", map);
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("delete from meals where id = ? and userid = ?", id , userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> list = jdbcTemplate.query("select * from meals where id = ? and userid = ?", ROW_MAPPER, id, userId);
        return DataAccessUtils.requiredSingleResult(list);
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> list = jdbcTemplate.query("select * from meals where userid = ? ORDER BY datetime", ROW_MAPPER, userId);
        return list;
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        List<Meal> list = jdbcTemplate.query("select * from meals where userid = ? and dateTime > ? and dateTime < ?", ROW_MAPPER, userId, startDate, endDate);
        return list;
    }
}
