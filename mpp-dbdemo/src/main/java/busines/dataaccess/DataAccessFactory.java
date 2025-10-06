package busines.dataaccess;

public class DataAccessFactory {
    public static DataAccess getDataAccess() {
        return new DataAccessSystem();
    }
}
