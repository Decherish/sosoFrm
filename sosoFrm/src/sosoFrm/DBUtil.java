package sosoFrm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	
	/**
	 * 方法一：增删改 操作的方法
	 * @param sql
	 */
	public static int noQuery(String sql,Object[] params){		
		int n = 0;
		try {			
			
			Connection conn = getConnection();
			
			// 3. 发送一条SQL语句			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// ############# 注意设置参数 #########
						if(params!=null)
							for(int i=0; i<params.length; i++){
								pstmt.setObject(i+1, params[i]);
							}
						
			n = pstmt.executeUpdate(); // 增删改都使用executeUpdate方法，n返回的是该语句影响了几行
			
			// 5. 关闭连接
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		return n;
		
	}

	/**
	 * 方法二：查询 是否存在
	 * @param sql
	 * @return
	 */
	public static boolean queryExist(String sql,Object[] params){
		boolean find = false;
		
		try {
			
			Connection conn = getConnection();			
			
			// 3. 发送一条SQL语句			
			PreparedStatement pstmt = conn.prepareStatement(sql);

			// ############# 注意设置参数 #########
			if(params!=null)
				for(int i=0; i<params.length; i++){
					pstmt.setObject(i+1, params[i]);
				}
			
			ResultSet rs = pstmt.executeQuery();
			
			// 4. 根据结果进行处理
			if(rs!=null && rs.next()){
				 find = true;
			}			
			
			// 5. 关闭连接
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		

		return find;
	}
	
	/**
	 * 方法三：查询 得到结果集【？关闭连接】
	 * @param sql
	 * @return
	 */
	public static ResultSet queryResultSet(String sql){
		
		ResultSet rs = null;
		
		try {
			
			Connection conn = getConnection();
			
			// 3. 发送一条SQL语句		
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return rs;
		
	}

	/**
	 * 方法四：查询 得到单行单列的值
	 * @param sql
	 * @return
	 */
	public static Object queryObject(String sql,Object[] params){
		
		Object obj = null;
		
		try {
			
			Connection conn = getConnection();
			
			// 3. 发送一条SQL语句			
						PreparedStatement pstmt = conn.prepareStatement(sql);

						// ############# 注意设置参数 #########
						if(params!=null)
							for(int i=0; i<params.length; i++){
								pstmt.setObject(i+1, params[i]);
							}
						
						ResultSet rs = pstmt.executeQuery();
			
			// 4. 根据结果进行处理
			if(rs!=null && rs.next()){
				obj = rs.getObject(1);
			}			
			
			// 5. 关闭连接
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		

		return obj;
	}


	/**
	 * 方法五：获得连接
	 * @return
	 */
	public static Connection getConnection(){
		
		Connection conn = null;
		
		try {
			// 1. 加载数据驱动的jar包
				Class.forName("com.mysql.jdbc.Driver");
				
			// 2. 与数据库创建连接			
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobile_package?characterEncoding=UTF-8", "root", "123456");
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

		return conn;
	}

	/**
	 * 方法六：关闭结果集
	 * @param rs
	 */
	public static void close(ResultSet rs){
		try {
			if(rs!=null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}

