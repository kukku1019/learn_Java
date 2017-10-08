package door;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Sql {

	private int flag;
	private String stFlag;
	ArrayList<DataBox> list = new ArrayList<DataBox>();
	ArrayList<DataBox> list2 = new ArrayList<DataBox>();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	DBConnector db = new DBConnector();
	Connection con = db.getConnection();

	Log4j log = new Log4j(new Object(){}.getClass().getName());

	public ArrayList<DataBox> add(String day, String param) {


		DataBox box = null;
		String tmp = null;
		int requestMonth = 0;
		if (!day.equals("")) {
			tmp = day.substring(5, 7);
			requestMonth = Integer.parseInt(tmp);
		}
		Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		int nowMonth = calendar.get(Calendar.MONTH) + 1;
		String sql = null;

		if (requestMonth == nowMonth && param.equals("2")) {
			sql = "select * from door where ENTRYDATE=? order by id desc";
			log.write("SQL検索実行 --- 画面名:ログ、検索テーブル:door", 5);
		} else if ((requestMonth != nowMonth && param.equals("2"))) {
			sql = "select * from tmp where ENTRYDATE=? order by id desc";
			log.write("SQL検索実行 --- 画面名:ログ、検索テーブル:tmp", 5);
		} else if (param.equals("3")) {

			String sql2 = null;
			String table = null;
			if (requestMonth == nowMonth) {
				table = "door";
				log.write("SQL検索実行 --- 画面名:グラフ、検索テーブル:door", 5);
			} else if (requestMonth != nowMonth) {
				table = "tmp";
				log.write("SQL検索実行 --- 画面名:グラフ、検索テーブル:tmp", 5);
			}

			sql = "select * from " + table + " where ENTRYDATE=? order by id;";
			sql2 = "select * from " + table + " where ENTRYDATE=? order by id desc limit 1;";


			if (!day.equals("")) {
				if (!day.equals(sdf.format(today))) {
					try {
						PreparedStatement ps = con.prepareStatement(sql2);
						ps.setString(1, day);
						ResultSet rs = ps.executeQuery();
						if(rs.next() == true){
							if (rs.getInt("flag") == 1) {
								lastGraph(day, param);
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}

		} else {
			sql = "select * from door where ENTRYDATE=? order by id desc";
			log.write("SQL検索実行 --- 画面名:トップ、検索テーブル:door", 6);
		}

		try {

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, day);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				box = new DataBox();
				box.setId(rs.getInt("id"));
				flag = rs.getInt("flag");
				flagWord();
				box.setStFlag(stFlag);
				box.setEntryDate(rs.getString("entrydate"));
				box.setEntryTime(rs.getString("entrytime"));
				list.add(box);
			}

			if (list2 != null) {
				list.addAll(list2);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}

	public ArrayList<DataBox> lastGraph(String day, String param) {

		Date d;
		DataBox box2 = new DataBox();
		try {
			d = sdf.parse(day);

			Calendar now = Calendar.getInstance();
			now.getTime();

			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			cal.add(Calendar.DATE, 1);
			String day2 = sdf.format(cal.getTime());
			String sql = null;
			if (cal.get(Calendar.MONTH) == now.get(Calendar.MONTH)) {
				sql = "select * from door where entrydate = \"" + day2 + "\" order by id limit 1;";
			} else if (cal.get(Calendar.MONTH) != now.get(Calendar.MONTH)) {
				sql = "select * from tmp where entrydate = \"" + day2 + "\" order by id limit 1;";
			}
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			box2.setId(rs.getInt("id"));
			flag = rs.getInt("flag");
			flagWord();
			box2.setStFlag(stFlag);
			box2.setEntryDate(rs.getString("entrydate"));
			box2.setEntryTime(rs.getString("entrytime"));
			list2.add(box2);
		} catch (ParseException | SQLException e) {
			e.printStackTrace();
		}

		return list2;
	}

	public String flagWord() {

		stFlag = String.valueOf(flag);
		if (stFlag.equals("1")) {
			stFlag = "open";
		} else if (stFlag.equals("0")) {
			stFlag = "close";
		}
		return stFlag;
	}

}
