package sosoFrm;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;

public class FrmRechange extends JInternalFrame {
	private JTextField jtf1;
	static int Recharge=0;
	private Date date;
	private DateFormat format;
	private String time;
	public FrmRechange(String mobile_name) {
		setBounds(100, 100, 450, 300);
		setTitle("充值");
		getContentPane().setLayout(null);
		JButton jb1 = new JButton("50");
		
		jb1.setBounds(29, 63, 93, 23);
		jb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Recharge=Integer.parseInt(jb1.getText());
			}
		});
		getContentPane().add(jb1);
		
		JButton jb2 = new JButton("100");
		jb2.setBounds(170, 63, 93, 23);
		jb2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Recharge=Integer.parseInt(jb2.getText());
				
			}
		});
		getContentPane().add(jb2);
		
		JButton jb3 = new JButton("200");
		jb3.setBounds(312, 63, 93, 23);
		jb3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Recharge=Integer.parseInt(jb3.getText());
			}
		});
		getContentPane().add(jb3);
		
		JButton jb4 = new JButton("300");
		jb4.setBounds(29, 135, 93, 23);
		jb4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Recharge=Integer.parseInt(jb4.getText());
			}
		});
		getContentPane().add(jb4);
		
		JButton jb5 = new JButton("500");
		jb5.setBounds(170, 135, 93, 23);
		jb5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Recharge=Integer.parseInt(jb5.getText());
			}
		});
		getContentPane().add(jb5);
		
		jtf1 = new JTextField();
		jtf1.setText("0");
		jtf1.setBounds(312, 136, 66, 21);
		getContentPane().add(jtf1);
		jtf1.setColumns(10);
		
		JButton jb6 = new JButton("充值");
		jb6.setBounds(170, 198, 93, 23);
		jb6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//判断输入框数是否为0
					if(!jtf1.getText().equals("0")) {
						int temp = Integer.parseInt(jtf1.getText());
						if(temp%100==0) {
							Recharge=temp;
						}else {
							JOptionPane.showMessageDialog(null, "请输入100的倍数");
							jtf1.setText("0");
						}
					}
					//1.利用mobile_name在user表查出user_id
					String sql1 = "select user_id from user where mobile_number=?";
					Object[] params1 = {mobile_name};
					Object user_id = DBUtil.queryObject(sql1, params1);
					//2.创建time存储系统当前时间
					date = new Date();
					format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					time = format.format(date);
					//3.利用user_id为record表插入数据
					String sql2 = "insert into money_record (user_id,r_time,r_money) values (?,?,?)";
					Object[] params2 = {user_id,time,Recharge};
					int n = DBUtil.noQuery(sql2,params2);
					if(n!=1){
						JOptionPane.showMessageDialog(null, "充值失败");
					}else {
						JOptionPane.showMessageDialog(null, "充值成功");
					}
					
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				
			}
		});
		getContentPane().add(jb6);
		setClosable(true);
	}
}
