package sosoFrm;

import java.awt.EventQueue;
import java.sql.ResultSet;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class FrmConsumeSelect extends JInternalFrame {

	public FrmConsumeSelect(String mobile_name) {
		setTitle("本月账单查询");
		
		
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
		
		JLabel jl1 = new JLabel("当前套餐总消费：");
		jl1.setBounds(10, 310, 109, 15);
		getContentPane().add(jl1);
		
		JLabel jl2 = new JLabel("00,00元");
		jl2.setBounds(106, 310, 54, 15);
		getContentPane().add(jl2);
		
		JLabel jl3 = new JLabel("套餐外消费：");
		jl3.setBounds(10, 335, 96, 15);
		getContentPane().add(jl3);
		
		JLabel jl4 = new JLabel("00.00元");
		jl4.setBounds(88, 335, 54, 15);
		getContentPane().add(jl4);
		
		JLabel jl5 = new JLabel("套餐费:");
		jl5.setBounds(10, 360, 54, 15);
		getContentPane().add(jl5);
		
		JLabel jl6 = new JLabel("00.00元");
		jl6.setBounds(59, 360, 54, 15);
		getContentPane().add(jl6);
		
		
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
						}
						if(rs.getInt(3) == 2) {
							jt1.setValueAt("上网", row, 1);
							jt1.setValueAt("kB", row, 2);
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
			jl2.setText(p_money+"元");
			jl6.setText(p_money+"元");
				
		} catch (Exception e) {
			// TODO: handle exceptio
			e.printStackTrace();
		}
		

	}
}
