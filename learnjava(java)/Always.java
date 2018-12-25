package door;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Always extends Thread {

	Log4j log = new Log4j(new Object() {
	}.getClass().getName());



	public void run() { // 30秒チェックメソッド


		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd");
		int id = 0;
		int tmp = 0;

		try {
			while (true) {
				Date nowTime = new Date();
				if (sdf2.format(nowTime).equals("01")) {
					if (tmp == 0) {
						Archive av = new Archive();
						av.csv();
						av.deleteTmp();
						av.copy();
						av.deleteDoor();
						av.reset();
						av.deleteCsv();
						tmp = 1;
					}
				}
				String sql = "SELECT * FROM door where ENTRYDATE=(select max(ENTRYDATE)from door) ORDER BY ENTRYTIME DESC LIMIT 1;";
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();

				if (rs.next()) {

					int flag = rs.getInt("flag");
					String time = rs.getString("entrytime");

					if (flag == 1) {
						Date limitTime = sdf.parse(time);

						String now;
						now = sdf.format(nowTime);
						nowTime = sdf.parse(now);

						int x = (int) nowTime.getTime();
						int y = (int) limitTime.getTime();

						if (x - y >= 30000) {
							if (id != rs.getInt("id")) {
								// Mail.send();
								System.out.println("メールを送信しました");
								log.write("アラートメールを送信しました。", 4);
								id = rs.getInt("id");
							}
						} else if (x - y <= 29999) {
							System.out.println("まだ３０秒経ってないよ");
						} else {
							log.write("アラートメール送信処理でエラー", 2);
							break;
						}
					}
				}

				Thread.sleep(1000);

			}
		} catch (SQLException | ParseException | InterruptedException e) {
			log.write(e, 2);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				log.write(e, 2);
			}
		}
	}


}