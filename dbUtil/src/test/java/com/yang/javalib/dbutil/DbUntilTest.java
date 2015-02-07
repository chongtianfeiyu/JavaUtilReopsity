package com.yang.javalib.dbutil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class DbUntilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

    /**
     * 暂时先放到这里了。批量提交的测试
     * 这个批量还是同一个statement,而如果语句不一样呢 ?
     * 一样的话，其实可以sql语句都可以实现的，但若是不一样呢？
     * http://gzcj.iteye.com/blog/224034
     *
     * @throws Exception
     */
	@Test
	public void test() throws Exception {
        Connection conn = getOracleConnection();
        ResultSet rs = null;
//      Statement stmt = null;
        PreparedStatement stmt=null;
        try {
//           Create a prepared statement
            String sql = "INSERT INTO batchtab employees values (?, ?)";
            stmt = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            stmt.clearBatch();
//           Insert 3 rows of data
            int _first =1;
            int _second =2;
            for (int i=0; i<3; i++) {
                stmt.setString( _first ,""+i);//第一个值是一哦.,y这个可以查看ＡＰＩ
                stmt.setString( _second ,"batch_value"+i);
                stmt.addBatch();
            }
            int[] updateCounts = stmt.executeBatch();
            System.out.println(updateCounts);
            conn.commit();
            sql="SELECT * FROM batchtab";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("batch_id");
                String name = rs.getString("batch_value");
                System.out.println("id=" + id + "  name=" + name);
            }
        } catch (BatchUpdateException b) {
            System.out.println("SQLException: " + b.getMessage());
            System.out.println("SQLState: " + b.getSQLState());
            System.out.println("Message: " + b.getMessage());
            System.out.println("Vendor error code: " + b.getErrorCode());
            System.out.print("Update counts: ");
            int[] updateCounts = b.getUpdateCounts();
            for (int i = 0; i < updateCounts.length; i++) {
                System.out.print(updateCounts[i] + " ");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("Message: " + ex.getMessage());
            System.out.println("Vendor error code: " + ex.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exception: " + e.getMessage());
        } finally {
            if( conn != null )
                conn.close();
            if(stmt !=null)
                stmt.close();
            if(rs !=null)
                rs.close();
        }

	}


    /**
     * 上一个测试支持， 要改的。
     * @return
     * @throws Exception
     */
    private static Connection getOracleConnection() throws Exception {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:test";
        String username = "test";
        String password = "test";

        Class.forName(driver); // load Oracle driver
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }


}
