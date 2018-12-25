package door;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Archive {

	DBConnector db = new DBConnector();
	Connection con = db.getConnection();
	Calendar today = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

	Log4j log = new Log4j(new Object(){}.getClass().getName());


	public void csv() {

			today.getTime();
			//today.add(Calendar.MONTH, 1);
			String date = sdf.format(today.getTime());


			try {
				// 出力先を作成する
				FileWriter fw = new FileWriter("C:\\ProgramData\\MySQL\\MySQL Server 5.7\\Uploads\\" + date + ".csv", false);
				PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

				Statement st = con.createStatement();

				String sql = "select * from tmp";

				ResultSet rs = st.executeQuery(sql);

				while(rs.next()){
					String str = String.valueOf(rs.getInt("id")) + "," + String.valueOf(rs.getInt("flag") + "," + rs.getString("entrydate") + "," + rs.getString("entrytime"));
					pw.println(str);
					}

				// ファイルに書き出す
				pw.close();
				log.write("CSVファイルを出力しました。", 4);

			} catch (IOException | SQLException e) {
				// 例外時処理
				log.write(e, 2);
			}

	}

	public void deleteCsv() {

		today.getTime();
		today.add(Calendar.MONTH, -6);
		String date = sdf.format(today.getTime());

		File csv = new File("C:\\ProgramData\\MySQL\\MySQL Server 5.7\\Uploads\\" + date + ".csv");
		if (csv.exists()){
			log.write("CSVファイルを削除しました。", 4);
			csv.delete();
		}else{
			log.write("該当ファイルは存在しません。", 4);
		}

	}

	public void deleteTmp() {

		try {
			Statement st = con.createStatement();
			String sql = "delete from tmp;";
			st.executeUpdate(sql);
			log.write("tmpテーブルからデータを削除しました。",4);
		} catch (SQLException e) {
			log.write(e, 2);
		}

	}

	public void deleteDoor() {

		try {
			Statement st = con.createStatement();
			String sql = "delete from door;";
			st.executeUpdate(sql);
			log.write("doorテーブルからデータを削除しました。", 4);
		} catch (SQLException e) {
			log.write(e, 2);
		}

	}


	public void copy() {

		try {
			Statement st = con.createStatement();
			String sql = "insert into tmp select * from door;";
			st.executeUpdate(sql);
			log.write("tmpテーブルにdoorテーブルのデータを移行しました。", 4);
		} catch (SQLException e) {
			log.write(e, 2);
		}

	}


	public void reset() {

		try {
			Statement st = con.createStatement();
			String sql = "alter table door auto_increment = 1";
			st.executeUpdate(sql);
			log.write("doorテーブルのオートインクリメントをリセットしました。", 4);
		} catch (SQLException e) {
			log.write(e, 2);
		}
	}

}
