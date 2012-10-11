import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Connector {
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	public Connector(String host, String database, String user, String password) {
		// init for the JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// init connection
			connection = (Connection) DriverManager
					.getConnection("jdbc:mysql://" + host + "/" + database
							+ "?" + "user=" + user + "&password=" + password);
			connection.setAutoCommit(false); // set auto-commit false
			// init statement
			statement = (Statement) connection.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ResultSet execute(String query) throws SQLException {
		resultSet = statement.executeQuery(query);
		return resultSet;
	}

	public int update(String query) throws SQLException {
		return statement.executeUpdate(query);
	}

	public void update_nonblocking(String query) {
		Handler handler = new Handler(query);
		handler.setDaemon(true);
		handler.start();
		return;
	}

	// handler for the async transaction
	private class Handler extends Thread {
		String query = new String();

		public Handler(String query) {
			this.query = query;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
				statement.executeUpdate(query);
				statement.executeUpdate("commit;");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
