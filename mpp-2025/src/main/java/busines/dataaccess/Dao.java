package busines.dataaccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Dao {
    DataAccess getDataAccessSystem();

    /** SQL must use ? placeholders if it has inputs */
    String getSql();

    /** Bind parameters in positional order; no-op by default for queries without params */
    default void bind(PreparedStatement ps) throws SQLException {}

    /** Map rows from a SELECT; no-op for DML Daos */
    default void unpackResultSet(ResultSet rs) throws SQLException {}

    /** For INSERTs that need generated keys (e.g., auto-increment id) */
    default boolean wantsGeneratedKeys() { return false; }

    /** Handle generated keys if wantsGeneratedKeys() == true */
    default void handleGeneratedKeys(ResultSet keys) throws SQLException {}

    /** Read models or any results accumulated by unpackResultSet(...) */
    List<?> getResults();
}