package org.xsj.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.xsj.global.Global;
import org.xsj.global.TransObj;
import org.xsj.security.User;

public class PanelFriends extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String top;
	private Vector<User> friends;
	private String me;
	private static Map<String, JFrame> userJFmap = new HashMap<String, JFrame>();
	private Timer timer;

	DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public PanelFriends(String me, String top,
			Vector<org.xsj.security.User> vector) {
		this.me = me;
		this.top = top;
		this.friends = vector;

		// 设置Timer定时器，并启动
		timer = new Timer(500, this);
		timer.start();

		this.setTitle("TalkList");
		this.setLayout(g);
		init();
		// 窗体可见
		this.setVisible(true);
		// 设置布局方式为绝对定位
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(220, 600);
		setLocationRelativeTo(null);// 设居中显示;
		// setBounds(100, 100, 220, 500);
	}

	GridBagLayout g = new GridBagLayout();
	GridBagConstraints c = new GridBagConstraints();
	JButton bo1, bo2;
	JList v;
	JTextField f1, f2;
	JScrollPane js1;
	JPanel j1;

	public void init() {

		f1 = new JTextField();
		f1.setText(top);
		addp(g, c, f1, 0, 0, 1, 1);

		f2 = new JTextField();
		f2.setText("好友列表");

		Vector<String> showf = new Vector<String>();
		for (User u : friends) {
			showf.add(u.getUsername());
		}
		v = new JList(showf);
		v.setBorder(BorderFactory.createTitledBorder("好友框"));
		js1 = new JScrollPane(v);
		js1.setPreferredSize(new Dimension(200, 400));
		addp(g, c, js1, 0, 1, 4, 1);

		bo1 = new JButton("发送信息");
		bo1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object friend = v.getSelectedValue();
				if (friend != null && !"".equals(friend.toString())) {
					JFrame jf = getUserJFmap().get(friend.toString());
					if (jf == null) {
						JFrame a = new PanelTalk(me, friend.toString());
						getUserJFmap().put(friend.toString(), a);
					} else {
						jf.setVisible(true);
					}

				}

			}
		});
		bo1.setPreferredSize(new Dimension(150, 30));
		addp(g, c, bo1, 0, 2, 1, 1);

		Date date = new Date();
		bo2 = new JButton();
		bo2.setText(format.format(date));
		bo2.setPreferredSize(new Dimension(150, 30));
		bo2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String newmsg = bo2.getText();
				if (newmsg.startsWith("有新消息")) {
					String users = newmsg.substring(newmsg.indexOf(':') + 1);
					String[] u = users.split(",");
					if(u != null){
						for (int i = 0; i < u.length; i++) {
							if(!"".equals(u[i])){
							 JFrame uJFrame  =	userJFmap.get(u[i]);
							 if(uJFrame ==  null){
								 	JFrame fJframe = new PanelTalk(me, u[i]);
									getUserJFmap().put( u[i], fJframe);
							 }else{
								 if(!uJFrame.isVisible()){
									 uJFrame.setVisible(true);
								 }
							 }
							}
						}
						
					}
					
					bo2.setText("[消息盒子]");
				}
			}
		});
		addp(g, c, bo2, 0, 3, 1, 1);

	}

	public void addp(GridBagLayout g, GridBagConstraints c, JComponent jc,
			int x, int y, int gw, int gh) {
		c.gridx = x;
		c.gridy = y;
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = gw;
		c.gridheight = gh;
		c.weightx = 1;
		c.weighty = 1;
		// c.fill = GridBagConstraints.BOTH;
		g.setConstraints(jc, c);
		add(jc);
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Date date = new Date();
		// bo2.setText(format.format(date));
		String tips = "";
		Map<String, Vector<TransObj>> userMsg = Global.getTalkMsg();
		for (String username : userMsg.keySet()) {
			Vector<TransObj> vt = userMsg.get(username);
			JFrame uJrame = userJFmap.get(username);
			if (vt != null && vt.size() > 0) {
				if (uJrame == null) {
					tips += username + ",";
				} else {
					if (uJrame.isVisible()) {// 已经显示
						// 不处理
					} else {
						tips += username + ",";
					}

				}

			}

		}
		if (!"".equals(tips)) {

			bo2.setText("有新消息:" + tips);
		}
	}

	public String getMe() {
		return me;
	}

	public void setMe(String me) {
		this.me = me;
	}

	public static Map<String, JFrame> getUserJFmap() {
		return userJFmap;
	}

	public static void setUserJFmap(Map<String, JFrame> userJFmap) {
		PanelFriends.userJFmap = userJFmap;
	}

}
