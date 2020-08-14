package net.ciebus.chestshopplus.Database;

import com.j256.ormlite.db.BaseSqliteDatabaseType;

public class SqliteDatabaseType extends BaseSqliteDatabaseType {
    private static final String DATABASE_URL_PORTION = "sqlite";
    private static final String DRIVER_CLASS_NAME = "org.sqlite.JDBC";
    private static final String DATABASE_NAME = "SQLite";

    public SqliteDatabaseType() {
    }

    public boolean isDatabaseUrlThisType(String url, String dbTypePart) {
        return "sqlite".equals(dbTypePart);
    }

    protected String getDriverClassName() {
        return "org.sqlite.JDBC";
    }

    public String getDatabaseName() {
        return "SQLite";
    }

    public void appendLimitValue(StringBuilder sb, long limit, Long offset) {
        sb.append("LIMIT ");
        if (offset != null) {
            sb.append(offset).append(',');
        }

        sb.append(limit).append(' ');
    }

    public boolean isOffsetLimitArgument() {
        return true;
    }

    public boolean isNestedSavePointsSupported() {
        return false;
    }

    public void appendOffsetValue(StringBuilder sb, long offset) {
        throw new IllegalStateException("Offset is part of the LIMIT in database type " + this.getClass());
    }
}