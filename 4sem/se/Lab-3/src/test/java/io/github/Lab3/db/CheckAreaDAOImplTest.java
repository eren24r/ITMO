package io.github.Lab3.db;

import io.github.Lab3.model.AreaResultChecker;
import io.github.Lab3.model.CheckAreaBean;
import io.github.Lab3.model.CheckAreaResultsBean;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckAreaDAOImplTest {

    @Test
    void addNewResult() throws SQLException {
        CheckAreaDAOImpl dao = new CheckAreaDAOImpl();

        CheckAreaResultsBean.newResult(1, 1, 1);

        Collection<CheckAreaBean> resList = dao.getAllResults();

        for(CheckAreaBean cb : resList) {
            assertEquals(1, cb.getX());
            assertEquals(1, cb.getY());
            assertEquals(1, cb.getR());
            assertEquals(false, cb.isResult());
        }
    }
}