package ldh.common.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by Puhui on 2016/12/8.
 */
public class H2FunctionRegister {

    private static final Logger LOGGER = LoggerFactory.getLogger(H2FunctionRegister.class);

    private JdbcTemplate jdbcTemplate;

    public H2FunctionRegister(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        register();
    }

    private void register() {
        LOGGER.info("h2 register function");
        jdbcTemplate.execute("CREATE ALIAS IF NOT EXISTS Date FOR \"ldh.common.function.H2Function.toDate\";");
        jdbcTemplate.execute("CREATE ALIAS IF NOT EXISTS date_format FOR \"ldh.common.function.H2Function.date_format\";");
    }


}
