package danor.matookit.gui;

import danor.matookit.natures.*;
import danor.matookit.utils.*;
import danor.matookit.middles.*;
import danor.matookit.functions.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.Window.Type;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JCheckBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.Font;

public class GMain7
{
	private JFrame frmMain;
	
	private TrayIcon tray;
	
	private FAction action;
	
	private JComboBox<String> iptAcout;
	private JPasswordField iptPaswd;
	private JComboBox<String> iptServer;
	private JComboBox<String> iptJumpto;
	
	private JButton btnLogin;
	private JTextField iptFryInterval;
	private JTable tblFryList;
	private JTextField valIfoName;
	private JTable tblExpArea;
	private JTable tblExpLog;

	private JLabel valIfoLevel;
	private JLabel valIfoCountry;
	private JLabel valIfoIDUser;
	private JLabel valIfoGoldCoin;
	private JLabel valIfoMillionCost;
	private JLabel valIfoFriendPoint;
	private JLabel valIfoGachaTicket;
	private JLabel valIfoGachaPoint;
	private JLabel valIfoFriendNow;
	private JLabel valIfoFriendMax;
	private JLabel valIfoFriendIvt;
	private JButton btnIfoCopyName;
	private JCheckBox cbxRemAcount;
	private JCheckBox cbxRemPasswd;


	private JLabel valAP;
	private JLabel valBC;
	private JLabel valExgauge;
	private JButton prgExgauge;
	private JButton prgAP;
	private JButton prgBC;

	private JLabel valItmBCHalf;
	private JLabel valItmBC;
	private JLabel valItmAPHalf;
	private JLabel valItmAP;
	private JButton btnAPRev;
	private JButton btnBCRev;
	private JButton btnAPRevHalf;
	private JButton btnBCRevHalf;

	private JButton btnHaveFairy;
	private JButton btnHaveReward;
	private JButton btnHaveNew;

	private JButton btnFryControl;
	private JTable tblFryHistory;

	private Timer timerPero;

	private JButton btnExpRefresh;

	private JButton btnExpControl;

	private JLabel valExpArea;

	private Timer timerExp;

	private JComboBox<String> cbxExpFloor;

	private final String title = "[DanoR MATookit][Version.7.3.2][Build.2014.05.12.A]";

	private FServer server;

