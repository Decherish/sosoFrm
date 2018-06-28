package sosoFrm;

import java.awt.EventQueue;
import java.sql.ResultSet;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class FrmSelect extends JInternalFrame {
	static int allConsume1=0;
	static int allConsume2=0;
	public FrmSelect(String mobile_name) {

		//利用name获得user_id
		String sql = "select user_id from user where mobile_number=?";
		Object[] params = {mobile_name};
		Object user_id = DBUtil.queryObject(sql, params);

		setClosable(true);
		setBounds(100, 100, 686, 461);
		getContentPane().setLayout(null);
		Object[] date= {"编号","消费项目","消费单位","消费数量","消费产生的时间","是否超出套餐消费","消费金额"};
		TableModel model = new DefaultTableModel(date,20);
		JTable jt1 = new JTable(model);
		jt1.setRowHeight(20);
		jt1.getTableHeader().setReorderingAllowed(false);
		JScrollPane jsp1 = new JScrollPane(jt1);
		jsp1.setRowHeader(null);
		jsp1.setLocation(10, 10);
		jsp1.setSize(650, 274);
		getContentPane().add(jsp1);
		
		
		JLabel jl1 = new JLabel("当前套餐情况:");
		jl1.setBounds(10, 294, 109, 15);
		getContentPane().add(jl1);
		
		JLabel jl2 = new JLabel("套餐资费:");
		jl2.setBounds(10, 319, 109, 15);
		getContentPane().add(jl2);
		
		JLabel jl3 = new JLabel("00,00元");
		jl3.setBounds(65, 319, 54, 15);
		getContentPane().add(jl3);
		
		JLabel jl4 = new JLabel("免费通话时长：");
		jl4.setBounds(10, 344, 96, 15);
		getContentPane().add(jl4);
		
		JLabel jl5 = new JLabel("100分钟");
		jl5.setBounds(105, 344, 122, 15);
		getContentPane().add(jl5);
		
		JLabel jl6 = new JLabel("免费上网流量:");
		jl6.setBounds(10, 369, 96, 15);
		getContentPane().add(jl6);
		
		JLabel jl7 = new JLabel("1024M");
		jl7.setBounds(105, 369, 122, 15);
		getContentPane().add(jl7);
		
		
		try {
			//把数据添加到表中
			String sql1="select *from cost_record where user_id="+user_id;
			int row=0;
			ResultSet rs=DBUtil.queryResultSet(sql1);
			while(rs.next()) {
				for(int i=1;i<6;i++) {
					if(row+1==i) {
						jt1.setValueAt(i, row, 0);;
						if(rs.getInt(3) == 1) {
							jt1.setValueAt("通话", row, 1);
							jt1.setValueAt("秒", row, 2);
							allConsume1 += rs.getInt(4);
						}
						if(rs.getInt(3) == 2) {
							jt1.setValueAt("上网", row, 1);
							jt1.setValueAt("kB", row, 2);
							allConsume2 += rs.getShort(4);
						}
						jt1.setValueAt(rs.getInt(4), row, 3);;
						jt1.setValueAt(rs.getString(5), row, 4);;
						if(rs.getInt(6)==0) {
							jt1.setValueAt("false", row, 5);
						} 	
						if(rs.getInt(6)==1) {
							jt1.setValueAt("true", row, 5);
						}
						jt1.setValueAt(rs.getBigDecimal(7), row, 6);;
					}
				}
				row++;
			}
			//计算总消费
			String sql2="select package_id from package_record where user_id=?";
			Object[] params2= {user_id};
			Object package_id = DBUtil.queryObject(sql2, params2);
			
			String sql3="select p_money from package where package_id=?";
			Object[] params3= {package_id};
			Object p_money = DBUtil.queryObject(sql3, params3);
			
			String sql4="select `call` from package where package_id=?";
			Object[] params4= {package_id};
			int call =  (int) DBUtil.queryObject(sql4, params4);
			
			String sql5="select traffic from package where package_id=?";
			Object[] params5= {package_id};
			int traffic = (int) DBUtil.queryObject(sql5, params5);
			
			int temp1=call-(allConsume1/60);
			int temp2=traffic-(allConsume2/1024);
			jl3.setText(p_money+"元");
			jl5.setText(call+"分钟,还剩"+temp1+"分钟");
			jl7.setText(traffic+"M,还剩"+temp2+"M");
		} catch (Exception e) {
			// TODO: handle exceptio
			e.printStackTrace();
		}
		

	}

}
