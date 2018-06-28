package sosoFrm;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FrmMain extends JFrame {

	JDesktopPane desktopPane;
	/**
	 * Create the frame.
	 */
	public FrmMain(String mobile_number) {
		// 窗体基本代码
		setBounds(100, 100, 1037, 725);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 放入桌面面板，以便容纳子窗口
		desktopPane = new JDesktopPane();
		getContentPane().add(desktopPane, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		getContentPane().add(menuBar, BorderLayout.NORTH);
		
		JButton jb1 = new JButton("充值");
		jb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrmRechange frmRechange=new FrmRechange(mobile_number);
				frmRechange.setVisible(true);
				desktopPane.add(frmRechange);
			}
		});
		menuBar.add(jb1);
		JButton jb2 = new JButton("套餐");
		jb2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrmPackage frmPackage=new FrmPackage(mobile_number);
				frmPackage.setVisible(true);
				desktopPane.add(frmPackage);
			}
		});
		menuBar.add(jb2);
		
		JButton jb3 = new JButton("本月账单查询");
		jb3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrmConsumeSelect frmConsumeSelect = new FrmConsumeSelect(mobile_number);
				frmConsumeSelect.setVisible(true);
				desktopPane.add(frmConsumeSelect);
			}
		});
		menuBar.add(jb3);
		
		JButton jb4 = new JButton("套餐余额查询");
		jb4.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				FrmSelect fromSelect=new FrmSelect(mobile_number);
				fromSelect.setVisible(true);
				desktopPane.add(fromSelect);
				
			}
		});
		menuBar.add(jb4);
		
		JButton jb5 = new JButton("模拟消费");
		jb5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrmConsume frmConsume=new FrmConsume(mobile_number);
				frmConsume.setVisible(true);
				desktopPane.add(frmConsume);
			}
		});
		menuBar.add(jb5);
	}
}
