package org.xsj.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.xsj.global.Global;
import org.xsj.global.TransFiles;
import org.xsj.global.TransObj;
import org.xsj.util.MyUtil;

public class PanelTalk extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String friend;
	private ChatLog chatLog;
	private Timer timer;
	/**
	 * 待发送的文件
	 */
	private File[] sendFiles ;
	private FileInputStream[] fis;
	public PanelTalk(String me, String friend) {
		this.friend = friend;
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 580);
		setLocation(400, 130);
		// 设置Timer定时器，并启动
		timer = new Timer(500, this);
		timer.start();

		BorderLayout gl = new BorderLayout(3, 3);
		BorderLayout gl2 = new BorderLayout(3, 3);
		setLayout(gl);
		Container c = this.getContentPane();
		c.add(new Top(friend), BorderLayout.NORTH);
		ChatLog cl = new ChatLog();
		chatLog = cl;
		c.add(cl, BorderLayout.CENTER);
		// c.add(new Talk(),BorderLayout.SOUTH);
		JPanel jp = new JPanel(gl2);
		jp.setLayout(gl2);
		Talk t = new Talk();
		jp.add(t, BorderLayout.NORTH);
		jp.add(new Operation(t, cl, me, friend), BorderLayout.SOUTH);
		c.add(jp, BorderLayout.SOUTH);
		// c.add(new Operation(),BorderLayout.SOUTH);
		setVisible(true);
	}

	class Top extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Top(String name) {
			JPanel a = new JPanel();
			a.setBorder(BorderFactory.createTitledBorder("好友"));
			a.setPreferredSize(new Dimension(500, 50));
			JTextField f = new JTextField();
			f.setText(name);
			a.add(f);
			this.add(a);
		}

	}

	class ChatLog extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JTextArea jt = new JTextArea();

		public ChatLog() {
			JPanel a = new JPanel();
			a.setPreferredSize(new Dimension(500, 220));
			a.setBorder(BorderFactory.createTitledBorder("聊天记录"));

			jt.setWrapStyleWord(true);
			jt.setLineWrap(true);
			jt.setRows(10);
			jt.setColumns(40);
			JScrollPane scrollPane = new JScrollPane(jt);
			scrollPane
					.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			a.add(scrollPane);
			jt.setEditable(false);
			jt.getCaret().addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					jt.getCaret().setVisible(true); // 使Text区的文本光标显示
				}
			});
			this.add(a);
		}

		public JTextArea getJTA() {
			return jt;
		}

	}

	class Talk extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JTextArea jt = new JTextArea();

		public Talk() {
			JPanel a = new JPanel();
			a.setPreferredSize(new Dimension(500, 130));
			a.setBorder(BorderFactory.createTitledBorder("会话框"));

			jt.setWrapStyleWord(true);
			jt.setLineWrap(true);
			jt.setRows(5);
			jt.setColumns(40);
			// a.add(jt);
			JScrollPane scrollPane = new JScrollPane(jt);
			// scrollPane.setHorizontalScrollBarPolicy(
			// JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollPane
					.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			a.add(scrollPane);
			this.add(a);
		}

		public JTextArea getJTA() {
			return jt;
		}
	}

	class Operation extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm:ss");

		public Operation(final Talk t, final ChatLog c, final String name,
				final String friend) {
			JPanel a = new JPanel();
			a.setPreferredSize(new Dimension(500, 50));
			a.setBorder(BorderFactory.createTitledBorder("操作"));
			JButton jb = new JButton();
			jb.setText("发送");

			jb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					JTextArea tt = t.getJTA();
					if (tt.getText() != null && !"".equals(tt.getText())) {
						JTextArea ct = c.getJTA();
						String info = "[" + sdf.format(new Date()) + "]" + name
								+ ":" + tt.getText() + "\n";
						ct.append(info);
						tt.setText("");
						TransObj t = new TransObj();
						t.setHead(MyUtil.talkMsg(name, friend));
						t.setObj(info);
						Global.gMsg().add(t);
						if(sendFiles != null && fis != null && fis.length > 0 && sendFiles.length > 0 ){
							TransObj tf = new TransObj();
							TransFiles tfs = new TransFiles();
							tfs.setFiles(sendFiles);
							tfs.setFis(fis);
							tf.setHead(MyUtil.talkMsg(name, friend));
							tf.setObj(tfs);
							Global.gMsg().add(tf);
						}
					}
				}
			});
			JButton jb3 = new JButton();
			jb3.setText("选择文件");

			jb3.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					JFileChooser jfc = new JFileChooser();
					jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
					jfc.setMultiSelectionEnabled(true);
					jfc.showDialog(new JLabel(), "选择");
					File[] files = jfc.getSelectedFiles();
					if(files != null && files.length > 0){
						fis = new FileInputStream[files.length];
						setSendFiles(files);
						t.jt.append("\n");
						for (int j = 0; j < files.length; j++) {
							t.jt.append("文件:" + files[j].getName() + "\n");
							try {
								fis[j] = new FileInputStream(files[j]);
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}
						}
					}
				
				}
			});

			a.add(jb3);

			JButton jb2 = new JButton();
			jb2.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					PanelFriends.getUserJFmap().get(friend).setVisible(false);
					t.jt.setText(null);
					setSendFiles(null);
					setFis(null);

				}
			});
			jb2.setText("关闭");
			a.add(jb);
			a.add(jb2);

			this.add(a);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Map<String, JFrame> userJFmap = PanelFriends.getUserJFmap();
		JFrame jf = userJFmap.get(friend);
		if (jf != null) {
			Vector<TransObj> vt = Global.getTalkMsg().get(friend);

			if (vt != null && vt.size() > 0) {
				chatLog.jt.append(vt.get(0).getObj().toString());
				vt.remove(0);
			}
		}
		// DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Date date = new Date();
		// chatLog.jt.append(format.format(date) + "\n");

	}

	public File[] getSendFiles() {
		return sendFiles;
	}

	public void setSendFiles(File[] sendFiles) {
		this.sendFiles = sendFiles;
	}

	public FileInputStream[] getFis() {
		return fis;
	}

	public void setFis(FileInputStream[] fis) {
		this.fis = fis;
	}

}
