/**
 * 
 */
package org.haijun.study.item.write;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

/**
 * 为带？号sql提供参数赋值的接口实现
 * 2013-9-23下午10:33:20
 */
public class DestCreditBillItemPreparedStatementSetter implements
		ItemPreparedStatementSetter<DestinationCreditBill> {
	
	public void setValues(DestinationCreditBill item, PreparedStatement ps)
			throws SQLException {
		ps.setString(1, item.getId());
		ps.setString(2, item.getAccountID());
		ps.setString(3, item.getName());
		ps.setDouble(4, item.getAmount());
		ps.setString(5, item.getDate());
		ps.setString(6, item.getAddress());
	}
}
