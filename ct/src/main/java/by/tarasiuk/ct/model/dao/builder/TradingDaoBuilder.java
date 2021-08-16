package by.tarasiuk.ct.model.dao.builder;

import by.tarasiuk.ct.model.dao.ColumnLabel;
import by.tarasiuk.ct.model.entity.impl.Trading;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.tarasiuk.ct.model.dao.ColumnLabel.EMPLOYEE_ID;
import static by.tarasiuk.ct.model.dao.ColumnLabel.OFFER_ID;
import static by.tarasiuk.ct.model.dao.ColumnLabel.TRADING_FREIGHT;
import static by.tarasiuk.ct.model.dao.ColumnLabel.TRADING_ID;

/**
 * Build trading object from <code>ResultSet</code>.
 * @see java.sql.ResultSet
 */

public class TradingDaoBuilder {
    private TradingDaoBuilder() {
    }

    public static Trading build(ResultSet resultSet) throws SQLException {
        long tradingId = resultSet.getLong(TRADING_ID);
        long offerId = resultSet.getLong(OFFER_ID);
        long employeeId = resultSet.getLong(EMPLOYEE_ID);
        float tradingFreight = resultSet.getFloat(TRADING_FREIGHT);
        Trading.Status status = Trading.Status.valueOf(resultSet.getString(ColumnLabel.TRADING_STATUS));

        Trading trading = new Trading();
        trading.setId(tradingId);
        trading.setOfferId(offerId);
        trading.setEmployeeId(employeeId);
        trading.setFreight(tradingFreight);
        trading.setStatus(status);

        return trading;
    }
}
