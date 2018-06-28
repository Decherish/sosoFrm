package sosoFrm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class FrmLogin extends JFrame {
	private JPanel contentPane;
	private JPasswordField jpf;
	private JTextField jtf;
	private JFrame frmLogin = this;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmLogin frame = new FrmLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmLogin() {
		this("13866668888","123456");
	}
	
	public FrmLogin(String mobile_number,String password) {
		setTitle("登录");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 259);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		
		//登录按钮
		JButton btn1 = new JButton("登录");
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String loginName = jtf.getText();
				String loginPass = jpf.getText();
				
				String sql = "select user_id from login where mobile_number=? and password=?";
				
				Object[] params = {loginName,loginPass};
				Object user_id = DBUtil.queryObject(sql, params);
				
				boolean find = false;
				long id = (Long)user_id;
				
				if(id>0) {
					find = true;
				}
				
				String sql1 = "select user_name from user where user_id=? and status<>2";
				Object[] params1 = {user_id};
				Object user_name = DBUtil.queryObject(sql1, params1);
					
				if(find && user_name!=null) {
					FrmMain frmMain1 = new FrmMain(loginName);
					frmMain1.setVisible(true);
					frmMain1.setTitle("欢迎你,"+user_name+"!");
					frmLogin.dispose();
				}else {
					JOptionPane.showMessageDialog(null, "登录失败！");
				}
			}
		});
		btn1.setBounds(132, 153, 93, 23);
		contentPane.add(btn1);
		//退出按钮
		JButton btn2 = new JButton("退出");
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(ABORT);
			}
		});
		btn2.setBounds(257, 153, 93, 23);
		contentPane.add(btn2);
		//密码框
		jpf = new JPasswordField();
		jpf.setBounds(132, 93, 221, 21);
		jpf.setText(password);
		contentPane.add(jpf);
		//用户名框
		jtf = new JTextField();
		 jtf.setText(mobile_number);
		jtf.setBounds(132, 43, 221, 21);
		contentPane.add(jtf);
		jtf.setColumns(10);
		//用户名
		JLabel lb1 = new JLabel("用户名");
		lb1.setBounds(59, 42, 44, 23);
		contentPane.add(lb1);
		//密码
		JLabel lb2 = new JLabel("密  码");
		lb2.setBounds(59, 96, 54, 15);
		contentPane.add(lb2);
		//注册
		JLabel label = new JLabel("<html><u>\u70B9\u51FB\u8FD9\u91CC\u6CE8\u518C</u></html>");
		label.setForeground(Color.BLUE);
		label.setBounds(331, 200, 78, 21);
		label.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(label);
		// 点击这里注册的事件
		label.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FrmRegister frmR = new FrmRegister();
				frmR.setVisible(true);
				frmLogin.dispose();
			}
		});
	}
}
