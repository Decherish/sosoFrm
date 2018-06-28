package sosoFrm;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;

public class FrmConsume extends JInternalFrame {
	private JTextField jtf1;
	private JTextField jtf2;
	private Date date;
	private DateFormat format;
	private String time;
	private int Converse=0;
	private int net_play=0;
	private int temp=0;

	public FrmConsume(String mobile_name) {
		setTitle("模拟消费");
		setBounds(100, 100, 450, 300);
		setClosable(true);
		getContentPane().setLayout(null);
		
		JLabel jl1 = new JLabel("模拟通话：");
		jl1.setFont(new Font("宋体", Font.PLAIN, 14));
		jl1.setBounds(92, 66, 76, 33);
		getContentPane().add(jl1);
		
		jtf1 = new JTextField();
		jtf1.setBounds(164, 72, 129, 21);
		getContentPane().add(jtf1);
		jtf1.setColumns(10);
		jtf1.setText("0");
		
		JLabel jl2 = new JLabel("秒");
		jl2.setFont(new Font("宋体", Font.PLAIN, 14));
		jl2.setBounds(303, 75, 54, 15);
		getContentPane().add(jl2);
		
		JLabel jl3 = new JLabel("模拟上网:");
		jl3.setFont(new Font("宋体", Font.PLAIN, 14));
		jl3.setBounds(92, 135, 76, 33);
		getContentPane().add(jl3);
		
		jtf2 = new JTextField();
		jtf2.setBounds(164, 141, 129, 21);
		getContentPane().add(jtf2);
		jtf2.setColumns(10);
		jtf2.setText("0");
		
		JLabel jl4 = new JLabel("M");
		jl4.setFont(new Font("宋体", Font.PLAIN, 14));
		jl4.setBounds(303, 144, 34, 15);
		getContentPane().add(jl4);
		
		JButton jb1 = new JButton("消费");
		jb1.setBounds(92, 207, 93, 23);
		getContentPane().add(jb1);
		jb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Converse=Integer.parseInt(jtf1.getText());
				net_play=Integer.parseInt(jtf2.getText());
				if(Converse !=0 && net_play !=0) {
					JOptionPane.showMessageDialog(null, "上网和通话，二者不可兼得");
					jtf1.setText("0");
					jtf2.setText("0");
				}else {
					//1.利用mobile_name在user表查出user_id
					String sql1 = "select user_id from user where mobile_number=?";
					Object[] params1 = {mobile_name};
					Object user_id = DBUtil.queryObject(sql1, params1);
					//2.创建time存储系统当前时间
					date = new Date();
					format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					time = format.format(date);
					//3.通过user_id查出套餐剩余情况并且与消费做对比
					if(Converse!=0) {
						temp=1;
						//4.通过uer_id来对cost_record数据库进行插入，默认不会超出套餐
						String sql2="insert into cost_record (user_id,cost_type,cost_quantity,r_time,isOverPackage,cost_money) values (?,?,?,?,?,?)";
						Object[] params2= {user_id,temp,Converse,date,0,0};
						int n = DBUtil.noQuery(sql2,params2);
						if(n!=1){
							JOptionPane.showMessageDialog(null, "模拟失败");
						}else {
							JOptionPane.showMessageDialog(null, "模拟成功");
							jtf1.setText("0");
						}
						
					}
					if(net_play!=0) {
						temp=2;					
						//4.通过uer_id来对cost_record数据库进行插入，默认不会超出套餐
						String sql2="insert into cost_record (user_id,cost_type,cost_quantity,r_time,isOverPackage,cost_money) values (?,?,?,?,?,?)";
						Object[] params2= {user_id,temp,net_play,date,0,0};
						int n = DBUtil.noQuery(sql2,params2);
						if(n!=1){
							JOptionPane.showMessageDialog(null, "模拟失败");
						}else {
							JOptionPane.showMessageDialog(null, "模拟成功");
							jtf2.setText("0");
						}
					}
				
				}
				
			}
		});
		
		JButton jb2 = new JButton("退网");
		jb2.setBounds(234, 207, 93, 23);
		jb2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//1.利用mobile_name在user表查出user_id
				String sql1 = "select user_id from user where mobile_number=?";
				Object[] params1 = {mobile_name};
				Object user_id = DBUtil.queryObject(sql1, params1);
				//2.根据user_id删除login中的数据
				String sql2 = "delete from login where user_id=?";
				Object[] params2= {user_id};
				int n = DBUtil.noQuery(sql2,params2);
				if(n!=1){
					JOptionPane.showMessageDialog(null, "退网失败");
				}else {
					JOptionPane.showMessageDialog(null, "退网成功");
				}
			}
		});
		getContentPane().add(jb2);
	}
}