	private void initialize() throws Exception
	{
//		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
		UIManager.put("RootPane.setupButtonVisible", false);

        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        
		for(Enumeration<?> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();)
		{
			Object key = keys.nextElement();
			if(UIManager.get(key) instanceof FontUIResource)
				UIManager.put(key, new FontUIResource(new Font("Arial Unicode MS", Font.PLAIN, 12)));
		}

		frmMain = new JFrame();
		frmMain.setResizable(false);
		frmMain.setType(Type.POPUP);
		frmMain.setAutoRequestFocus(true);
		frmMain.setTitle(title);
		frmMain.setIconImage(Toolkit.getDefaultToolkit().getImage(FServer.dirGuiAll.getPath()+"/ico.png"));
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frmMain.setBounds((d.width - 848) / 2, (d.height - 480) / 2, 848, 480);
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMain.getContentPane().setLayout(null);
		
		JTabbedPane tabMain = new JTabbedPane(JTabbedPane.TOP);
		tabMain.setBounds(0, 0, 840, 450);
		frmMain.getContentPane().add(tabMain);
		
		JPanel pnlInfo = new JPanel();
		tabMain.addTab("操作中心", null, pnlInfo, null);
		pnlInfo.setLayout(null);
		
		JPanel pnlNature = new JPanel();
		pnlNature.setBorder(new TitledBorder(null, "\u5C5E\u6027", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlNature.setBounds(10, 170, 340, 230);
		pnlInfo.add(pnlNature);
		pnlNature.setLayout(null);
		
		valIfoName = new JTextField();
		valIfoName.setBounds(70, 20, 200, 20);
		pnlNature.add(valIfoName);
		valIfoName.setColumns(10);
		
		valIfoLevel = new JLabel("null");
		valIfoLevel.setBounds(70, 50, 80, 20);
		pnlNature.add(valIfoLevel);
		
		valIfoCountry = new JLabel("null");
		valIfoCountry.setBounds(70, 80, 80, 20);
		pnlNature.add(valIfoCountry);
		
		valIfoIDUser = new JLabel("null");
		valIfoIDUser.setBounds(70, 110, 80, 20);
		pnlNature.add(valIfoIDUser);
		
		valIfoGoldCoin = new JLabel("null");
		valIfoGoldCoin.setBounds(70, 140, 80, 20);
		pnlNature.add(valIfoGoldCoin);
		
		valIfoMillionCost = new JLabel("null");
		valIfoMillionCost.setBounds(70, 170, 80, 20);
		pnlNature.add(valIfoMillionCost);
		
		valIfoFriendPoint = new JLabel("null");
		valIfoFriendPoint.setBounds(70, 200, 80, 20);
		pnlNature.add(valIfoFriendPoint);
		
		valIfoGachaTicket = new JLabel("null");
		valIfoGachaTicket.setBounds(230, 50, 80, 20);
		pnlNature.add(valIfoGachaTicket);
		
		valIfoGachaPoint = new JLabel("null");
		valIfoGachaPoint.setBounds(230, 80, 80, 20);
		pnlNature.add(valIfoGachaPoint);
		
		valIfoFriendNow = new JLabel("null");
		valIfoFriendNow.setBounds(230, 110, 80, 20);
		pnlNature.add(valIfoFriendNow);
		
		valIfoFriendMax = new JLabel("null");
		valIfoFriendMax.setBounds(230, 140, 80, 20);
		pnlNature.add(valIfoFriendMax);
		
		valIfoFriendIvt = new JLabel("null");
		valIfoFriendIvt.setBounds(230, 170, 80, 20);
		pnlNature.add(valIfoFriendIvt);
		
		JLabel lblIfoName = new JLabel("昵称");
		lblIfoName.setBounds(10, 20, 50, 20);
		pnlNature.add(lblIfoName);
		
		JLabel lblIfoLevel = new JLabel("等级");
		lblIfoLevel.setBounds(10, 50, 50, 20);
		pnlNature.add(lblIfoLevel);
		
		JLabel lblIfoCountry = new JLabel("阵营");
		lblIfoCountry.setBounds(10, 80, 50, 20);
		pnlNature.add(lblIfoCountry);
		
		JLabel lblIfoIDUser = new JLabel("角色ID");
		lblIfoIDUser.setBounds(10, 110, 50, 20);
		pnlNature.add(lblIfoIDUser);
		
		JLabel lblIfoGoldCoin = new JLabel("金币");
		lblIfoGoldCoin.setBounds(10, 140, 50, 20);
		pnlNature.add(lblIfoGoldCoin);
		
		JLabel lblIfoMillionCoin = new JLabel("MC");
		lblIfoMillionCoin.setBounds(10, 170, 50, 20);
		pnlNature.add(lblIfoMillionCoin);
		
		JLabel lblIfoFriendPoint = new JLabel("友情点");
		lblIfoFriendPoint.setBounds(10, 200, 50, 20);
		pnlNature.add(lblIfoFriendPoint);
		
		JLabel lblIfoGachaTicket = new JLabel("扭蛋劵");
		lblIfoGachaTicket.setBounds(170, 50, 50, 20);
		pnlNature.add(lblIfoGachaTicket);
		
		JLabel lblIfoGachaPoint = new JLabel("扭蛋点");
		lblIfoGachaPoint.setBounds(170, 80, 50, 20);
		pnlNature.add(lblIfoGachaPoint);
		
		JLabel lblIfoFriendNow = new JLabel("好友数");
		lblIfoFriendNow.setBounds(170, 110, 50, 20);
		pnlNature.add(lblIfoFriendNow);
		
		JLabel blIfoFriendMax = new JLabel("好友上限");
		blIfoFriendMax.setBounds(170, 140, 50, 20);
		pnlNature.add(blIfoFriendMax);
		
		JLabel lblIfoFriendIvt = new JLabel("好友邀请");
		lblIfoFriendIvt.setBounds(170, 170, 50, 20);
		pnlNature.add(lblIfoFriendIvt);
		
		btnIfoCopyName = new JButton("复制");
		btnIfoCopyName.setBounds(280, 20, 50, 20);
		pnlNature.add(btnIfoCopyName);
		
		JPanel pnlLogin = new JPanel();
		pnlLogin.setBorder(new TitledBorder(null, "\u767B\u5F55", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlLogin.setBounds(10, 10, 340, 150);
		pnlInfo.add(pnlLogin);
		pnlLogin.setLayout(null);
		
		JLabel lblAcout = new JLabel("帐号");
		lblAcout.setBounds(10, 20, 45, 20);
		pnlLogin.add(lblAcout);
		
		JLabel lblPaswd = new JLabel("密码");
		lblPaswd.setBounds(10, 50, 45, 20);
		pnlLogin.add(lblPaswd);
		
		JLabel lblServer = new JLabel("服务器");
		lblServer.setBounds(10, 80, 45, 20);
		pnlLogin.add(lblServer);
		
		JLabel lblJumpto = new JLabel("跳转到");
		lblJumpto.setBounds(165, 80, 45, 20);
		pnlLogin.add(lblJumpto);
		
		iptAcout = new JComboBox<String>();
		iptAcout.setBounds(65, 20, 130, 20);
		pnlLogin.add(iptAcout);
		iptAcout.setEditable(true);
		
		iptPaswd = new JPasswordField();
		iptPaswd.setBounds(65, 50, 130, 20);
		pnlLogin.add(iptPaswd);
		iptPaswd.setEchoChar((char)0);
		
		iptServer = new JComboBox<String>();
		iptServer.setModel(new DefaultComboBoxModel<String>(new String[] {"国服一区", "国服二区", "国服三区", "海服一区","韩服一区"}));
		iptServer.setBounds(65, 80, 90, 20);
		pnlLogin.add(iptServer);
		
		iptJumpto = new JComboBox<String>();
		iptJumpto.setEnabled(false);
		iptJumpto.setModel(new DefaultComboBoxModel<String>(new String[] {"智能舔怪", "智能探索"}));
		iptJumpto.setBounds(220, 80, 90, 20);
		pnlLogin.add(iptJumpto);
		
		cbxRemAcount = new JCheckBox("记住帐号");
		cbxRemAcount.setEnabled(false);
		cbxRemAcount.setBounds(10, 110, 90, 20);
		pnlLogin.add(cbxRemAcount);
		
		cbxRemPasswd = new JCheckBox("记住密码");
		cbxRemPasswd.setEnabled(false);
		cbxRemPasswd.setBounds(105, 110, 90, 20);
		pnlLogin.add(cbxRemPasswd);
		
		btnLogin = new JButton("登录");
		btnLogin.setBounds(205, 20, 125, 50);
		pnlLogin.add(btnLogin);
		
		JPanel pnlPoint = new JPanel();
		pnlPoint.setLayout(null);
		pnlPoint.setBorder(new TitledBorder(null, "\u72B6\u6001-\u70B9\u6570", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(58, 135, 173)));
		pnlPoint.setBounds(360, 10, 190, 250);
		pnlInfo.add(pnlPoint);
		
		JLabel lblExgauge = new JLabel("SP");
		lblExgauge.setHorizontalAlignment(SwingConstants.CENTER);
		lblExgauge.setBounds(10, 20, 50, 20);
		pnlPoint.add(lblExgauge);
		
		JLabel lblAP = new JLabel("AP");
		lblAP.setHorizontalAlignment(SwingConstants.CENTER);
		lblAP.setBounds(10, 50, 50, 20);
		pnlPoint.add(lblAP);
		
		JLabel lblBC = new JLabel("BC");
		lblBC.setHorizontalAlignment(SwingConstants.CENTER);
		lblBC.setBounds(10, 80, 50, 20);
		pnlPoint.add(lblBC);
		
		valAP = new JLabel("");
		valAP.setHorizontalAlignment(SwingConstants.CENTER);
		valAP.setBounds(70, 50, 100, 20);
		pnlPoint.add(valAP);
		
		valBC = new JLabel("");
		valBC.setHorizontalAlignment(SwingConstants.CENTER);
		valBC.setBounds(70, 80, 100, 20);
		pnlPoint.add(valBC);
		
		valExgauge = new JLabel("");
		valExgauge.setHorizontalAlignment(SwingConstants.CENTER);
		valExgauge.setBounds(70, 20, 100, 20);
		pnlPoint.add(valExgauge);
		
		prgExgauge = new JButton("");
		prgExgauge.setForeground(Color.WHITE);
		prgExgauge.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
		prgExgauge.setBounds(70, 20, 0, 20);
		pnlPoint.add(prgExgauge);
		
		JButton prgExgaugeButton = new JButton("");
		prgExgaugeButton.setBounds(70, 19, 100, 22);
		pnlPoint.add(prgExgaugeButton);
		
		prgAP = new JButton("");
		prgAP.setForeground(new Color(255, 255, 255));
		prgAP.setBounds(70, 50, 0, 20);
		prgAP.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		pnlPoint.add(prgAP);
		
		JButton prgAPButton = new JButton("");
		prgAPButton.setBounds(70, 49, 100, 22);
		pnlPoint.add(prgAPButton);
		
		prgBC = new JButton("");
		prgBC.setForeground(new Color(255, 255, 255));
		prgBC.setBounds(70, 80, 0, 20);
		prgBC.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
		pnlPoint.add(prgBC);
		
		JButton prgBCButton = new JButton("");
		prgBCButton.setBounds(70, 79, 100, 22);
		pnlPoint.add(prgBCButton);
		
		valItmAP = new JLabel("");
		valItmAP.setHorizontalAlignment(SwingConstants.CENTER);
		valItmAP.setBounds(20, 110, 70, 20);
		pnlPoint.add(valItmAP);
		
		valItmAPHalf = new JLabel("");
		valItmAPHalf.setHorizontalAlignment(SwingConstants.CENTER);
		valItmAPHalf.setBounds(100, 110, 70, 20);
		pnlPoint.add(valItmAPHalf);
		
		valItmBC = new JLabel("");
		valItmBC.setHorizontalAlignment(SwingConstants.CENTER);
		valItmBC.setBounds(20, 180, 70, 20);
		pnlPoint.add(valItmBC);
		
		valItmBCHalf = new JLabel("");
		valItmBCHalf.setHorizontalAlignment(SwingConstants.CENTER);
		valItmBCHalf.setBounds(100, 180, 70, 20);
		pnlPoint.add(valItmBCHalf);
		
		btnAPRev = new JButton("嗑绿");
		btnAPRev.setForeground(new Color(255, 255, 255));
		btnAPRev.setBounds(20, 140, 70, 30);
		pnlPoint.add(btnAPRev);
		btnAPRev.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		
		btnBCRev = new JButton("嗑红");
		btnBCRev.setForeground(new Color(255, 255, 255));
		btnBCRev.setBounds(20, 210, 70, 30);
		pnlPoint.add(btnBCRev);
		btnBCRev.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
		
		btnAPRevHalf = new JButton("嗑半绿");
		btnAPRevHalf.setForeground(new Color(255, 255, 255));
		btnAPRevHalf.setBounds(100, 140, 70, 30);
		btnAPRevHalf.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		pnlPoint.add(btnAPRevHalf);
		
		btnBCRevHalf = new JButton("嗑半红");
		btnBCRevHalf.setForeground(new Color(255, 255, 255));
		btnBCRevHalf.setBounds(100, 210, 70, 30);
		btnBCRevHalf.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
		pnlPoint.add(btnBCRevHalf);
		
		JPanel pnlState = new JPanel();
		pnlState.setLayout(null);
		pnlState.setBorder(new TitledBorder(null, "\u72B6\u6001-\u636E\u70B9", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(58, 135, 173)));
		pnlState.setBounds(360, 260, 190, 140);
		pnlInfo.add(pnlState);
		
		btnHaveFairy = new JButton("{有无妖精出现}");
		btnHaveFairy.setBounds(10, 20, 170, 30);
//		btnHaveFairy.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
		pnlState.add(btnHaveFairy);
		
		btnHaveReward = new JButton("{有无未领取奖励}");
		btnHaveReward.setBounds(10, 60, 170, 30);
//		btnHaveReward.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
		pnlState.add(btnHaveReward);
		
		btnHaveNew = new JButton("{有无新动态}");
		btnHaveNew.setBounds(10, 100, 170, 30);
		btnHaveNew.setVisible(false);
		pnlState.add(btnHaveNew);
		
		JPanel pnlAutoFight = new JPanel();
		tabMain.addTab("智能舔怪", null, pnlAutoFight, null);
		pnlAutoFight.setLayout(null);
		
		JPanel pnlFairyList = new JPanel();
		pnlFairyList.setBorder(new TitledBorder(null, "\u5996\u7CBE\u5217\u8868", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlFairyList.setBounds(10, 10, 580, 390);
		pnlAutoFight.add(pnlFairyList);
		pnlFairyList.setLayout(null);
		
		JScrollPane srlFairyList = new JScrollPane();
		srlFairyList.setBounds(10, 20, 560, 360);
		pnlFairyList.add(srlFairyList);
		
		tblFryList = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) { return false; }};
		srlFairyList.setViewportView(tblFryList);
		
		JPanel pnlFairyControl = new JPanel();
		pnlFairyControl.setLayout(null);
		pnlFairyControl.setBorder(new TitledBorder(null, "\u667A\u80FD\u8214\u602A-\u64CD\u4F5C", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(58, 135, 173)));
		pnlFairyControl.setBounds(720, 10, 110, 240);
		pnlAutoFight.add(pnlFairyControl);
		
		JLabel lblInterval = new JLabel("刷新间隔(秒):");
		lblInterval.setBounds(10, 20, 90, 20);
		pnlFairyControl.add(lblInterval);
		
		iptFryInterval = new JTextField("24");
		iptFryInterval.setBounds(10, 50, 90, 20);
		pnlFairyControl.add(iptFryInterval);
		
		btnFryControl = new JButton("开始");
		btnFryControl.setEnabled(false);
		btnFryControl.setActionCommand("actAutoFight");
		btnFryControl.setBounds(10, 80, 90, 30);
		pnlFairyControl.add(btnFryControl);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("只舔觉醒");
		chckbxNewCheckBox.setBounds(10, 120, 90, 20);
		pnlFairyControl.add(chckbxNewCheckBox);
		
		JPanel pnlFairyCount = new JPanel();
		pnlFairyCount.setBorder(new TitledBorder(null, "\u7EDF\u8BA1", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlFairyCount.setBounds(720, 260, 110, 140);
		pnlAutoFight.add(pnlFairyCount);
		pnlFairyCount.setLayout(null);
		
		JLabel lblFairyRefreshCount = new JLabel("刷新次数");
		lblFairyRefreshCount.setBounds(10, 20, 50, 20);
		pnlFairyCount.add(lblFairyRefreshCount);
		
		JLabel valFairyRefreshCount = new JLabel("0");
		valFairyRefreshCount.setBounds(10, 50, 50, 20);
		pnlFairyCount.add(valFairyRefreshCount);
		
		JLabel lblFairyPerorCount = new JLabel("舔怪次数");
		lblFairyPerorCount.setBounds(10, 80, 50, 20);
		pnlFairyCount.add(lblFairyPerorCount);
		
		JLabel valFairyPerorCount = new JLabel("0");
		valFairyPerorCount.setBounds(10, 110, 110, 20);
		pnlFairyCount.add(valFairyPerorCount);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(null, "\u5361\u7EC4\u914D\u7F6E", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(58, 135, 173)));
		panel_2.setBounds(600, 10, 110, 390);
		pnlAutoFight.add(panel_2);
		
		JPanel pnlExplore = new JPanel();
		tabMain.addTab("智能探索", null, pnlExplore, null);
		pnlExplore.setLayout(null);
		
		JPanel pnlArea = new JPanel();
		pnlArea.setBorder(new TitledBorder(null, "\u5217\u8868-\u79D8\u5883", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlArea.setBounds(10, 70, 340, 330);
		pnlExplore.add(pnlArea);
		pnlArea.setLayout(null);
		
		JScrollPane srlArea = new JScrollPane();
		srlArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		srlArea.setBounds(10, 20, 320, 300);
		pnlArea.add(srlArea);
		
		tblExpArea = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) { return false; }};
		srlArea.setViewportView(tblExpArea);
		
		JPanel panel_9 = new JPanel();
		panel_9.setLayout(null);
		panel_9.setBorder(new TitledBorder(null, "\u667A\u80FD\u63A2\u7D22-\u63A7\u5236", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(58, 135, 173)));
		panel_9.setBounds(720, 10, 110, 390);
		pnlExplore.add(panel_9);
		
		btnExpRefresh = new JButton("刷新");
		btnExpRefresh.setActionCommand("actAutoFight");
		btnExpRefresh.setBounds(10, 20, 90, 30);
		panel_9.add(btnExpRefresh);
		
		btnExpControl = new JButton("开始");
		btnExpControl.setActionCommand("actAutoFight");
		btnExpControl.setBounds(10, 60, 90, 30);
		panel_9.add(btnExpControl);
		
		JCheckBox checkBox = new JCheckBox("仅当前区域");
		checkBox.setBounds(10, 100, 90, 20);
		panel_9.add(checkBox);
		
		JCheckBox checkBox_1 = new JCheckBox("仅当前秘境");
		checkBox_1.setBounds(10, 130, 90, 23);
		panel_9.add(checkBox_1);
		
		JCheckBox checkBox_2 = new JCheckBox("无限探索");
		checkBox_2.setBounds(10, 160, 90, 23);
		panel_9.add(checkBox_2);
		
		JCheckBox checkBox_3 = new JCheckBox("放妖模式");
		checkBox_3.setBounds(10, 190, 90, 23);
		panel_9.add(checkBox_3);
		
		JPanel panel_10 = new JPanel();
		panel_10.setLayout(null);
		panel_10.setBorder(new TitledBorder(null, "\u63A2\u7D22-\u8BB0\u5F55", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(58, 135, 173)));
		panel_10.setBounds(360, 10, 350, 390);
		pnlExplore.add(panel_10);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_4.setBounds(10, 20, 330, 360);
		panel_10.add(scrollPane_4);
		
		tblExpLog = new JTable();
		scrollPane_4.setViewportView(tblExpLog);
		
		JPanel panel_11 = new JPanel();
		panel_11.setLayout(null);
		panel_11.setBorder(new TitledBorder(null, "\u5F53\u524D\u79D8\u5883", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(58, 135, 173)));
		panel_11.setBounds(10, 10, 340, 50);
		pnlExplore.add(panel_11);
		
		valExpArea = new JLabel("{当前秘境}");
		valExpArea.setHorizontalAlignment(SwingConstants.CENTER);
		valExpArea.setBounds(10, 20, 150, 20);
		panel_11.add(valExpArea);
		
		cbxExpFloor = new JComboBox<String>();
		cbxExpFloor.setBounds(170, 20, 160, 20);
		panel_11.add(cbxExpFloor);
		
		JPanel pnlFryHitoryH = new JPanel();
		pnlFryHitoryH.setLayout(null);
		tabMain.addTab("妖精历史", null, pnlFryHitoryH, null);
		tabMain.setEnabledAt(3, false);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "\u653B\u51FB\u5386\u53F2", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 820, 390);
		pnlFryHitoryH.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(null, "\u5F53\u524D\u5996\u7CBE", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 20, 140, 110);
		panel.add(panel_1);
		
		JLabel label = new JLabel("狂暴的教练的的卢瑟布");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(10, 20, 120, 20);
		panel_1.add(label);
		
		JLabel label_1 = new JLabel("Lv.100");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(80, 50, 50, 20);
		panel_1.add(label_1);
		
		JLabel label_2 = new JLabel("发现者");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(10, 50, 50, 20);
		panel_1.add(label_2);
		
		JLabel label_3 = new JLabel("windysoul3000");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(10, 80, 120, 20);
		panel_1.add(label_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(160, 20, 650, 360);
		panel.add(scrollPane);
		
		tblFryHistory = new JTable();
		scrollPane.setViewportView(tblFryHistory);
		
		JButton button = new JButton("返回");
		button.setBounds(10, 345, 140, 30);
		panel.add(button);
		
		JPanel panel_12 = new JPanel();
		tabMain.addTab("卡牌选择", null, panel_12, null);
//		tabMain.setSelectedIndex(1);
	}
	
	public GMain7() throws Exception //初始化
	{
		ULog.log("Gui-Main-Init");
	//界面加载
		initialize();
	//配置预读取
		UConfig.getInstance();
		
		String[] user = UConfig.load("User");
		
		for(String u:user)
			iptAcout.addItem(u.split(",")[0]);
		
		iptAcout.setSelectedItem(UConfig.load("LastUser")[0]);
		
		for(String u:user)
				if(u.startsWith((String)iptAcout.getSelectedItem()))
				{
					iptPaswd.setText(u.split(",")[1]);
					
					switch(u.split(",")[2])
					{
					case "CN1": iptServer.setSelectedIndex(0); server = FServer.CN1; break;
					case "CN2": iptServer.setSelectedIndex(1); server = FServer.CN2; break;
					case "CN3": iptServer.setSelectedIndex(2); server = FServer.CN3; break;
					case "SG1": iptServer.setSelectedIndex(3); server = FServer.SG1; break;
					case "KR1": iptServer.setSelectedIndex(4); server = FServer.KR1; break;
					}
				}
	//选择用户时同步密码到密码框
		iptAcout.addItemListener((e)->
		{
			if(e.getStateChange() == ItemEvent.SELECTED)
				try
				{
					for(String u:UConfig.load("User"))
						if(u.startsWith((String)iptAcout.getSelectedItem()))
						{
							iptPaswd.setText(u.split(",")[1]);
							
							switch(u.split(",")[2])
							{
							case "CN1": iptServer.setSelectedIndex(0); server = FServer.CN1; break;
							case "CN2": iptServer.setSelectedIndex(1); server = FServer.CN2; break;
							case "CN3": iptServer.setSelectedIndex(2); server = FServer.CN3; break;
							case "SG1": iptServer.setSelectedIndex(3); server = FServer.SG1; break;
							case "KR1": iptServer.setSelectedIndex(4); server = FServer.KR1; break;
							}
							
						}
				} catch (Exception e1) { e1.printStackTrace(); }
		});
	//选择大区时同步server属性
		iptServer.addItemListener((e)->
		{
			if(e.getStateChange() == ItemEvent.SELECTED)
				switch((String)iptServer.getSelectedItem())
				{
				case "国服一区": server = FServer.CN1; break;
				case "国服二区": server = FServer.CN2; break;
				case "国服三区": server = FServer.CN3; break;
				case "海服一区": server = FServer.SG1; break;
				case "韩服一区": server = FServer.KR1; break;
				}
		});
	//设置托盘图标
		tray = new TrayIcon(new ImageIcon(FServer.dirGuiAll +"/ico.png").getImage(), "MATookit");
		tray.setImageAutoSize(true);
		
		SystemTray.getSystemTray().add(tray);
		tray.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if(e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1)// 左键双击图标
				{
					if(!frmMain.isDisplayable())
					{
						frmMain.setExtendedState(JFrame.NORMAL);
						frmMain.setVisible(true);
					}
					else
					{
						frmMain.setVisible(false);
						frmMain.dispose();
					}
				}
			}
		});
	//操作
		
	//登录
		btnLogin.addActionListener((e)->
		{
			if(!server.dirPak().exists()) server.dirPak().mkdirs();
			
			new Thread(()->
			{
				if(btnLogin.getText().equals("登录"))
				{
					btnLogin.setEnabled(false);
					btnLogin.setText("登录中");

					iptAcout.setEnabled(false);
					iptPaswd.setEnabled(false);
					iptServer.setEnabled(false);
					try
					{
						action = new FAction(false, null, server);
						
						action.Login((String) iptAcout.getSelectedItem(), new String(iptPaswd.getPassword()));

						UConfig.save("LastUser", (String)iptAcout.getSelectedItem());
						
						ifoShow();
					}
					catch(Exception e1) { e1.printStackTrace(); }
					
					if(action.arthur() != null)
					{
						frmMain.setTitle(frmMain.getTitle()+"["+server.toString()+"]["+(String)iptAcout.getSelectedItem()+"]["+action.arthur().base().name()+"]");
						tray.setToolTip("["+(String)iptAcout.getSelectedItem()+"]["+action.arthur().base().name()+"]");
						
						btnLogin.setText("登出");
						btnLogin.setEnabled(true);
						btnFryControl.setEnabled(true);
					}
					else
					{
						btnLogin.setEnabled(true);
						btnLogin.setText("登录");
						iptAcout.setEnabled(true);
						iptPaswd.setEnabled(true);
						iptServer.setEnabled(true);
						
						action = null;
					}
				}
				else if(btnLogin.getText().equals("登出"))
				{
					action = null;
					MPoint.reset();
					
					btnLogin.setEnabled(true);
					btnLogin.setText("登录");
					iptAcout.setEnabled(true);
					iptPaswd.setEnabled(true);
					iptServer.setEnabled(true);
					
					frmMain.setTitle(title);
					tray.setToolTip("[DanoR MATookit]");
				}
			}).start();
		});
	//嗑药
		btnAPRev.addActionListener((e)->
		{
			try { action.ItemUse("1"); }
			catch(Exception e1) { e1.printStackTrace(); }
		});
		btnBCRev.addActionListener((e)->
		{
			try { action.ItemUse("2"); }
			catch(Exception e1) { e1.printStackTrace(); }
		});	
		btnAPRevHalf.addActionListener((e)->
		{
			try { action.ItemUse("111"); }
			catch(Exception e1) { e1.printStackTrace(); }
		});
		btnBCRevHalf.addActionListener((e)->
		{
			try { action.ItemUse("112"); }
			catch(Exception e1) { e1.printStackTrace(); }
		});
	//复制角色名
		btnIfoCopyName.addActionListener((e)->
		{
			Transferable tText = new StringSelection(valIfoName.getText());
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(tText, null);  
		});
	//智能舔怪
	//表头
		setCol(tblFryList, new String[] {"已舔","名称","等级","发现者","血量","剩余收集","剩余时间","ID"}, new int[] {35,100,35,120,140,60,60,0});
		hidCol(tblFryList, tblFryList.getColumnCount()-1);
		
		setCol(tblFryHistory, new String[] {"名称","攻击次数","血量"}, new int[] {120,60,60});
	//开始舔怪
		btnFryControl.addActionListener((e)->
		{
			if(btnFryControl.getText().equals("开始"))
			{	
				try
				{
					if(!iptFryInterval.getText().equals(""))
					{
						timerPero = new Timer();
						timerPero.schedule(new FFryAuto(action, tblFryList, tray), 1000, Integer.parseInt(iptFryInterval.getText())*1000);
						btnFryControl.setText("暂停");
					}
				}
				catch(Exception e1) { e1.printStackTrace(); }
			}
			else
			{
				timerPero.cancel();
				btnFryControl.setText("开始");
			}
		});
	//智能跑图
	//表头
		setCol(tblExpArea, new String[] {"ID","名称","探索进度","发现进度"}, new int[] {35,135,60,60});
		
		setCol(tblExpLog, new String[] {"区域","进度","经验/剩余经验","金钱","事件","内容"}, new int[] {35,35,90,35,60,45});
		
		btnExpControl.setEnabled(false);
	//获取秘境
		btnExpRefresh.addActionListener((e)->
		{
			try
			{
				((DefaultTableModel)tblExpArea.getModel()).setRowCount(0);
				valExpArea.setText("无");
				btnExpControl.setEnabled(false);
				
				action.AreaList();
				
				if(!action.arthur().areas().isEmpty())
					for(NArea a:action.arthur().areas())
						((DefaultTableModel)tblExpArea.getModel()).addRow(new String[] {a.idArea(), a.name(), a.prgArea()+"%", a.prgItem()+"%"});
			}
			catch(Exception e1) { e1.printStackTrace(); }
		});
	//获取区域
		tblExpArea.addMouseListener(new MouseAdapter()
		{
		    public void mouseClicked(MouseEvent e)
		    {
		       if(e.getClickCount() == 2)
		       {
		    	   valExpArea.setText((String)tblExpArea.getValueAt(tblExpArea.getSelectedRow(),1));
		    	   
					for(NArea a:action.arthur().areas())
					{
						if(a.name().equals(valExpArea.getText()))
						{
							try
							{
								action.FloorList(a);
								
								cbxExpFloor.removeAllItems();
								cbxExpFloor.addItem("");
								
								for(NFloor f:UUtil.reverse(a.floors()))
									if(f.isNormal())
										cbxExpFloor.addItem("区域 "+f.idFloor()+" ("+f.prog()+"%) -"+f.cost());
									else
										cbxExpFloor.addItem("区域 "+f.idFloor()+" Boss "+f.idCards()[0]);
							}
							catch(Exception e1) { e1.printStackTrace(); }
							break;
						}
					}
		       }
		    }
		});
	//切换区域
		cbxExpFloor.addItemListener((e)->
		{
			if(e.getStateChange() == ItemEvent.SELECTED)
					if(cbxExpFloor.getSelectedIndex() > 0 || timerExp != null)
						btnExpControl.setEnabled(true);
					else
						btnExpControl.setEnabled(false);
		});
	//探索控制
		btnExpControl.addActionListener((e)->
		{
			if(btnExpControl.getText().equals("开始"))
			{
				new Thread(()->
				{
					try
					{
						timerExp = new Timer();
						timerExp.schedule(new FExpAuto(action, valExpArea.getText(), tblExpLog, tray, btnExpControl, cbxExpFloor), 1000, 3000);
					}
					catch(Exception e1) { e1.printStackTrace(); }
	
				}).start();
				
				btnExpControl.setText("暂停");
			}
			else
			{
				cbxExpFloor.setEnabled(true);
				btnExpControl.setText("开始");
				timerExp.cancel();
			}
		});
	}

	public void ifoShow()
	{
		if(action.arthur() != null)
		{
			valIfoName.setText(action.arthur().base().name());
			valIfoLevel.setText(action.arthur().town().lv());
			valIfoCountry.setText(new String[]{"剑术之城","技巧之场","魔法之派"}[Integer.parseInt(action.arthur().town().idTown())-1]);
			valIfoIDUser.setText(action.arthur().base().idArthur());
			valIfoGoldCoin.setText(action.arthur().base().gold());
			valIfoMillionCost.setText(action.arthur().shop().mc());
			valIfoFriendPoint.setText(action.arthur().gacha().pointFriend());
			valIfoGachaTicket.setText(action.arthur().gacha().ticket());
			valIfoGachaPoint.setText(action.arthur().gacha().point());
			valIfoFriendNow.setText(action.arthur().friends().now());
			valIfoFriendMax.setText(action.arthur().friends().max());
			valIfoFriendIvt.setText(action.arthur().friends().ivt());
			
			valExgauge.setText(action.arthur().battle().sp()+"%");
			prgExgauge.setBounds(70, 20, (int)(100*(Double.valueOf(action.arthur().battle().sp())/100.0)), 20);
			
			if(action.arthur().town().hasFairy())
			{
				btnHaveFairy.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
				btnHaveFairy.setText("妖精出现中");
				btnHaveFairy.setForeground(Color.WHITE);
			}
			else
			{
				btnHaveFairy.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.normal));
				btnHaveFairy.setText("无妖精出现");
				btnHaveFairy.setForeground(Color.BLACK);
			}
			
			if(action.arthur().town().hasReward())
			{
				btnHaveReward.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
				btnHaveReward.setText("未领取奖励");
				btnHaveReward.setForeground(Color.WHITE);
			}
			else
			{
				btnHaveReward.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.normal));
				btnHaveReward.setText("奖励盒为空");
				btnHaveReward.setForeground(Color.BLACK);
			}
			
