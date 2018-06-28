package sosoFrm;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.FormatStyle;
import java.util.Date;
import java.awt.event.ActionEvent;

public class FrmPackage extends JInternalFrame {
	private JTable jt1;
	private int selectedRowIndex=0;
	private int package_id=0;
	private Date date;
	private DateFormat format;
	private String time;
	public FrmPackage(String mobile_name) {		
		
		//获取user_id
		String sql1 = "select user_id from user where mobile_number=?";
		Object[] params1 = {mobile_name};
		Object user_id = DBUtil.queryObject(sql1, params1);

		
		setBounds(100, 100, 450, 300);
		setTitle("套餐");
		JButton jb1 = new JButton("套餐申请");
		jb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//2.检查是否含有套餐，没有则无法更换
				String sql2 = "select * from package_record where user_id =?";
				Object[] params2 = {user_id};
				boolean find = DBUtil.queryExist(sql2, params2);
				if(find) {
					JOptionPane.showMessageDialog(null, "已有套餐，无法申请！");
				}else {
					//1.获取选中的套餐
					selectedRowIndex = jt1.getSelectedRow();
					package_id=(int) jt1.getValueAt(selectedRowIndex, 0);
					//2.创建time存储系统当前时间
					date = new Date();
					format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					time = format.format(date);
					//3.根据user_id来插入数据
					String sql3 = "insert into package_record(user_id,package_id,r_time,use_time,unuse_time) values (?,?,?,?,?)";
					Object[] params3= {user_id,package_id,time,time,null};
					int n = DBUtil.noQuery(sql3,params3);
					if(n!=1){
						JOptionPane.showMessageDialog(null, "申请失败");
					}else {
						JOptionPane.showMessageDialog(null, "申请成功");
					}
					
				}
			}
		});
		jb1.setBounds(20, 225, 101, 23);
		
		JButton jb2 = new JButton("套餐变更");
		jb2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//2.检查是否含有套餐，没有则无法更换
				String sql2 = "select * from package_record where user_id =?";
				Object[] params2 = {user_id};
				boolean find = DBUtil.queryExist(sql2, params2);
				if(!find) {
					JOptionPane.showMessageDialog(null, "没有套餐，无法更换！");
				}else {
					//3.获取选中的套餐
					selectedRowIndex = jt1.getSelectedRow();
					package_id=(int) jt1.getValueAt(selectedRowIndex, 0);
					//4.创建time存储系统当前时间
					date = new Date();
					format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					time = format.format(date);
					//5.根据user_id来替换数据库中的数据
					String sql3 = "update package_record set package_id=?,r_time=?,use_time=? where user_id=?";
					Object[] params3 = {package_id,date,date,user_id};
					int n = DBUtil.noQuery(sql3,params3);
					if(n!=1){
						JOptionPane.showMessageDialog(null, "更换失败");
					}else {
						JOptionPane.showMessageDialog(null, "更换成功");
					}
				}
				
				
				
			}
		});
		jb2.setBounds(162, 225, 101, 23);
		
		JButton jb3 = new JButton("退出套餐");
		jb3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		jb3.setBounds(309, 225, 101, 23);
		
		jt1 = new JTable(10,5);
		jt1.setBounds(10, 15, 414, 200);
		jt1.setRowHeight(20);
		jt1.setValueAt("套餐编号", 0, 0);
		jt1.setValueAt("套餐名称", 0, 1);
		jt1.setValueAt("套餐资费", 0, 2);
		jt1.setValueAt("免费通话时长", 0, 3);
		jt1.setValueAt("免费上网流量", 0, 4);
		
		//将package数据显示在表上
		try {
			for(int i=1;i<4;i++) {
				String sql3="select *from package where package_id="+i;
				ResultSet rs=DBUtil.queryResultSet(sql3);
				while(rs.next()) {
					jt1.setValueAt(rs.getInt(1), i, 0);
					jt1.setValueAt(rs.getString(2), i, 1);
					jt1.setValueAt(rs.getInt(3), i, 2);
					jt1.setValueAt(rs.getInt(4), i, 3);
					jt1.setValueAt(rs.getInt(5), i, 4);
				}
			}
		} catch (Exception e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		
		
		getContentPane().setLayout(null);
		getContentPane().add(jb1);
		getContentPane().add(jb2);
		getContentPane().add(jb3);
		getContentPane().add(jt1);
		setClosable(true);
	}
}
