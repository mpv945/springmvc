package org.haijun.study.tools;

import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.MySQLDialect;

/**
 * 数据库连接的方言
 * 方言列表：
 * DB2 org.hibernate.dialect.DB2Dialect
 * PostgreSQL org.hibernate.dialect.PostgreSQLDialect
 * MySQL org.hibernate.dialect.MySQLDialect
 * MySQL with InnoDB 	org.hibernate.dialect.MySQLInnoDBDialect
 * MySQL with MyISAM    org.hibernate.dialect.MySQLMyISAMDialect
 * Oracle (any version) org.hibernate.dialect.OracleDialect
 * Oracle 9i/10g        org.hibernate.dialect.Oracle9Dialect
 * Sybase				org.hibernate.dialect.SybaseDialect
 * Sybase Anywhere		org.hibernate.dialect.SybaseAnywhereDialect
 * Microsoft SQL Server	org.hibernate.dialect.SQLServerDialect
 * @author zhenx
 *
 */
public class CustomMySQLDialect extends MySQL5Dialect {
	/**
	 * 如果在删除前必须删除约束，则返回true,不删除返回false
	 */
    @Override
    public boolean dropConstraints() {
        return false;
    }
}