			for(NItem i:action.arthur().items())
			{
				switch(i.idItem())
				{
				case "1":
					valItmAP.setText("剩余"+i.now()+"瓶"); break;
				case "2":
					valItmBC.setText("剩余"+i.now()+"瓶"); break;
				case "111":
					valItmAPHalf.setText("剩余"+i.now()+"瓶"); break;
				case "112":
					valItmBCHalf.setText("剩余"+i.now()+"瓶"); break;
				}
			}
			
			MPoint.getInstance(action.arthur().point(), valAP, valBC, prgAP, prgBC);
			
			MPoint.update();
		}
	}
	
	public void setCol(JTable table, String[] colName, int[] colWidth)
	{
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		
		DefaultTableModel tblModel = (DefaultTableModel)table.getModel();
		
		for(String s:colName)
			tblModel.addColumn(s);

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		for(int i=0;i<table.getColumnCount();i++)
		{
			table.getColumnModel().getColumn(i).setPreferredWidth(colWidth[i]);
			table.getColumnModel().getColumn(i).setCellRenderer(render);
		}
	}
	public void hidCol(JTable table, int index)
	{
		table.getColumnModel().getColumn(index).setMinWidth(0);
		table.getColumnModel().getColumn(index).setMaxWidth(0);
		table.getColumnModel().getColumn(index).setWidth(0);
		table.getColumnModel().getColumn(index).setPreferredWidth(0);
	}
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run() { try { new GMain7().frmMain.setVisible(true); } catch(Exception e) { e.printStackTrace(); } }
		});
		
//		EventQueue.invokeLater(() -> {try { new PcGui7().frmMain.setVisible(true); } catch(Exception e) { e.printStackTrace(); }});
	}
}
