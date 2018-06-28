package sosoFrm;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmRegister extends JFrame {

	private JPanel contentPane;
	private JTextField txtMobileNumber;
	private JPasswordField txtPwd1;
	private JPasswordField txtPwd2;
	private JFrame frmR;
	
	public FrmRegister() {
		
		frmR = this;
		
		setTitle("\u6CE8\u518C");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 454);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtMobileNumber = new JTextField();
		txtMobileNumber.setBounds(200, 58, 281, 21);
		contentPane.add(txtMobileNumber);
		txtMobileNumber.setColumns(10);
		
		txtPwd1 = new JPasswordField();
		txtPwd1.setBounds(200, 122, 281, 21);
		contentPane.add(txtPwd1);
		
		txtPwd2 = new JPasswordField();
		txtPwd2.setBounds(200, 192, 281, 21);
		contentPane.add(txtPwd2);
		
		
		JButton button = new JButton("\u6CE8\u518C");
		button.setBounds(154, 271, 93, 23);
		contentPane.add(button);
	
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//1.获得用户的输入
				String mobileNumber = txtMobileNumber.getText();
				String pwd1 = txtPwd1.getText();
				String pwd2 = txtPwd2.getText();
				//2.检查用户输入是否规范
				if(mobileNumber.length()!=11)
				{
					JOptionPane.showMessageDialog(null, "登录账号必须是11位的数字");
					return;
				}
				//    2.2 密码是6~20位
				if(pwd1.length()<6 || pwd1.length()>20)
				{
					JOptionPane.showMessageDialog(null, "密码长度必须是6~20位");
					return;
				}
				//    2.3 两次密码输入要一致
				if(!pwd1.equals(pwd2))
				{
					JOptionPane.showMessageDialog(null, "两次密码输入不一致");
					return;
				}

				//3.检查用户名是否存在数据库当中
				String sql1 = "select * from login where mobile_number=?";
				Object[] params1 = {mobileNumber};
				boolean find = DBUtil.queryExist(sql1, params1);
				
				if(find)
				{
					JOptionPane.showMessageDialog(null, "该登录账号已经被注册");
					return;
				}

				//4.检查用户表是否已经存在改号码
				String sql4 = "select * from user where mobile_number=?";
				Object[] params4 = {mobileNumber};
				boolean find1 = DBUtil.queryExist(sql4, params4);
				
				if(!find1)
				{
					JOptionPane.showMessageDialog(null, "该号码没有实名认证，是不能注册的");
					return;
				}

				//5.数据库操作
				String sql2 = "select user_id from user where mobile_number=?";
				Object[] params2 = {mobileNumber};
				Object user_id = DBUtil.queryObject(sql2, params2);
				
				//    4.2 增加数据到login表
				String sql3 = "insert into login (mobile_number, password, user_id) values (?,?,?)";
				Object[] params3 = {mobileNumber,pwd1,user_id};
				int n = DBUtil.noQuery(sql3,params3);
				if(n!=1){
					JOptionPane.showMessageDialog(null, "注册失败");
				}else {
					FrmLogin frmLogin=new FrmLogin(mobileNumber,pwd1);
					frmLogin.setVisible(true);
					frmR.dispose();
				}
 				
			}
		});
		
		
		JButton button_1 = new JButton("\u9000\u51FA");
		button_1.setBounds(374, 271, 93, 23);
		contentPane.add(button_1);
		
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
}
