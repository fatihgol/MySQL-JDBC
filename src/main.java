import java.sql.SQLException;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connector connector = new Connector("localhost", "test", "root", "123");
		connector.update_nonblocking("alter table `test` add index(name);");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
