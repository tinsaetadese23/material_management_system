package repository;

public class SearchingRepository {
	private boolean connCreated;
	DbConnection dbConn;
	public SearchingRepository() {
		try {
			dbConn = new DbConnection();
			connCreated = dbConn.isConnected();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
