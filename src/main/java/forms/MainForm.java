package forms;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import entity.CompanyDto;
import entity.LevelNode;
import entity.SystemEnv;
import entity.UserDto;
import entity.UserNode;
import libs.Core;
import netscape.javascript.JSObject;
import threads.SaveThread;

import java.awt.CardLayout;
import java.awt.TrayIcon;

import javax.swing.border.EtchedBorder;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;

import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

import javax.swing.AbstractButton;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import java.awt.Font;
import javax.swing.JCheckBox;

public class MainForm {

	private static final String VERSION = "26.04.18v04";
	private static final String NAME_FORM = "Адресная книга";
	private static final Dimension DEMENSION_TREE = new Dimension(380, 50);
	private static final Dimension DEMENSION_IMAGE = new Dimension(250, 250);
	private static final Dimension DEMENSION_MENU = new Dimension(20, 30);
	private static final Dimension DEMENSION_ICON_MENU = new Dimension(20, 20);
	private static final LineBorder BORDER_ICON_MENU = new LineBorder(new Color(214, 217, 223));
	private static final boolean IS_LOADER_CLASS_PATH = false;
	private static final String IS_CONTACT_RU = "Контакт в кириллице";
	private static final String IS_CONTACT_EN = "Контакт в транслите";
	private static final String FOLDER_IMAGE = "images";
	private static final String EMPTY_IMAGE = "/empty.png";
	private static final String USERS_IMAGE = "/iphone/Users.png";
	private static final String DOWNLOADS_IMAGE = "/iphone/Transmission.png";
	private static final String COPY_EMAILS_IMAGE = "/iphone/Mail.png";
	private static final String SAVE_XLS_IMAGE = "/iphone/Excel.png";
	private static final String LOGO_IMAGE = "/logo.png";
	private static final String PRELOAD_IMAGE = "/ajax-loader.gif";
	private static final String PLANS_IMAGE = "/plans/";
	private static final String COPY_IMAGE = "/iphone/appadvice.png";
	private static final String PLUS_IMAGE = "/iphone/plus.png";

	private JFrame frmHandbook;

	private JLabel labelPersonWriDescription;
	private JLabel labelPersonWriFio;
	private JLabel labelPersonWriCompany;
	private JLabel labelPersonWriDepartment;
	private JLabel labelPersonWriHead;
	private JLabel labelPersonPic;
	private JLabel labelPersonQrCode;
	private JLabel labelPersonWriRoom;
	private JLabel labelPersonWriPhoneInside;
	private JLabel labelPersonWriMail;
	private JLabel labelPersonWriPhoneSmall;

	private JLabel labelContactWriСountry;
	private JLabel labelContactWriRegion;
	private JLabel labelContactWriTown;
	private JLabel labelContactWritPostIndex;
	private JLabel labelContactWriStreet;
	private JLabel labelContactWriBirth;
	private JLabel labelContactWriRoom;
	private JLabel labelContactWriPhone;
	private JLabel labelContactWriPhoneInside;
	private JLabel labelContactWriMobilePhone;
	private JLabel labelContactWriMail;

	private JLabel labelRoomPic;

	private JPanel panelFSM;
	private JTextField textFieldLastName;
	private JTextField textFieldFirstName;
	private JTextField textFieldMiddleName;
	private JComboBox<CompanyDto> comboBoxCompany;
	private JComboBox<CompanyDto> comboBoxFilial;
	private JTextField textFieldPhone;
	private JTextField textFieldDepartment;
	private JTextField textFieldPesonPosition;
	private JTextField textFieldRoom;
	private boolean isTransQrcode = false;

	private JLabel labelStatusBar;
	private JTree tree;
	private JScrollPane treeView;

	private JPanel panelMessages;
	private JPanel panelMessagesEditor;
	private JPanel panelAuth;
	private JTextField textFieldLogin;
	private JPasswordField passwordField;
	private JButton buttonAuth;

	DefaultMutableTreeNode top;
	private JPanel panelTree;

	private JLabel labelPersonHead;

	private mxGraphComponent graphComponent;
	private JLabel labelUpdate;
	private JLabel labelCopyMails;
	private JLabel labelSaveXls;
	private JLabel labelCollapsTree;

	private JList listMessagesEditorTo;
	private JTextField textFieldMessagesEditorSubject;
	private JLabel labelMessagesEditorWriFrom;
	private JButton buttonMessagesEditorSend;
	private JTextArea textAreaMessagesEditor;
	private JButton buttonMessagesEditorAttachment;
	private JButton buttonMessagesEditorClear;
	private JList listMessagesEditorAttachment;
	private JButton buttonMessagesEditorAttachmentClear;

	private TrayIcon trayIcon = null;

	private static Core core;

	private boolean isLockRenderTree = false;
	private boolean isLockRenderComboBox = false;

	private SpringLayout sl_panelPerson;
	private JPanel panelQrCode;
	private JLabel labelPersonFio;
	private JLabel labelCopyPersonFio;
	private JLabel labelCopyPersonMail;
	private JPanel panelPerson;
	private AbstractButton checkBoxSignature;
	private JCheckBox checkBoxNotifyDelivery;
	private AbstractButton checkBoxNotifyRead;
	private JLabel labelCopyPersonPhoneInside;
	private JLabel labelCopyPersonDescription;
	private JLabel labelCopyPersonCompany;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		core = new Core();

		if (core.getSystemEnv().isSkin("nimbus")) {
			try {
				// Set System L&F
				UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
					| IllegalAccessException e) {
				// handle exception
				e.printStackTrace();
			}
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frmHandbook.setVisible(true);
					if (!core.getSystemEnv().isWeb()) {
						window.frmHandbook.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					} else {
						window.frmHandbook
								.setExtendedState(window.frmHandbook.getExtendedState() | JFrame.MAXIMIZED_BOTH);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainForm() {
		if (!core.getSystemEnv().isWeb()) {
			if (!core.sendServerSocket()) {
				core.runServerSocket();
				createForm();
			} else {
				if (core.getSystemEnv().isForce()) {
					createForm();
				} else {
					System.exit(0);
				}
			}
		} else {
			createForm();
		}
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void createForm() {
		componentsInitialize();
		addWindowListener();
		core.setMainForm(this);
		core.loadData();
		if (core.getSystemEnv().isUpdate()) {
			addTimer(core.getSystemEnv().getTimeUpdate());
		}
	}

	private void addTimer(int dalay) {
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				core.callByTimer();
			}
		};

		Timer timer = new Timer(dalay, listener);
		timer.start();
	}

	private void addWindowListener() {
		if (!core.getSystemEnv().isWeb()) {
			frmHandbook.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					Object[] options = { "Свернуть", "Закрыть" };
					int n = JOptionPane.showOptionDialog(frmHandbook, "", "Выберите действие",
							JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

					if (n == JOptionPane.YES_OPTION) {
						createTray();
					} else {
						System.exit(0);
					}
				}
			});
		}
	}

	public DefaultMutableTreeNode getTopTree() {
		return this.top;
	}

	public void setStatusBar(String status) {
		labelStatusBar.setText(status);
	}

	public void setCompanySelector() {
		for (CompanyDto company : core.getCompanys().all()) {
			comboBoxCompany.addItem(company);
		}
	}

	public void setFilialSelector(CompanyDto company) {
		for (CompanyDto filial : company.getFilials()) {
			comboBoxFilial.addItem(filial);
		}
	}

	public void setTreeNode(ArrayList<CompanyDto> companys, boolean isInit) {
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		root.removeAllChildren();
		model.reload();
		UserNode userNode = new UserNode();
		for (CompanyDto company : companys) {
			DefaultMutableTreeNode nodes = createCompanyTreeNode(company, userNode);
			if (null != nodes) {
				addToTopCompanyTreeNode(nodes);
			}
		}
		if (userNode.isOnly()) {
			userNode.findUser(tree);
			setSelectionUser(userNode.getPath());
			core.setUser(userNode.getUser());
			updatePanelView();
			setMessagesEditorTo();
		}
		userNode = null;

		expandTree(isInit);
	}

	public void setSelectionUser(TreePath eTree) {
		this.isLockRenderTree = true;
		TreeSelectionModel model = tree.getSelectionModel();
		model.addSelectionPath(eTree);
		this.isLockRenderTree = false;
	}

	private void expandTree(boolean fullExpand) {
		if (fullExpand) {
			Enumeration<?> topLevelNodes = ((TreeNode) tree.getModel().getRoot()).children();
			while (topLevelNodes.hasMoreElements()) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) topLevelNodes.nextElement();
				CompanyDto company = (CompanyDto) node.getUserObject();
				if (company.getUsers().isEmpty()) {
					tree.expandPath(new TreePath(node.getPath()));
				}
			}
		} else {
			for (int i = 0; i < tree.getRowCount(); i++) {
				tree.expandRow(i);
			}
		}
		tree.repaint();
	}

	private void collapseTree() {
		Enumeration<?> topLevelNodes = ((TreeNode) tree.getModel().getRoot()).children();
		while (topLevelNodes.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) topLevelNodes.nextElement();
			CompanyDto company = (CompanyDto) node.getUserObject();
			if (company.getUsers().isEmpty()) {
				tree.collapsePath(new TreePath(node.getPath()));
			}
		}
		tree.repaint();
	}

	private void collapseAll() {
		TreeNode root = (TreeNode) tree.getModel().getRoot();
		collapseAll(tree, new TreePath(root));
		expandTree(true);
	}

	private void collapseAll(JTree tree, TreePath parent) {
		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (Enumeration e = node.children(); e.hasMoreElements();) {
				TreeNode n = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				collapseAll(tree, path);
			}
		}
		tree.collapsePath(parent);
	}

	private UserDto addToCompanyUserTreeNode(DefaultMutableTreeNode top, CompanyDto company) {
		UserDto findUser = null;
		for (UserDto user : company.getUsersSortedByCn().values()) {
			if (null == findUser) {
				findUser = user;
			}
			top.add(new DefaultMutableTreeNode(user));
		}
		return findUser;
	}

	private DefaultMutableTreeNode createCompanyTreeNode(CompanyDto company, UserNode userNode) {
		DefaultMutableTreeNode companyTreeNode = null;
		if (!company.getUsers().isEmpty()) {
			companyTreeNode = new DefaultMutableTreeNode(company);
			UserDto user = addToCompanyUserTreeNode(companyTreeNode, company);
			if (null == userNode.getUser()) {
				userNode.setUser(user);
			}
			userNode.incCountUser(company.getUsers().size());
		} else if (!company.getFilials().isEmpty()) {
			companyTreeNode = new DefaultMutableTreeNode(company);
			for (CompanyDto filial : company.getFilials()) {
				DefaultMutableTreeNode filialTreeNode = createCompanyTreeNode(filial, userNode);
				if (null != filialTreeNode) {
					companyTreeNode.add(filialTreeNode);
				}
			}
		}

		return companyTreeNode;
	}

	private void addToTopCompanyTreeNode(DefaultMutableTreeNode companyTreeNode) {
		top.add(companyTreeNode);
	}

	private void showGraph(HashMap<Integer, ArrayList<LevelNode>> levels) {
		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();
		graph.getModel().beginUpdate();

		try {
			for (Entry<Integer, ArrayList<LevelNode>> entity : levels.entrySet()) {
				ArrayList<LevelNode> nodes = entity.getValue();
				for (LevelNode node : nodes) {
					node.setGraph(graph).setVertex(parent).getVertex();
					if (!node.isRoot()) {
						node.setVertexLink();
					}
				}
				nodes = null;
			}
		} finally {
			graph.getModel().endUpdate();
		}

		graphComponent.setGraph(graph);
		graphComponent.getGraphControl().repaint();
		graph = null;
		parent = null;
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @wbp.parser.entryPoint
	 */
	private void componentsInitialize() {
		frmHandbook = new JFrame();
		if (core.getSystemEnv().isWeb()) {
			frmHandbook.setUndecorated(true);
		}
		ImageIcon icon = getResourceImage(LOGO_IMAGE);
		frmHandbook.setIconImage(icon.getImage());
		frmHandbook.setTitle(NAME_FORM);
		frmHandbook.setBounds(100, 100, 952, 940);
		frmHandbook.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHandbook.getContentPane().setLayout(new BorderLayout(0, 0));

		JSplitPane splitPaneHorz = new JSplitPane();
		splitPaneHorz.setOrientation(JSplitPane.VERTICAL_SPLIT);
		frmHandbook.getContentPane().add(splitPaneHorz);

		JSplitPane splitPaneVert = new JSplitPane();
		splitPaneVert.setMinimumSize(DEMENSION_TREE);
		splitPaneVert.setPreferredSize(DEMENSION_TREE);
		splitPaneHorz.setRightComponent(splitPaneVert);

		JPanel panelSearch = new JPanel();
		panelSearch.setLayout(new BorderLayout(0, 0));
		splitPaneHorz.setLeftComponent(panelSearch);
		createPanelFSM(panelSearch);

		panelTree = new JPanel();
		panelTree.setPreferredSize(DEMENSION_TREE);
		panelTree.setMinimumSize(DEMENSION_TREE);
		panelTree.setLayout(new CardLayout(0, 0));
		splitPaneVert.setLeftComponent(panelTree);
		createTreePanel();

		JPanel panelView = new JPanel();
		splitPaneVert.setRightComponent(panelView);
		panelView.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panelView.add(tabbedPane);

		panelPerson = new JPanel();
		panelPerson.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelPerson.setMinimumSize(new Dimension(10, 250));
		tabbedPane.addTab("Персональная информация", panelPerson);
		createTabPerson(panelPerson);

		JPanel panelContact = new JPanel();
		panelContact.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelContact.setMinimumSize(new Dimension(10, 250));
		tabbedPane.addTab("Контакты", panelContact);
		createTabContact(panelContact);

		JPanel panelRoom = new JPanel();
		panelRoom.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		tabbedPane.addTab("Схема", panelRoom);
		createTabRoom(panelRoom);

		JPanel panelDepend = new JPanel();
		panelDepend.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelDepend.setMinimumSize(new Dimension(10, 250));
		tabbedPane.addTab("Подчинённые", panelDepend);
		panelDepend.setLayout(new CardLayout(0, 0));
		graphComponent = new mxGraphComponent(new mxGraph());
		graphComponent.setToolTips(true);
		panelDepend.add(graphComponent, "panelDepend");

		panelMessages = new JPanel();
		panelMessages.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelMessages.setMinimumSize(new Dimension(10, 250));
		tabbedPane.addTab("Создать рассылку", panelMessages);
		createTabMessages();

		createPanelStatus();

		addListners();
	}

	public void removeTray() {
		final SystemTray systemTray = SystemTray.getSystemTray();
		systemTray.remove(trayIcon);
		frmHandbook.setVisible(true);
	}

	private void createTray() {
		// checking for support
		if (!SystemTray.isSupported()) {
			System.out.println("System tray is not supported !!! ");
			return;
		}

		PopupMenu trayPopupMenu = new PopupMenu();

		final SystemTray systemTray = SystemTray.getSystemTray();

		ImageIcon icon = getResourceImage(LOGO_IMAGE);
		Image image = icon.getImage();

		trayIcon = new TrayIcon(image, "Контакты", trayPopupMenu);
		// кликаем по менюшке Развернуть
		MenuItem action = new MenuItem("Развернуть");
		action.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeTray();
			}
		});
		trayPopupMenu.add(action);

		// кликаем по менюшке Выход
		MenuItem close = new MenuItem("Выход");
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// core.removeRunningProcess();
				core.close();
				System.exit(0);
			}
		});
		trayPopupMenu.add(close);
		// клик по трайиконке
		trayIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					frmHandbook.setVisible(true);
					systemTray.remove(trayIcon);
				}
			}
		});

		// adjust to default size as per system recommendation
		trayIcon.setImageAutoSize(true);

		try {
			systemTray.add(trayIcon);
		} catch (AWTException awtException) {
			awtException.printStackTrace();
		}
	}

	private void createTreePanel() {
		addTreePreloader();

		top = new DefaultMutableTreeNode("Сотрудники");
		tree = new JTree(top);
		treeView = new JScrollPane(tree);
		panelTree.add(treeView, "treeView");

		JPanel tools = new JPanel();
		tools.setPreferredSize(DEMENSION_MENU);
		tools.setMinimumSize(DEMENSION_MENU);
		tools.setMaximumSize(DEMENSION_MENU);
		treeView.setColumnHeaderView(tools);
		SpringLayout sl_tools = new SpringLayout();
		tools.setLayout(sl_tools);

		labelUpdate = new JLabel("");
		sl_tools.putConstraint(SpringLayout.NORTH, labelUpdate, 2, SpringLayout.NORTH, tools);
		sl_tools.putConstraint(SpringLayout.WEST, labelUpdate, 5, SpringLayout.WEST, tools);
		labelUpdate.setMaximumSize(DEMENSION_ICON_MENU);
		labelUpdate.setMinimumSize(DEMENSION_ICON_MENU);
		labelUpdate.setSize(DEMENSION_ICON_MENU);
		labelUpdate.setToolTipText("Обновить");
		labelUpdate.setIcon(resizeIcon(this.getResourceImage(DOWNLOADS_IMAGE), labelUpdate));
		tools.add(labelUpdate);

		labelCopyMails = new JLabel("");
		sl_tools.putConstraint(SpringLayout.NORTH, labelCopyMails, 2, SpringLayout.NORTH, tools);
		sl_tools.putConstraint(SpringLayout.WEST, labelCopyMails, 30, SpringLayout.WEST, tools);
		labelCopyMails.setMaximumSize(DEMENSION_ICON_MENU);
		labelCopyMails.setMinimumSize(DEMENSION_ICON_MENU);
		labelCopyMails.setSize(DEMENSION_ICON_MENU);
		labelCopyMails.setToolTipText("Копировать почтовые адреса");
		labelCopyMails.setIcon(resizeIcon(this.getResourceImage(COPY_EMAILS_IMAGE), labelCopyMails));
		tools.add(labelCopyMails);

		labelSaveXls = new JLabel("");
		sl_tools.putConstraint(SpringLayout.NORTH, labelSaveXls, 2, SpringLayout.NORTH, tools);
		sl_tools.putConstraint(SpringLayout.WEST, labelSaveXls, 55, SpringLayout.WEST, tools);
		labelSaveXls.setMaximumSize(DEMENSION_ICON_MENU);
		labelSaveXls.setMinimumSize(DEMENSION_ICON_MENU);
		labelSaveXls.setSize(DEMENSION_ICON_MENU);
		labelSaveXls.setToolTipText("Сохранить в XLS");
		labelSaveXls.setIcon(resizeIcon(this.getResourceImage(SAVE_XLS_IMAGE), labelSaveXls));
		tools.add(labelSaveXls);

		labelCollapsTree = new JLabel("");
		sl_tools.putConstraint(SpringLayout.NORTH, labelCollapsTree, 2, SpringLayout.NORTH, tools);
		sl_tools.putConstraint(SpringLayout.WEST, labelCollapsTree, 80, SpringLayout.WEST, tools);
		labelCollapsTree.setMaximumSize(DEMENSION_ICON_MENU);
		labelCollapsTree.setMinimumSize(DEMENSION_ICON_MENU);
		labelCollapsTree.setSize(DEMENSION_ICON_MENU);
		labelCollapsTree.setToolTipText("Свернуть список компаний");
		labelCollapsTree.setIcon(resizeIcon(this.getResourceImage(PLUS_IMAGE), labelCollapsTree));
		tools.add(labelCollapsTree);
	}

	private void lockPanelFSM(boolean lock) {
		for (Component entity : panelFSM.getComponents()) {
			if (lock) {
				entity.disable();
			} else {
				if (null != entity.getName() && entity.getName().equals("comboBoxFilial")) {
					CompanyDto company = (CompanyDto) comboBoxCompany.getSelectedItem();
					if (!company.isAllSelected()) {
						entity.enable();
					} else {
						entity.disable();
					}
				} else {
					entity.enable();
				}
			}
		}
		panelFSM.repaint();
	}

	private void lockPanelFSM() {
		comboBoxCompany.setSelectedIndex(0);
		comboBoxFilial.setSelectedIndex(0);
		lockPanelFSM(true);
	}

	private void companyBoxLogic() {
		isLockRenderComboBox = true;
		CompanyDto company = (CompanyDto) comboBoxCompany.getSelectedItem();
		if (!company.isAllSelected() & (company.getFilials().size() > 0)) {
			comboBoxFilial.enable();
			comboBoxFilial.removeAllItems();
			comboBoxFilial.addItem(new CompanyDto());
			setFilialSelector(company);
		} else {
			comboBoxFilial.disable();
			comboBoxFilial.removeAllItems();
			comboBoxFilial.addItem(new CompanyDto());
		}
		isLockRenderComboBox = false;
	}

	private void unLockPanelFSM() {
		lockPanelFSM(false);
	}

	private void createPanelPreloader(JPanel panel, String nameComponent) {
		JLabel labelPreload = new JLabel();
		labelPreload.setHorizontalAlignment(SwingConstants.CENTER);
		labelPreload.setIcon(getResourceImage(PRELOAD_IMAGE));
		labelPreload.setName(nameComponent);
		panel.add(labelPreload, 0);
	}

	private void removePreload(JPanel panel, String name) {
		for (Component component : panel.getComponents()) {
			if (null != component.getName()) {
				if (name.contains(component.getName())) {
					panel.remove(component);
				}
			}
		}
	}

	private void createPanelPreloader(JPanel panel) {
		createPanelPreloader(panel, "preloader");
	}

	private void removePreload(JPanel panel) {
		removePreload(panel, "preloader");
	}

	private void addMessagePreloader() {
		if (null != panelAuth) {
			panelAuth.hide();
		}
		if (null != panelMessagesEditor) {
			panelMessagesEditor.hide();
		}
		createPanelPreloader(panelMessages);

	}

	public void authorizeMessage() {
		addMessagePreloader();
		core.authorizeOnMail(textFieldLogin.getText().toLowerCase(), passwordField.getText());
	}

	public void removeMessagePreload() {
		removePreload(panelMessages);
	}

	public void showMessageEditorPanel(String authUser) {
		this.labelMessagesEditorWriFrom.setText(authUser);
		panelAuth.hide();
		panelMessagesEditor.show();
		panelMessagesEditor.repaint();
	}

	public void addTreePreloader() {
		if (null != treeView) {
			treeView.hide();
		}
		lockPanelFSM();
		createPanelPreloader(panelTree);
	}

	public void removeTreePreload() {
		removePreload(panelTree);
		unLockPanelFSM();
	}

	public void clearMessages() {
		this.textFieldMessagesEditorSubject.setText("");
		this.clearMessagesTo();
		this.textAreaMessagesEditor.setText("");
		this.checkBoxSignature.setSelected(true);
		clearMessagesAttachment();
	}

	public void clearMessagesByNotify() {
		Object[] options = { "Очистить", "Редактировать" };
		int n = JOptionPane.showOptionDialog(frmHandbook, "<html>Рассылка осуществлена</html>", "",
				JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
		if (n == JOptionPane.YES_OPTION) {
			clearMessages();
		}
	}

	public void repaint() {
		frmHandbook.repaint();
	}

	private void clearMessagesAttachment() {
		DefaultListModel listModel = new DefaultListModel();
		listModel.clear();
		this.listMessagesEditorAttachment.setModel(listModel);
		core.clearMailAttachmet();
	}

	private void clearMessagesTo() {
		DefaultListModel listModel = new DefaultListModel();
		listModel.clear();
		this.listMessagesEditorTo.setModel(listModel);
	}

	private void createTabRoom(JPanel panel) {
		labelRoomPic = new JLabel();
		GroupLayout gl_panelRoom = new GroupLayout(panel);
		gl_panelRoom.setHorizontalGroup(gl_panelRoom.createParallelGroup(Alignment.LEADING).addComponent(labelRoomPic,
				GroupLayout.PREFERRED_SIZE, 546, GroupLayout.PREFERRED_SIZE));
		gl_panelRoom.setVerticalGroup(gl_panelRoom.createParallelGroup(Alignment.LEADING).addComponent(labelRoomPic,
				GroupLayout.PREFERRED_SIZE, 631, GroupLayout.PREFERRED_SIZE));
		panel.setLayout(gl_panelRoom);
	}

	private void createTabContact(JPanel panel) {
		SpringLayout sl_panelContact = new SpringLayout();
		panel.setLayout(sl_panelContact);

		JLabel labelContactСountry = new JLabel("Страна:");
		sl_panelContact.putConstraint(SpringLayout.NORTH, labelContactСountry, 10, SpringLayout.NORTH, panel);
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactСountry, 10, SpringLayout.WEST, panel);
		panel.add(labelContactСountry);

		JLabel labelContactRegion = new JLabel("Область, край:");
		sl_panelContact.putConstraint(SpringLayout.NORTH, labelContactRegion, 6, SpringLayout.SOUTH,
				labelContactСountry);
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactRegion, 0, SpringLayout.WEST, labelContactСountry);
		panel.add(labelContactRegion);

		JLabel labelContactTown = new JLabel("Город:");
		sl_panelContact.putConstraint(SpringLayout.NORTH, labelContactTown, 6, SpringLayout.SOUTH, labelContactRegion);
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactTown, 0, SpringLayout.WEST, labelContactСountry);
		panel.add(labelContactTown);

		JLabel labelContactPostIndex = new JLabel("Почтовый индекс:");
		sl_panelContact.putConstraint(SpringLayout.NORTH, labelContactPostIndex, 6, SpringLayout.SOUTH,
				labelContactTown);
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactPostIndex, 0, SpringLayout.WEST,
				labelContactСountry);
		panel.add(labelContactPostIndex);

		JLabel labelContactStreet = new JLabel("Улица:");
		sl_panelContact.putConstraint(SpringLayout.NORTH, labelContactStreet, 6, SpringLayout.SOUTH,
				labelContactPostIndex);
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactStreet, 0, SpringLayout.WEST, labelContactСountry);
		panel.add(labelContactStreet);

		JLabel labelContactBirth = new JLabel("Дата рождения:");
		sl_panelContact.putConstraint(SpringLayout.NORTH, labelContactBirth, 6, SpringLayout.SOUTH, labelContactStreet);
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactBirth, 0, SpringLayout.WEST, labelContactСountry);
		panel.add(labelContactBirth);

		JLabel labelContactRoom = new JLabel("Комната:");
		sl_panelContact.putConstraint(SpringLayout.NORTH, labelContactRoom, 6, SpringLayout.SOUTH, labelContactBirth);
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactRoom, 0, SpringLayout.WEST, labelContactСountry);
		panel.add(labelContactRoom);

		JLabel labelContactPhoneInside = new JLabel("Телефон:");
		sl_panelContact.putConstraint(SpringLayout.NORTH, labelContactPhoneInside, 6, SpringLayout.SOUTH,
				labelContactRoom);
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactPhoneInside, 0, SpringLayout.WEST,
				labelContactСountry);
		panel.add(labelContactPhoneInside);

		JLabel labelContactPhone = new JLabel("Телефон (внутр.):");
		sl_panelContact.putConstraint(SpringLayout.NORTH, labelContactPhone, 6, SpringLayout.SOUTH,
				labelContactPhoneInside);
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactPhone, 0, SpringLayout.WEST, labelContactСountry);
		panel.add(labelContactPhone);

		JLabel labelContactMobilePhone = new JLabel("Телефон мобильный:");
		sl_panelContact.putConstraint(SpringLayout.NORTH, labelContactMobilePhone, 6, SpringLayout.SOUTH,
				labelContactPhone);
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactMobilePhone, 0, SpringLayout.WEST,
				labelContactСountry);
		panel.add(labelContactMobilePhone);

		JLabel labelContactMail = new JLabel("Электронная почта:");
		sl_panelContact.putConstraint(SpringLayout.NORTH, labelContactMail, 6, SpringLayout.SOUTH,
				labelContactMobilePhone);
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactMail, 0, SpringLayout.WEST, labelContactСountry);
		panel.add(labelContactMail);

		labelContactWriСountry = new JLabel();
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactWriСountry, 5, SpringLayout.EAST,
				labelContactMobilePhone);
		sl_panelContact.putConstraint(SpringLayout.SOUTH, labelContactWriСountry, 0, SpringLayout.SOUTH,
				labelContactСountry);
		sl_panelContact.putConstraint(SpringLayout.EAST, labelContactWriСountry, 0, SpringLayout.EAST, panel);
		panel.add(labelContactWriСountry);

		labelContactWriRegion = new JLabel();
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactWriRegion, 5, SpringLayout.EAST,
				labelContactMobilePhone);
		sl_panelContact.putConstraint(SpringLayout.SOUTH, labelContactWriRegion, 0, SpringLayout.SOUTH,
				labelContactRegion);
		sl_panelContact.putConstraint(SpringLayout.EAST, labelContactWriRegion, 0, SpringLayout.EAST, panel);
		panel.add(labelContactWriRegion);

		labelContactWriTown = new JLabel();
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactWriTown, 5, SpringLayout.EAST,
				labelContactMobilePhone);
		sl_panelContact.putConstraint(SpringLayout.SOUTH, labelContactWriTown, 0, SpringLayout.SOUTH, labelContactTown);
		sl_panelContact.putConstraint(SpringLayout.EAST, labelContactWriTown, 0, SpringLayout.EAST, panel);
		panel.add(labelContactWriTown);

		labelContactWritPostIndex = new JLabel();
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactWritPostIndex, 5, SpringLayout.EAST,
				labelContactMobilePhone);
		sl_panelContact.putConstraint(SpringLayout.SOUTH, labelContactWritPostIndex, 0, SpringLayout.SOUTH,
				labelContactPostIndex);
		sl_panelContact.putConstraint(SpringLayout.EAST, labelContactWritPostIndex, 0, SpringLayout.EAST, panel);
		panel.add(labelContactWritPostIndex);

		labelContactWriStreet = new JLabel();
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactWriStreet, 5, SpringLayout.EAST,
				labelContactMobilePhone);
		sl_panelContact.putConstraint(SpringLayout.SOUTH, labelContactWriStreet, 0, SpringLayout.SOUTH,
				labelContactStreet);
		sl_panelContact.putConstraint(SpringLayout.EAST, labelContactWriStreet, 0, SpringLayout.EAST, panel);
		panel.add(labelContactWriStreet);

		labelContactWriBirth = new JLabel();
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactWriBirth, 5, SpringLayout.EAST,
				labelContactMobilePhone);
		sl_panelContact.putConstraint(SpringLayout.SOUTH, labelContactWriBirth, 0, SpringLayout.SOUTH,
				labelContactBirth);
		sl_panelContact.putConstraint(SpringLayout.EAST, labelContactWriBirth, 0, SpringLayout.EAST, panel);
		panel.add(labelContactWriBirth);

		labelContactWriRoom = new JLabel();
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactWriRoom, 5, SpringLayout.EAST,
				labelContactMobilePhone);
		sl_panelContact.putConstraint(SpringLayout.SOUTH, labelContactWriRoom, 0, SpringLayout.SOUTH, labelContactRoom);
		sl_panelContact.putConstraint(SpringLayout.EAST, labelContactWriRoom, 0, SpringLayout.EAST, panel);
		panel.add(labelContactWriRoom);

		labelContactWriPhone = new JLabel();
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactWriPhone, 5, SpringLayout.EAST,
				labelContactMobilePhone);
		sl_panelContact.putConstraint(SpringLayout.SOUTH, labelContactWriPhone, 0, SpringLayout.SOUTH,
				labelContactPhoneInside);
		sl_panelContact.putConstraint(SpringLayout.EAST, labelContactWriPhone, 0, SpringLayout.EAST, panel);
		panel.add(labelContactWriPhone);

		labelContactWriPhoneInside = new JLabel();
		labelContactWriPhoneInside.setFont(new Font("Roboto", Font.PLAIN, 14));
		labelContactWriPhoneInside.setForeground(Color.BLUE);
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactWriPhoneInside, 5, SpringLayout.EAST,
				labelContactMobilePhone);
		sl_panelContact.putConstraint(SpringLayout.SOUTH, labelContactWriPhoneInside, 0, SpringLayout.SOUTH,
				labelContactPhone);
		sl_panelContact.putConstraint(SpringLayout.EAST, labelContactWriPhoneInside, 0, SpringLayout.EAST, panel);
		panel.add(labelContactWriPhoneInside);

		labelContactWriMobilePhone = new JLabel();
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactWriMobilePhone, 5, SpringLayout.EAST,
				labelContactMobilePhone);
		sl_panelContact.putConstraint(SpringLayout.SOUTH, labelContactWriMobilePhone, 0, SpringLayout.SOUTH,
				labelContactMobilePhone);
		sl_panelContact.putConstraint(SpringLayout.EAST, labelContactWriMobilePhone, 0, SpringLayout.EAST, panel);
		panel.add(labelContactWriMobilePhone);

		labelContactWriMail = new JLabel();
		labelContactWriMail.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactWriMail, 5, SpringLayout.EAST,
				labelContactMobilePhone);
		sl_panelContact.putConstraint(SpringLayout.SOUTH, labelContactWriMail, 0, SpringLayout.SOUTH, labelContactMail);
		/*
		 * sl_panelContact.putConstraint(SpringLayout.EAST, labelContactWriMail,
		 * 0, SpringLayout.EAST, panel);
		 */
		panel.add(labelContactWriMail);

	}

	public void setLoginField() {
		if (!this.textFieldLogin.isEnabled()) {
			UserDto user = core.getCompanys().findUserBysAMAccountName(System.getProperty("user.name"));
			if (null != user) {
				this.textFieldLogin.setText(user.getMail());
			}
			this.textFieldLogin.setEnabled(true);
		}
	}

	private void createTabMessages() {
		panelMessages.setLayout(new CardLayout(0, 0));

		SpringLayout sl_panelAuth = new SpringLayout();
		panelAuth = new JPanel();
		panelAuth.setLayout(sl_panelAuth);
		panelMessages.add(panelAuth, "panelAuth");

		Dimension demensionLoginPass = new Dimension(70, 25);
		JLabel lblLogin = new JLabel("Email:");
		lblLogin.setPreferredSize(demensionLoginPass);
		lblLogin.setMinimumSize(demensionLoginPass);
		lblLogin.setMaximumSize(demensionLoginPass);
		sl_panelAuth.putConstraint(SpringLayout.NORTH, lblLogin, 10, SpringLayout.NORTH, panelAuth);
		sl_panelAuth.putConstraint(SpringLayout.WEST, lblLogin, 10, SpringLayout.WEST, panelAuth);
		panelAuth.add(lblLogin);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setPreferredSize(demensionLoginPass);
		lblPassword.setMinimumSize(demensionLoginPass);
		lblPassword.setMaximumSize(demensionLoginPass);
		sl_panelAuth.putConstraint(SpringLayout.NORTH, lblPassword, 30, SpringLayout.NORTH, lblLogin);
		sl_panelAuth.putConstraint(SpringLayout.WEST, lblPassword, 10, SpringLayout.WEST, panelAuth);
		panelAuth.add(lblPassword);

		Dimension demensionLoginPassField = new Dimension(150, 25);
		textFieldLogin = new JTextField();
		textFieldLogin.setEnabled(false);
		sl_panelAuth.putConstraint(SpringLayout.NORTH, textFieldLogin, 10, SpringLayout.NORTH, panelAuth);
		sl_panelAuth.putConstraint(SpringLayout.WEST, textFieldLogin, 0, SpringLayout.EAST, lblLogin);
		textFieldLogin.setPreferredSize(demensionLoginPassField);
		textFieldLogin.setMinimumSize(demensionLoginPassField);
		textFieldLogin.setMaximumSize(demensionLoginPassField);
		panelAuth.add(textFieldLogin);

		passwordField = new JPasswordField();
		sl_panelAuth.putConstraint(SpringLayout.NORTH, passwordField, 30, SpringLayout.NORTH, textFieldLogin);
		sl_panelAuth.putConstraint(SpringLayout.WEST, passwordField, 0, SpringLayout.EAST, lblPassword);
		passwordField.setPreferredSize(demensionLoginPassField);
		passwordField.setMinimumSize(demensionLoginPassField);
		passwordField.setMaximumSize(demensionLoginPassField);
		panelAuth.add(passwordField);

		buttonAuth = new JButton("Вход");
		sl_panelAuth.putConstraint(SpringLayout.NORTH, buttonAuth, 16, SpringLayout.SOUTH, lblPassword);
		sl_panelAuth.putConstraint(SpringLayout.WEST, buttonAuth, 0, SpringLayout.WEST, lblLogin);
		panelAuth.add(buttonAuth);

		panelMessagesEditor = new JPanel();
		panelMessagesEditor.setVisible(false);
		panelMessages.add(panelMessagesEditor);
		SpringLayout sl_panelMessagesEditor = new SpringLayout();
		panelMessagesEditor.setLayout(sl_panelMessagesEditor);

		JLabel labelMessagesEditorFrom = new JLabel("От:");
		sl_panelMessagesEditor.putConstraint(SpringLayout.NORTH, labelMessagesEditorFrom, 20, SpringLayout.NORTH,
				panelMessagesEditor);
		sl_panelMessagesEditor.putConstraint(SpringLayout.WEST, labelMessagesEditorFrom, 10, SpringLayout.WEST,
				panelMessagesEditor);
		panelMessagesEditor.add(labelMessagesEditorFrom);

		JLabel labelMessagesEditorTo = new JLabel("Кому:");
		sl_panelMessagesEditor.putConstraint(SpringLayout.NORTH, labelMessagesEditorTo, 30, SpringLayout.NORTH,
				labelMessagesEditorFrom);
		sl_panelMessagesEditor.putConstraint(SpringLayout.WEST, labelMessagesEditorTo, 10, SpringLayout.WEST,
				panelMessagesEditor);
		panelMessagesEditor.add(labelMessagesEditorTo);

		JLabel labelMessagesEditorSubject = new JLabel("Тема:");
		sl_panelMessagesEditor.putConstraint(SpringLayout.NORTH, labelMessagesEditorSubject, 30, SpringLayout.NORTH,
				labelMessagesEditorTo);
		sl_panelMessagesEditor.putConstraint(SpringLayout.WEST, labelMessagesEditorSubject, 10, SpringLayout.WEST,
				panelMessagesEditor);
		panelMessagesEditor.add(labelMessagesEditorSubject);

		buttonMessagesEditorClear = new JButton("Очистить");
		sl_panelMessagesEditor.putConstraint(SpringLayout.NORTH, buttonMessagesEditorClear, 15, SpringLayout.NORTH,
				panelMessagesEditor);
		panelMessagesEditor.add(buttonMessagesEditorClear);

		buttonMessagesEditorSend = new JButton("Отправить");
		sl_panelMessagesEditor.putConstraint(SpringLayout.EAST, buttonMessagesEditorClear, -15, SpringLayout.WEST,
				buttonMessagesEditorSend);
		sl_panelMessagesEditor.putConstraint(SpringLayout.NORTH, buttonMessagesEditorSend, 15, SpringLayout.NORTH,
				panelMessagesEditor);
		sl_panelMessagesEditor.putConstraint(SpringLayout.EAST, buttonMessagesEditorSend, -10, SpringLayout.EAST,
				panelMessagesEditor);
		panelMessagesEditor.add(buttonMessagesEditorSend);

		labelMessagesEditorWriFrom = new JLabel("");
		labelMessagesEditorWriFrom.setPreferredSize(new Dimension(12, 27));
		labelMessagesEditorWriFrom.setMinimumSize(new Dimension(12, 27));
		sl_panelMessagesEditor.putConstraint(SpringLayout.EAST, labelMessagesEditorWriFrom, -10, SpringLayout.WEST,
				buttonMessagesEditorClear);
		sl_panelMessagesEditor.putConstraint(SpringLayout.NORTH, labelMessagesEditorWriFrom, 0, SpringLayout.NORTH,
				labelMessagesEditorFrom);
		sl_panelMessagesEditor.putConstraint(SpringLayout.WEST, labelMessagesEditorWriFrom, 10, SpringLayout.EAST,
				labelMessagesEditorSubject);
		panelMessagesEditor.add(labelMessagesEditorWriFrom);

		textFieldMessagesEditorSubject = new JTextField();
		sl_panelMessagesEditor.putConstraint(SpringLayout.NORTH, textFieldMessagesEditorSubject, 0, SpringLayout.NORTH,
				labelMessagesEditorSubject);
		sl_panelMessagesEditor.putConstraint(SpringLayout.WEST, textFieldMessagesEditorSubject, 10, SpringLayout.EAST,
				labelMessagesEditorSubject);
		sl_panelMessagesEditor.putConstraint(SpringLayout.EAST, textFieldMessagesEditorSubject, -10, SpringLayout.EAST,
				panelMessagesEditor);

		panelMessagesEditor.add(textFieldMessagesEditorSubject);

		Dimension dimensionTo = new Dimension(262, 25);

		listMessagesEditorTo = new JList();
		listMessagesEditorTo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPaneMessagesEditorTo = new JScrollPane(listMessagesEditorTo);
		scrollPaneMessagesEditorTo.setPreferredSize(dimensionTo);
		scrollPaneMessagesEditorTo.setMinimumSize(dimensionTo);
		scrollPaneMessagesEditorTo.setMaximumSize(dimensionTo);
		sl_panelMessagesEditor.putConstraint(SpringLayout.NORTH, scrollPaneMessagesEditorTo, 0, SpringLayout.NORTH,
				labelMessagesEditorTo);
		sl_panelMessagesEditor.putConstraint(SpringLayout.WEST, scrollPaneMessagesEditorTo, 10, SpringLayout.EAST,
				labelMessagesEditorSubject);
		sl_panelMessagesEditor.putConstraint(SpringLayout.EAST, scrollPaneMessagesEditorTo, -10, SpringLayout.EAST,
				panelMessagesEditor);
		panelMessagesEditor.add(scrollPaneMessagesEditorTo);

		Dimension dimensionEditor = new Dimension(262, 280);

		textAreaMessagesEditor = new JTextArea();
		textAreaMessagesEditor.setWrapStyleWord(true);
		JScrollPane scrollPaneMessagesEditor = new JScrollPane(textAreaMessagesEditor);
		scrollPaneMessagesEditor.setPreferredSize(dimensionEditor);
		sl_panelMessagesEditor.putConstraint(SpringLayout.NORTH, scrollPaneMessagesEditor, 30, SpringLayout.NORTH,
				labelMessagesEditorSubject);
		sl_panelMessagesEditor.putConstraint(SpringLayout.WEST, scrollPaneMessagesEditor, 10, SpringLayout.WEST,
				panelMessagesEditor);
		sl_panelMessagesEditor.putConstraint(SpringLayout.SOUTH, textAreaMessagesEditor, -144, SpringLayout.SOUTH,
				panelMessagesEditor);
		sl_panelMessagesEditor.putConstraint(SpringLayout.EAST, scrollPaneMessagesEditor, -10, SpringLayout.EAST,
				panelMessagesEditor);
		panelMessagesEditor.add(scrollPaneMessagesEditor);

		buttonMessagesEditorAttachment = new JButton("Добавить вложение");
		sl_panelMessagesEditor.putConstraint(SpringLayout.NORTH, buttonMessagesEditorAttachment, 10, SpringLayout.SOUTH,
				scrollPaneMessagesEditor);
		sl_panelMessagesEditor.putConstraint(SpringLayout.WEST, buttonMessagesEditorAttachment, 10, SpringLayout.WEST,
				panelMessagesEditor);
		panelMessagesEditor.add(buttonMessagesEditorAttachment);
		//
		JScrollPane paneMessagesEditorAttachment = new JScrollPane();
		sl_panelMessagesEditor.putConstraint(SpringLayout.NORTH, paneMessagesEditorAttachment, 10, SpringLayout.SOUTH,
				scrollPaneMessagesEditor);
		sl_panelMessagesEditor.putConstraint(SpringLayout.WEST, paneMessagesEditorAttachment, 20, SpringLayout.EAST,
				buttonMessagesEditorAttachment);
		sl_panelMessagesEditor.putConstraint(SpringLayout.SOUTH, paneMessagesEditorAttachment, -5, SpringLayout.SOUTH,
				panelMessagesEditor);
		sl_panelMessagesEditor.putConstraint(SpringLayout.EAST, paneMessagesEditorAttachment, -10, SpringLayout.EAST,
				panelMessagesEditor);
		panelMessagesEditor.add(paneMessagesEditorAttachment);

		listMessagesEditorAttachment = new JList();
		paneMessagesEditorAttachment.setViewportView(listMessagesEditorAttachment);

		buttonMessagesEditorAttachmentClear = new JButton("Очистить вложения");
		sl_panelMessagesEditor.putConstraint(SpringLayout.NORTH, buttonMessagesEditorAttachmentClear, 5,
				SpringLayout.SOUTH, buttonMessagesEditorAttachment);
		sl_panelMessagesEditor.putConstraint(SpringLayout.WEST, buttonMessagesEditorAttachmentClear, 0,
				SpringLayout.WEST, labelMessagesEditorFrom);
		panelMessagesEditor.add(buttonMessagesEditorAttachmentClear);

		checkBoxSignature = new JCheckBox("создать подпись");
		checkBoxSignature.setSelected(true);
		sl_panelMessagesEditor.putConstraint(SpringLayout.NORTH, checkBoxSignature, 5, SpringLayout.SOUTH,
				buttonMessagesEditorAttachmentClear);
		sl_panelMessagesEditor.putConstraint(SpringLayout.WEST, checkBoxSignature, 10, SpringLayout.WEST,
				panelMessagesEditor);
		panelMessagesEditor.add(checkBoxSignature);

		checkBoxNotifyDelivery = new JCheckBox("Уведомить о доставке");
		checkBoxNotifyDelivery.setSelected(false);
		sl_panelMessagesEditor.putConstraint(SpringLayout.NORTH, checkBoxNotifyDelivery, 0, SpringLayout.SOUTH,
				checkBoxSignature);
		sl_panelMessagesEditor.putConstraint(SpringLayout.WEST, checkBoxNotifyDelivery, 10, SpringLayout.WEST,
				panelMessagesEditor);
		panelMessagesEditor.add(checkBoxNotifyDelivery);

		checkBoxNotifyRead = new JCheckBox("Уведомить о прочтении");
		checkBoxNotifyRead.setSelected(false);
		sl_panelMessagesEditor.putConstraint(SpringLayout.NORTH, checkBoxNotifyRead, 0, SpringLayout.SOUTH,
				checkBoxNotifyDelivery);
		sl_panelMessagesEditor.putConstraint(SpringLayout.WEST, checkBoxNotifyRead, 10, SpringLayout.WEST,
				panelMessagesEditor);
		panelMessagesEditor.add(checkBoxNotifyRead);

		demensionLoginPass = null;
		dimensionTo = null;
		dimensionEditor = null;
		demensionLoginPassField = null;
	}

	private void createCopyLabel(JLabel label) {
		label.setMaximumSize(DEMENSION_ICON_MENU);
		label.setMinimumSize(DEMENSION_ICON_MENU);
		label.setSize(DEMENSION_ICON_MENU);
		label.setToolTipText("копировать");
		label.setIcon(resizeIcon(this.getResourceImage(COPY_IMAGE), label));
	}

	private void createTabPerson(JPanel panel) {
		sl_panelPerson = new SpringLayout();
		panel.setLayout(sl_panelPerson);

		panelQrCode = new JPanel();
		sl_panelPerson.putConstraint(SpringLayout.NORTH, panelQrCode, 5, SpringLayout.NORTH, panel);
		sl_panelPerson.putConstraint(SpringLayout.WEST, panelQrCode, 10, SpringLayout.WEST, panel);
		panelQrCode.setPreferredSize(DEMENSION_IMAGE);
		panelQrCode.setMinimumSize(DEMENSION_IMAGE);
		panelQrCode.setMaximumSize(DEMENSION_IMAGE);
		panel.add(panelQrCode);

		JPanel panelPhoto = new JPanel();
		sl_panelPerson.putConstraint(SpringLayout.NORTH, panelPhoto, 0, SpringLayout.NORTH, panelQrCode);
		sl_panelPerson.putConstraint(SpringLayout.WEST, panelPhoto, 19, SpringLayout.EAST, panelQrCode);
		panelPhoto.setPreferredSize(DEMENSION_IMAGE);
		panelPhoto.setMinimumSize(DEMENSION_IMAGE);
		panelPhoto.setMaximumSize(DEMENSION_IMAGE);

		panelPhoto.setLayout(new BorderLayout(0, 0));
		panel.add(panelPhoto);

		labelPersonPic = new JLabel();
		labelPersonPic.setSize(DEMENSION_IMAGE);
		labelPersonPic.setIcon(resizeIcon(getResourceImage(USERS_IMAGE), labelPersonPic));
		panelQrCode.add(labelPersonPic);

		labelPersonQrCode = new JLabel();
		labelPersonQrCode.setSize(DEMENSION_IMAGE);
		panelPhoto.add(labelPersonQrCode);

		int tab = 20;
		labelPersonFio = new JLabel("ФИО:");
		sl_panelPerson.putConstraint(SpringLayout.NORTH, labelPersonFio, 33, SpringLayout.SOUTH, panelQrCode);
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonFio, tab, SpringLayout.WEST, panelQrCode);
		panel.add(labelPersonFio);

		labelCopyPersonFio = new JLabel("");
		sl_panelPerson.putConstraint(SpringLayout.NORTH, labelCopyPersonFio, 0, SpringLayout.NORTH, labelPersonFio);
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelCopyPersonFio, 5, SpringLayout.WEST, panelPerson);
		createCopyLabel(labelCopyPersonFio);
		panel.add(labelCopyPersonFio);

		JLabel labelPersonCompany = new JLabel("Компания:");
		sl_panelPerson.putConstraint(SpringLayout.NORTH, labelPersonCompany, 6, SpringLayout.SOUTH, labelPersonFio);
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonCompany, tab, SpringLayout.WEST, panelQrCode);
		panel.add(labelPersonCompany);

		labelCopyPersonCompany = new JLabel("");
		sl_panelPerson.putConstraint(SpringLayout.NORTH, labelCopyPersonCompany, 0, SpringLayout.NORTH,
				labelPersonCompany);
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelCopyPersonCompany, 5, SpringLayout.WEST, panelPerson);
		createCopyLabel(labelCopyPersonCompany);
		panel.add(labelCopyPersonCompany);

		JLabel labelPersonDepartment = new JLabel("Отдел:");
		sl_panelPerson.putConstraint(SpringLayout.NORTH, labelPersonDepartment, 6, SpringLayout.SOUTH,
				labelPersonCompany);
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonDepartment, tab, SpringLayout.WEST, panelQrCode);
		panel.add(labelPersonDepartment);

		JLabel labelPersonDescription = new JLabel("Должность:");
		sl_panelPerson.putConstraint(SpringLayout.NORTH, labelPersonDescription, 6, SpringLayout.SOUTH,
				labelPersonDepartment);
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonDescription, tab, SpringLayout.WEST, panelQrCode);
		panel.add(labelPersonDescription);

		labelCopyPersonDescription = new JLabel("");
		sl_panelPerson.putConstraint(SpringLayout.NORTH, labelCopyPersonDescription, 0, SpringLayout.NORTH,
				labelPersonDescription);
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelCopyPersonDescription, 5, SpringLayout.WEST, panelPerson);
		createCopyLabel(labelCopyPersonDescription);
		panel.add(labelCopyPersonDescription);

		JLabel labelPersonMail = new JLabel("Электронная почта:");
		sl_panelPerson.putConstraint(SpringLayout.NORTH, labelPersonMail, 6, SpringLayout.SOUTH,
				labelPersonDescription);
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonMail, tab, SpringLayout.WEST, panelQrCode);
		panel.add(labelPersonMail);

		labelCopyPersonMail = new JLabel("");
		sl_panelPerson.putConstraint(SpringLayout.NORTH, labelCopyPersonMail, 0, SpringLayout.NORTH, labelPersonMail);
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelCopyPersonMail, 5, SpringLayout.WEST, panelPerson);
		createCopyLabel(labelCopyPersonMail);
		panel.add(labelCopyPersonMail);

		JLabel labelPersonPhoneInside = new JLabel("Телефон:");
		sl_panelPerson.putConstraint(SpringLayout.NORTH, labelPersonPhoneInside, 6, SpringLayout.SOUTH,
				labelPersonMail);
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonPhoneInside, tab, SpringLayout.WEST, panelQrCode);
		panel.add(labelPersonPhoneInside);

		labelCopyPersonPhoneInside = new JLabel("");
		sl_panelPerson.putConstraint(SpringLayout.NORTH, labelCopyPersonPhoneInside, 0, SpringLayout.NORTH,
				labelPersonPhoneInside);
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelCopyPersonPhoneInside, 5, SpringLayout.WEST, panelPerson);
		createCopyLabel(labelCopyPersonPhoneInside);
		panel.add(labelCopyPersonPhoneInside);

		JLabel labelPersonPhoneSmall = new JLabel("Телефон (внутр.):");
		sl_panelPerson.putConstraint(SpringLayout.NORTH, labelPersonPhoneSmall, 6, SpringLayout.SOUTH,
				labelPersonPhoneInside);
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonPhoneSmall, tab, SpringLayout.WEST, panelQrCode);
		panel.add(labelPersonPhoneSmall);

		JLabel labelPersonRoom = new JLabel("Комната:");
		sl_panelPerson.putConstraint(SpringLayout.NORTH, labelPersonRoom, 6, SpringLayout.SOUTH, labelPersonPhoneSmall);
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonRoom, tab, SpringLayout.WEST, panelQrCode);
		panel.add(labelPersonRoom);

		labelPersonHead = new JLabel("Руководитель:");
		sl_panelPerson.putConstraint(SpringLayout.NORTH, labelPersonHead, 6, SpringLayout.SOUTH, labelPersonRoom);
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonHead, tab, SpringLayout.WEST, panelQrCode);
		panelQrCode.setLayout(new BorderLayout(0, 0));
		panel.add(labelPersonHead);

		labelPersonWriDescription = new JLabel();
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonWriDescription, 5, SpringLayout.EAST,
				labelPersonMail);
		sl_panelPerson.putConstraint(SpringLayout.SOUTH, labelPersonWriDescription, 0, SpringLayout.SOUTH,
				labelPersonDescription);
		sl_panelPerson.putConstraint(SpringLayout.EAST, labelPersonWriDescription, 0, SpringLayout.EAST, panel);
		panel.add(labelPersonWriDescription);

		labelPersonWriFio = new JLabel();
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonWriFio, 5, SpringLayout.EAST, labelPersonMail);
		sl_panelPerson.putConstraint(SpringLayout.SOUTH, labelPersonWriFio, 0, SpringLayout.SOUTH, labelPersonFio);
		sl_panelPerson.putConstraint(SpringLayout.EAST, labelPersonWriFio, 0, SpringLayout.EAST, panel);
		panel.add(labelPersonWriFio);

		labelPersonWriCompany = new JLabel();
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonWriCompany, 5, SpringLayout.EAST, labelPersonMail);
		sl_panelPerson.putConstraint(SpringLayout.SOUTH, labelPersonWriCompany, 0, SpringLayout.SOUTH,
				labelPersonCompany);
		sl_panelPerson.putConstraint(SpringLayout.EAST, labelPersonWriCompany, 0, SpringLayout.EAST, panel);
		panel.add(labelPersonWriCompany);

		labelPersonWriDepartment = new JLabel();
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonWriDepartment, 5, SpringLayout.EAST,
				labelPersonMail);
		sl_panelPerson.putConstraint(SpringLayout.SOUTH, labelPersonWriDepartment, 0, SpringLayout.SOUTH,
				labelPersonDepartment);
		sl_panelPerson.putConstraint(SpringLayout.EAST, labelPersonWriDepartment, 0, SpringLayout.EAST, panel);
		panel.add(labelPersonWriDepartment);

		labelPersonWriRoom = new JLabel();
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonWriRoom, 5, SpringLayout.EAST, labelPersonMail);
		sl_panelPerson.putConstraint(SpringLayout.SOUTH, labelPersonWriRoom, 0, SpringLayout.SOUTH, labelPersonRoom);
		sl_panelPerson.putConstraint(SpringLayout.EAST, labelPersonWriRoom, 0, SpringLayout.EAST, panel);
		panel.add(labelPersonWriRoom);

		labelPersonWriPhoneInside = new JLabel();
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonWriPhoneInside, 5, SpringLayout.EAST,
				labelPersonMail);
		sl_panelPerson.putConstraint(SpringLayout.SOUTH, labelPersonWriPhoneInside, 0, SpringLayout.SOUTH,
				labelPersonPhoneInside);
		sl_panelPerson.putConstraint(SpringLayout.EAST, labelPersonWriPhoneInside, 0, SpringLayout.EAST, panel);
		panel.add(labelPersonWriPhoneInside);

		labelPersonWriPhoneSmall = new JLabel();
		labelPersonWriPhoneSmall.setFont(new Font("Roboto", Font.PLAIN, 14));
		labelPersonWriPhoneSmall.setForeground(Color.BLUE);
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonWriPhoneSmall, 5, SpringLayout.EAST,
				labelPersonMail);
		sl_panelPerson.putConstraint(SpringLayout.SOUTH, labelPersonWriPhoneSmall, 0, SpringLayout.SOUTH,
				labelPersonPhoneSmall);
		sl_panelPerson.putConstraint(SpringLayout.EAST, labelPersonWriPhoneSmall, 0, SpringLayout.EAST, panel);
		panel.add(labelPersonWriPhoneSmall);

		labelPersonWriMail = new JLabel();
		labelPersonWriMail.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonWriMail, 5, SpringLayout.EAST, labelPersonMail);
		sl_panelPerson.putConstraint(SpringLayout.SOUTH, labelPersonWriMail, 0, SpringLayout.SOUTH, labelPersonMail);
		panel.add(labelPersonWriMail);

		labelPersonWriHead = new JLabel();
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonWriHead, 5, SpringLayout.EAST, labelPersonMail);
		sl_panelPerson.putConstraint(SpringLayout.SOUTH, labelPersonWriHead, 0, SpringLayout.SOUTH, labelPersonHead);
		sl_panelPerson.putConstraint(SpringLayout.EAST, labelPersonWriHead, 0, SpringLayout.EAST, panel);
		panel.add(labelPersonWriHead);
	}

	private void createPanelStatus() {
		labelStatusBar = new JLabel("");
		labelStatusBar.setPreferredSize(new Dimension(60, 16));
		frmHandbook.getContentPane().add(labelStatusBar, java.awt.BorderLayout.SOUTH);
	}

	private void createPanelFSM(JPanel panelSearch) {
		SpringLayout layoutFSM = new SpringLayout();
		panelFSM = new JPanel(layoutFSM);
		panelFSM.setPreferredSize(new Dimension(0, 290));
		TitledBorder borderFSM = new TitledBorder("Поиск по адресной книге");
		borderFSM.setTitlePosition(TitledBorder.TOP);
		panelFSM.setBorder(borderFSM);
		panelSearch.add(panelFSM);

		textFieldLastName = new JTextField("");
		textFieldLastName.setToolTipText("Фамилия");
		textFieldLastName.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldLastName.setTransferHandler(null);
		JLabel labelSurName = new JLabel(textFieldLastName.getToolTipText() + ':');
		labelSurName.setMinimumSize(new Dimension(50, 0));

		textFieldFirstName = new JTextField();
		textFieldFirstName.setToolTipText("Имя");
		textFieldFirstName.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldFirstName.setTransferHandler(null);
		JLabel labelFirstName = new JLabel(textFieldFirstName.getToolTipText() + ':');
		labelFirstName.setMinimumSize(new Dimension(50, 0));

		textFieldMiddleName = new JTextField();

		textFieldMiddleName.setToolTipText("Отчество");
		textFieldMiddleName.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldMiddleName.setTransferHandler(null);
		JLabel labelMiddleName = new JLabel(textFieldMiddleName.getToolTipText() + ':');
		labelMiddleName.setMinimumSize(new Dimension(50, 0));

		comboBoxCompany = new JComboBox<CompanyDto>();

		comboBoxCompany.addItem(new CompanyDto());
		comboBoxCompany.setToolTipText("Комания");
		JLabel labelCompany = new JLabel(comboBoxCompany.getToolTipText() + ':');
		labelCompany.setMinimumSize(new Dimension(50, 0));

		comboBoxFilial = new JComboBox<CompanyDto>();
		comboBoxFilial.addItem(new CompanyDto());
		comboBoxFilial.setToolTipText("Представительство");
		JLabel labelFilials = new JLabel(comboBoxFilial.getToolTipText() + ':');
		labelFilials.setMinimumSize(new Dimension(50, 0));
		comboBoxFilial.disable();
		comboBoxFilial.setName("comboBoxFilial");

		textFieldDepartment = new JTextField();
		textFieldDepartment.setToolTipText("Отдел");
		textFieldDepartment.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldDepartment.setTransferHandler(null);
		JLabel labelDepartment = new JLabel(textFieldDepartment.getToolTipText() + ':');
		labelDepartment.setMinimumSize(new Dimension(50, 0));

		textFieldPesonPosition = new JTextField();
		textFieldPesonPosition.setToolTipText("Должность");
		textFieldPesonPosition.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldPesonPosition.setTransferHandler(null);
		JLabel labelPesonPosition = new JLabel(textFieldPesonPosition.getToolTipText() + ':');
		labelPesonPosition.setMinimumSize(new Dimension(50, 0));

		textFieldPhone = new JTextField();
		textFieldPhone.setToolTipText("Номер телефона");
		textFieldPhone.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldPhone.setTransferHandler(null);
		JLabel labelPhone = new JLabel(textFieldPhone.getToolTipText() + ':');
		labelPhone.setMinimumSize(new Dimension(50, 0));

		textFieldRoom = new JTextField();
		textFieldRoom.setToolTipText("Комната");
		textFieldRoom.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldRoom.setMinimumSize(new Dimension(50, 0));
		textFieldRoom.setTransferHandler(null);
		JLabel labelRoom = new JLabel(textFieldRoom.getToolTipText() + ':');
		labelRoom.setMinimumSize(new Dimension(50, 0));

		layoutFSM.putConstraint(SpringLayout.NORTH, labelSurName, 5, SpringLayout.NORTH, panelFSM);
		layoutFSM.putConstraint(SpringLayout.WEST, textFieldLastName, 5, SpringLayout.EAST, labelFilials);
		layoutFSM.putConstraint(SpringLayout.NORTH, textFieldLastName, 0, SpringLayout.NORTH, panelFSM);
		layoutFSM.putConstraint(SpringLayout.EAST, textFieldLastName, 0, SpringLayout.EAST, panelFSM);

		layoutFSM.putConstraint(SpringLayout.NORTH, labelFirstName, 28, SpringLayout.NORTH, labelSurName);
		layoutFSM.putConstraint(SpringLayout.WEST, textFieldFirstName, 5, SpringLayout.EAST, labelFilials);
		layoutFSM.putConstraint(SpringLayout.NORTH, textFieldFirstName, 28, SpringLayout.NORTH, textFieldLastName);
		layoutFSM.putConstraint(SpringLayout.EAST, textFieldFirstName, 0, SpringLayout.EAST, textFieldLastName);

		layoutFSM.putConstraint(SpringLayout.NORTH, labelMiddleName, 28, SpringLayout.NORTH, labelFirstName);
		layoutFSM.putConstraint(SpringLayout.WEST, textFieldMiddleName, 5, SpringLayout.EAST, labelFilials);
		layoutFSM.putConstraint(SpringLayout.NORTH, textFieldMiddleName, 28, SpringLayout.NORTH, textFieldFirstName);
		layoutFSM.putConstraint(SpringLayout.EAST, textFieldMiddleName, 0, SpringLayout.EAST, textFieldFirstName);

		layoutFSM.putConstraint(SpringLayout.NORTH, labelCompany, 28, SpringLayout.NORTH, labelMiddleName);
		layoutFSM.putConstraint(SpringLayout.WEST, comboBoxCompany, 5, SpringLayout.EAST, labelFilials);
		layoutFSM.putConstraint(SpringLayout.NORTH, comboBoxCompany, 28, SpringLayout.NORTH, textFieldMiddleName);
		layoutFSM.putConstraint(SpringLayout.EAST, comboBoxCompany, 0, SpringLayout.EAST, textFieldMiddleName);

		layoutFSM.putConstraint(SpringLayout.NORTH, labelFilials, 28, SpringLayout.NORTH, labelCompany);
		layoutFSM.putConstraint(SpringLayout.WEST, comboBoxFilial, 5, SpringLayout.EAST, labelFilials);
		layoutFSM.putConstraint(SpringLayout.NORTH, comboBoxFilial, 28, SpringLayout.NORTH, comboBoxCompany);
		layoutFSM.putConstraint(SpringLayout.EAST, comboBoxFilial, 0, SpringLayout.EAST, comboBoxCompany);

		layoutFSM.putConstraint(SpringLayout.NORTH, labelDepartment, 28, SpringLayout.NORTH, labelFilials);
		layoutFSM.putConstraint(SpringLayout.WEST, textFieldDepartment, 5, SpringLayout.EAST, labelFilials);
		layoutFSM.putConstraint(SpringLayout.NORTH, textFieldDepartment, 28, SpringLayout.NORTH, comboBoxFilial);
		layoutFSM.putConstraint(SpringLayout.EAST, textFieldDepartment, 0, SpringLayout.EAST, comboBoxFilial);

		layoutFSM.putConstraint(SpringLayout.NORTH, labelPesonPosition, 28, SpringLayout.NORTH, labelDepartment);
		layoutFSM.putConstraint(SpringLayout.WEST, textFieldPesonPosition, 5, SpringLayout.EAST, labelFilials);
		layoutFSM.putConstraint(SpringLayout.NORTH, textFieldPesonPosition, 28, SpringLayout.NORTH,
				textFieldDepartment);
		layoutFSM.putConstraint(SpringLayout.EAST, textFieldPesonPosition, 0, SpringLayout.EAST, textFieldDepartment);

		layoutFSM.putConstraint(SpringLayout.NORTH, labelPhone, 28, SpringLayout.NORTH, labelPesonPosition);
		layoutFSM.putConstraint(SpringLayout.WEST, textFieldPhone, 5, SpringLayout.EAST, labelFilials);
		layoutFSM.putConstraint(SpringLayout.NORTH, textFieldPhone, 28, SpringLayout.NORTH, textFieldPesonPosition);
		layoutFSM.putConstraint(SpringLayout.EAST, textFieldPhone, 0, SpringLayout.EAST, textFieldPesonPosition);

		layoutFSM.putConstraint(SpringLayout.NORTH, labelRoom, 28, SpringLayout.NORTH, labelPhone);
		layoutFSM.putConstraint(SpringLayout.WEST, textFieldRoom, 5, SpringLayout.EAST, labelFilials);
		layoutFSM.putConstraint(SpringLayout.NORTH, textFieldRoom, 28, SpringLayout.NORTH, textFieldPhone);
		layoutFSM.putConstraint(SpringLayout.EAST, textFieldRoom, 0, SpringLayout.EAST, textFieldPhone);

		panelFSM.add(labelSurName);
		panelFSM.add(textFieldLastName);
		panelFSM.add(labelFirstName);
		panelFSM.add(textFieldFirstName);
		panelFSM.add(labelMiddleName);
		panelFSM.add(textFieldMiddleName);
		panelFSM.add(labelCompany);
		panelFSM.add(comboBoxCompany);
		panelFSM.add(labelFilials);
		panelFSM.add(comboBoxFilial);
		panelFSM.add(labelDepartment);
		panelFSM.add(textFieldDepartment);
		panelFSM.add(labelPesonPosition);
		panelFSM.add(textFieldPesonPosition);
		panelFSM.add(labelPhone);
		panelFSM.add(textFieldPhone);
		panelFSM.add(labelRoom);
		panelFSM.add(textFieldRoom);
	}

	private ImageIcon resizeIcon(ImageIcon image, JLabel label) {
		return resizeIcon(image, label.getWidth(), label.getHeight());
	}

	private ImageIcon resizeIcon(ImageIcon image, JButton button) {
		return resizeIcon(image, button.getWidth(), button.getHeight());
	}

	private ImageIcon resizeIcon(ImageIcon image, int width, int height) {
		return new ImageIcon(image.getImage().getScaledInstance(width, height, image.getImage().SCALE_DEFAULT));
	}

	private void updatePanelView() {
		if (core.getUser() instanceof UserDto) {
			String mail = core.getUser().getHtmlFormatMail();
			String phone = core.getUser().getFullTelephone();

			labelPersonWriDescription.setText(core.getUser().getDescription());
			labelPersonWriFio.setText(core.getUser().getCn());
			labelPersonWriCompany.setText(core.getUser().getCompany());
			labelPersonWriDepartment.setText(core.getUser().getDepartment());

			if (!core.getUser().getManager().isEmpty()) {
				labelPersonHead.setVisible(true);
				labelPersonWriHead.setText(core.getUserManger(core.getUser()));
			} else {
				labelPersonHead.setVisible(false);
				labelPersonWriHead.setText("");

			}
			showGraph(core.getUserDependency(core.getUser()));
			labelPersonWriPhoneSmall.setText(core.getUser().getTelephoneNumber());
			labelPersonWriRoom.setText(core.getUser().getPhysicalDeliveryOfficeName());
			labelPersonWriPhoneInside.setText(phone);
			labelPersonWriMail.setText(mail);
			labelPersonWriMail.setToolTipText(core.getUser().getMail());

			labelContactWriСountry.setText(core.getUser().getCo());
			labelContactWriRegion.setText(core.getUser().getSt());
			labelContactWriTown.setText(core.getUser().getL());
			labelContactWritPostIndex.setText(core.getUser().getPostalCode());
			labelContactWriStreet.setText(core.getUser().getStreetAddress());
			labelContactWriBirth.setText(core.getUser().getInfo());
			labelContactWriRoom.setText(core.getUser().getPhysicalDeliveryOfficeName());
			labelContactWriPhone.setText(phone);
			labelContactWriPhoneInside.setText(core.getUser().getTelephoneNumber());
			labelContactWriMobilePhone.setText(core.getUser().getMobile());
			labelContactWriMail.setText(mail);
			labelContactWriMail.setToolTipText(core.getUser().getMail());

			labelRoomPic.setIcon(resizeIcon(((null != core.getUser().getPhysicalDeliveryOfficeName())
					&& (0 != core.getUser().getPhysicalDeliveryOfficeName().length()))
							? getResourceImage(PLANS_IMAGE + core.getUser().getPhysicalDeliveryOfficeName() + ".jpg")
							: getResourceImage(EMPTY_IMAGE),
					labelRoomPic

			));

			labelPersonPic.setIcon(resizeIcon((null != core.getUser().getJpegPhoto())
					? new ImageIcon(core.getUser().getJpegPhoto()) : getResourceImage(USERS_IMAGE), labelPersonPic));

			setPersonPanelQrCode();
		}
	}

	private void setPersonPanelQrCode() {
		if (isTransQrcode) {
			labelPersonQrCode.setToolTipText(IS_CONTACT_EN);
		} else {
			labelPersonQrCode.setToolTipText(IS_CONTACT_RU);
		}
		labelPersonQrCode.setIcon(core.createQrCodeWithLogo(getResourceFile(LOGO_IMAGE),
				core.getUser().getVCard(isTransQrcode), labelPersonQrCode.getWidth(), labelPersonQrCode.getHeight()));
	}

	private URL getResourceFile(String nameFile) {
		URL url = null;
		if (IS_LOADER_CLASS_PATH) {
			url = this.getClass().getResource(nameFile);
		} else {
			try {
				url = new File(FOLDER_IMAGE + nameFile).toURI().toURL();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return url;
	}

	private ImageIcon getResourceImage(String nameFile) {
		URL url = getResourceFile(nameFile);
		return (null == url) ? new ImageIcon(getResourceFile(EMPTY_IMAGE)) : new ImageIcon(url);
	}

	private void setSelectionTree(TreePath eTree, Object selectedValue) {
		isLockRenderTree = true;
		TreeSelectionModel selectionModel = tree.getSelectionModel();
		if (1 < selectionModel.getSelectionRows().length) {

			ArrayList<TreePath> rmSelection = new ArrayList<TreePath>();
			ArrayList<TreePath> addSelection = new ArrayList<TreePath>();

			if (selectedValue instanceof CompanyDto) {
				CompanyDto company = (CompanyDto) selectedValue;
				String Dn = company.getDn();
				String parentDn = company.getParentDn();
				if (company.getFilials().size() != 0) {
					for (int selectedId : tree.getSelectionRows()) {
						TreePath treePath = tree.getPathForRow(selectedId);
						DefaultMutableTreeNode selectedItem = (DefaultMutableTreeNode) treePath.getLastPathComponent();
						if (null != selectedItem) {
							Object value = selectedItem.getUserObject();
							if (value instanceof CompanyDto) {
								if (((CompanyDto) value).getParentDn().equals(Dn)) {
									rmSelection.add(treePath);
								}
							} else if (value instanceof UserDto) {
								String userDn = ((UserDto) value).getCompanyDn();
								for (CompanyDto filial : company.getFilials()) {
									if (filial.getDn().equals(userDn)) {
										rmSelection.add(treePath);
										break;
									}
								}
							}
						}
					}
				} else {
					for (int selectedId : tree.getSelectionRows()) {
						TreePath treePath = tree.getPathForRow(selectedId);
						DefaultMutableTreeNode selectedItem = (DefaultMutableTreeNode) treePath.getLastPathComponent();
						if (null != selectedItem) {
							Object value = selectedItem.getUserObject();
							if (value instanceof UserDto) {
								if (((UserDto) value).getCompanyDn().equals(Dn)) {
									rmSelection.add(treePath);
								}
							} else if (value instanceof CompanyDto) {
								if (((CompanyDto) value).getDn().equals(parentDn)) {
									rmSelection.add(treePath);
								}
							}
						}
					}
					addSelection.add(eTree);
				}
			} else if (selectedValue instanceof UserDto) {
				UserDto user = (UserDto) selectedValue;
				String Dn = user.getCompanyDn();
				for (int selectedId : tree.getSelectionRows()) {
					TreePath treePath = tree.getPathForRow(selectedId);
					DefaultMutableTreeNode selectedItem = (DefaultMutableTreeNode) treePath.getLastPathComponent();
					if (null != selectedItem) {
						Object value = selectedItem.getUserObject();
						if (value instanceof CompanyDto) {
							if ((((CompanyDto) value).getFilials().size() != 0)) {
								for (CompanyDto filial : (((CompanyDto) value).getFilials())) {
									if (filial.getDn().equals(Dn)) {
										rmSelection.add(treePath);
										break;
									}
								}
							} else if (((CompanyDto) value).getDn().equals(Dn)) {
								rmSelection.add(treePath);
							}
						}
					}
				}
				core.setUser(user);
				updatePanelView();
			}

			for (TreePath tree : rmSelection) {
				selectionModel.removeSelectionPath(tree);
			}

			if (0 <= addSelection.size()) {
				selectionModel.addSelectionPath(eTree);
			}

			selectionModel = null;
			rmSelection = null;
			addSelection = null;

		} else {
			if (selectedValue instanceof UserDto) {
				UserDto user = (UserDto) selectedValue;
				core.setUser(user);
				updatePanelView();
			}
		}

		setMessagesEditorTo();

		isLockRenderTree = false;
	}

	private void addListners() {
		// TODO Auto-generated method stub
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {

				TreePath eTree = e.getNewLeadSelectionPath();
				if (null == eTree)
					return;

				DefaultMutableTreeNode eNode = (DefaultMutableTreeNode) eTree.getLastPathComponent();

				if (null == eNode)
					return;

				Object selectedValue = eNode.getUserObject();

				if (!((selectedValue instanceof CompanyDto) || (selectedValue instanceof UserDto))) {
					tree.getSelectionModel().removeSelectionPath(eTree);
					return;
				}

				if (!isLockRenderTree) {
					setSelectionTree(eTree, selectedValue);
				}
			}
		});

		textFieldLastName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (filterOnlyAlphabetic(e) & filterOnlyErase(e)) {
					e.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!(filterOnlyAlphabetic(e) & filterOnlyErase(e))) {
					search();
				}
			}
		});
		textFieldMiddleName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (filterOnlyAlphabetic(e) & filterOnlyErase(e)) {
					e.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!(filterOnlyAlphabetic(e) & filterOnlyErase(e))) {
					search();
				}
			}
		});
		textFieldFirstName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (filterOnlyAlphabetic(e) & filterOnlyErase(e)) {
					e.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!(filterOnlyAlphabetic(e) & filterOnlyErase(e))) {
					search();
				}
			}
		});
		textFieldDepartment.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (filterOnlyAlphabetic(e) & filterOnlySpace(e) & filterOnlyErase(e)) {
					e.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!(filterOnlyAlphabetic(e) & filterOnlySpace(e) & filterOnlyErase(e))) {
					search();
				}
			}
		});

		textFieldPesonPosition.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (filterOnlyAlphabetic(e) & filterOnlySpace(e) & filterOnlyErase(e)) {
					e.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!(filterOnlyAlphabetic(e) & filterOnlySpace(e) & filterOnlyErase(e))) {
					search();
				}
			}
		});

		textFieldPhone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (filterOnlyDigit(e) & filterOnlyErase(e)) {
					e.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!(filterOnlyDigit(e) & filterOnlyErase(e))) {
					search();
				}
			}
		});

		textFieldRoom.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (filterOnlyDigit(e) & filterOnlyErase(e) & !filterCtrlV(e)) {
					e.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!(filterOnlyDigit(e) & filterOnlyErase(e))) {
					search();
				}
			}
		});

		comboBoxCompany.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if ((e.getStateChange() == ItemEvent.SELECTED)) {
					companyBoxLogic();
					search();
				}
			}
		});

		comboBoxFilial.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if ((e.getStateChange() == ItemEvent.SELECTED) && !isLockRenderComboBox) {
					search();
				}
			}
		});
		// клик по иконке обновить
		labelUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addTreePreloader();
				core.loadData(false);
				labelUpdate.setBorder(null);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				labelUpdate.setBorder(BORDER_ICON_MENU);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				labelUpdate.setBorder(null);
			}
		});

		labelCopyMails.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				labelCopyMails.setBorder(null);
				String mails = "";
				TreePath[] paths = tree.getSelectionPaths();
				if (paths != null) {
					mails = core.toStringUserMails(getListUsersFromTreePath(paths));
				}
				core.putStringToClipboard(mails);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				labelCopyMails.setBorder(BORDER_ICON_MENU);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				labelCopyMails.setBorder(null);
			}
		});

		labelSaveXls.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				saveToXls();
				labelSaveXls.setBorder(null);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				labelSaveXls.setBorder(BORDER_ICON_MENU);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				labelSaveXls.setBorder(null);
			}
		});

		labelCollapsTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				labelCollapsTree.setBorder(null);
				collapseAll();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				labelCollapsTree.setBorder(BORDER_ICON_MENU);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				labelCollapsTree.setBorder(null);
			}
		});

		labelCopyPersonFio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				labelCopyPersonFio.setBorder(null);
				runLinkCopy(labelPersonWriFio.getText());
			}

			@Override
			public void mousePressed(MouseEvent e) {
				labelCopyPersonFio.setBorder(BORDER_ICON_MENU);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				labelCopyPersonFio.setBorder(null);
			}
		});

		labelCopyPersonCompany.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				labelCopyPersonCompany.setBorder(null);
				runLinkCopy(labelPersonWriCompany.getText());
			}

			@Override
			public void mousePressed(MouseEvent e) {
				labelCopyPersonCompany.setBorder(BORDER_ICON_MENU);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				labelCopyPersonCompany.setBorder(null);
			}
		});

		labelCopyPersonDescription.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				labelCopyPersonDescription.setBorder(null);
				runLinkCopy(labelPersonWriDescription.getText());
			}

			@Override
			public void mousePressed(MouseEvent e) {
				labelCopyPersonDescription.setBorder(BORDER_ICON_MENU);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				labelCopyPersonDescription.setBorder(null);
			}
		});

		labelCopyPersonMail.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				labelCopyPersonMail.setBorder(null);
				runLinkCopy(labelPersonWriMail.getToolTipText());
			}

			@Override
			public void mousePressed(MouseEvent e) {
				labelCopyPersonMail.setBorder(BORDER_ICON_MENU);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				labelCopyPersonMail.setBorder(null);
			}
		});

		labelCopyPersonPhoneInside.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				labelCopyPersonPhoneInside.setBorder(null);
				runLinkCopy(labelPersonWriPhoneInside.getText());
			}

			@Override
			public void mousePressed(MouseEvent e) {
				labelCopyPersonPhoneInside.setBorder(BORDER_ICON_MENU);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				labelCopyPersonPhoneInside.setBorder(null);
			}
		});

		labelStatusBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				labelStatusBar.setText(labelStatusBar.getText() + " " + VERSION);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				labelStatusBar.setText(labelStatusBar.getText().replace(VERSION, ""));
			}
		});

		buttonAuth.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				authorizeMessage();
			}
		});

		passwordField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				authorizeMessage();
			}
		});

		buttonMessagesEditorClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clearMessages();
			}
		});

		buttonMessagesEditorAttachment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addToAttachment();
			}
		});

		buttonMessagesEditorAttachmentClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearMessagesAttachment();
			}
		});

		buttonMessagesEditorSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMessages();
			}
		});
		textAreaMessagesEditor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyCode() == KeyEvent.VK_ENTER) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
					sendMessages();
				}
			}
		});
		textFieldMessagesEditorSubject.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyCode() == KeyEvent.VK_ENTER) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
					sendMessages();
				}
			}
		});
		labelPersonQrCode.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				isTransQrcode = !isTransQrcode;
				if (core.getUser() instanceof UserDto) {
					if (isTransQrcode) {
						core.setStatusString(IS_CONTACT_EN);
					} else {
						core.setStatusString(IS_CONTACT_RU);
					}
					setPersonPanelQrCode();
				}
			}
		});

		labelContactWriMail.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				runLinkMail(labelContactWriMail.getToolTipText());
			}
		});
		labelPersonWriMail.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				runLinkMail(labelPersonWriMail.getToolTipText());
			}
		});
	}

	private void runLinkCopy(String clipboard) {
		/*
		 * if (core.getSystemEnv().isWeb()) { String java = new String(); try {
		 * RandomAccessFile fileIn = null; FileChannel channel = null; FileLock
		 * lock = null; try { fileIn = new RandomAccessFile(new
		 * java.io.File(".").getCanonicalPath() + "/" + "copy.java", "rw");
		 * channel = fileIn.getChannel(); lock = channel.lock();
		 * 
		 * if (lock != null) { FileDescriptor descriptorIn = fileIn.getFD();
		 * FileInputStream fileInputStream = new FileInputStream(descriptorIn);
		 * int content; while ((content = fileInputStream.read()) != -1) { java
		 * += (char) content; } } else {
		 * System.out.println("Another instance is already running loadCache");
		 * } } finally { if (lock != null && lock.isValid()) lock.release(); if
		 * (fileIn != null) fileIn.close(); } } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 * core.putStringToClipboard(java); JSObject global =
		 * JSObject.getWindow(null); global.eval(java);/
		 * /"(function() { alert(\"call\");console.log(document); var mode = document.designMode; document.designMode = \"on\"; document.execCommand(\"copy\"); document.designMode = mode;alert(\"end call\");})()"
		 * ); global = null; } else { core.putStringToClipboard(clipboard); }
		 */
		core.putStringToClipboard(clipboard);
	}

	private void runLinkMail(String email) {

		try {
			if (core.getSystemEnv().isWeb()) {
				JSObject global = JSObject.getWindow(null);
				global.eval("(function() { setTimeout(function() {  location.href = 'mailto:" + email
						+ "';  }, 0);   return \"done!\";	})()");
				global = null;
			} else {
				Desktop desktop = Desktop.getDesktop();
				desktop.mail(new URI("mailto:" + email));
				desktop = null;
			}
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private HashMap<String, UserDto> getListUsersFromTreePath(TreePath[] paths) {
		HashMap<String, UserDto> users = new HashMap<String, UserDto>();
		/* if nothing is selected */
		for (TreePath path : paths) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
			Object selected = node.getUserObject();
			if (selected instanceof UserDto) {
				UserDto user = (UserDto) selected;
				users.put(user.getDistinguishedName(), user);
			} else if (selected instanceof CompanyDto) {
				CompanyDto company = (CompanyDto) selected;
				if (company.getFilials().size() != 0) {
					for (CompanyDto filial : company.getFilials()) {
						users.putAll(filial.getUsers());
					}
				} else {
					users.putAll(company.getUsers());
				}
			}

		}
		return users;
	}

	private void setMessagesEditorTo() {
		if (panelMessagesEditor.isVisible()) {
			TreePath[] paths = tree.getSelectionPaths();
			if (paths != null) {
				HashMap<String, UserDto> users = getListUsersFromTreePath(paths);
				DefaultListModel listModel = new DefaultListModel();
				listModel.clear();
				String toolTip = new String("");
				int column = 0;
				for (UserDto user : users.values()) {
					if (core.isMailValid(user.getMail())) {
						listModel.addElement(user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName()
								+ " <" + user.getMail() + ">");
						toolTip += user.getFirstName() + " " + user.getLastName() + " [" + user.getMail() + "]";
						if (4 == column) {
							column = 0;
							toolTip += "<br>";
						} else {
							toolTip += " ";
						}
						column++;
					}
				}
				this.listMessagesEditorTo.setModel(listModel);
				this.listMessagesEditorTo.setToolTipText("<html>" + toolTip + "</html>");
				toolTip = null;
				listModel = null;
			}
		}
	}

	private void saveToXls() {
		JFrame parentFrame = new JFrame();

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Сохранить контакты как...");

		FileNameExtensionFilter xlsFilter = new FileNameExtensionFilter("*" + SaveThread.FILE_EXTENSION,
				"Microsoft Excel Documents");
		fileChooser.addChoosableFileFilter(xlsFilter);
		fileChooser.setSelectedFile(new File("adbook" + SaveThread.FILE_EXTENSION));
		int userSelection = fileChooser.showSaveDialog(parentFrame);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			core.saveToFile(fileToSave.getAbsolutePath());
		}
	}

	/**
	 * добавляет пути до файлов вложений
	 */
	private void addToAttachment() {
		JFrame parentFrame = new JFrame();
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Добавить вложение...");
		int userSelection = fileChooser.showOpenDialog(parentFrame);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToAttach = fileChooser.getSelectedFile();
			HashMap<String, String> filesList = core.addMailAttachmet(fileToAttach.getName(),
					fileToAttach.getAbsolutePath());
			this.listMessagesEditorAttachment.setListData(filesList.keySet().toArray());
		}
	}

	/**
	 * метод отправляет письма адресатам
	 */
	private void sendMessages() {
		if (checkFieldMessage()) {
			ArrayList<String> to = new ArrayList<String>();
			ListModel<?> model = listMessagesEditorTo.getModel();
			for (int i = 0; i < model.getSize(); i++) {
				to.add(model.getElementAt(i).toString());
			}
			addMessagePreloader();
			core.sendMessage(labelMessagesEditorWriFrom.getText(), to, textFieldMessagesEditorSubject.getText(),
					textAreaMessagesEditor.getText(), textFieldLogin.getText(), passwordField.getText(),
					this.checkBoxSignature.isSelected(), this.checkBoxNotifyDelivery.isSelected(),
					this.checkBoxNotifyRead.isSelected());
		} else {
			JOptionPane.showMessageDialog(null, "Не все поля сообщения заполены", "ADBook", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * метод проверяет заполенены ли все поля сообщения
	 * 
	 * @return
	 */
	private boolean checkFieldMessage() {
		boolean status = true;
		if ((0 == this.listMessagesEditorTo.getModel().getSize())
				|| (0 == this.textFieldMessagesEditorSubject.getText().length())
				|| (0 == this.textAreaMessagesEditor.getText().length())) {
			status = false;
		}

		return status;
	}

	/**
	 * фильтрация только цифр
	 * 
	 * @param e
	 * @return
	 */
	private boolean filterOnlyDigit(KeyEvent e) {
		if (Character.isDigit(e.getKeyChar())) {
			return false;
		}
		return true;
	}

	/**
	 * фильтрация только букв
	 * 
	 * @param e
	 * @return
	 */
	private boolean filterOnlyAlphabetic(KeyEvent e) {
		if (Character.isAlphabetic(e.getKeyChar())) {
			return false;
		}
		return true;
	}

	/**
	 * фильтрация только space
	 * 
	 * @param e
	 * @return
	 */
	private boolean filterOnlySpace(KeyEvent e) {
		if (Character.isSpaceChar(e.getKeyChar())) {
			return false;
		}
		return true;
	}

	/**
	 * фильтрация только удаление
	 * 
	 * @param e
	 * @return
	 */
	private boolean filterOnlyErase(KeyEvent e) {
		if (127 == e.getKeyCode() || 8 == e.getKeyCode()) {
			return false;
		}
		return true;
	}

	private boolean filterCtrlV(KeyEvent e) {
		boolean ctrlPressed = false;
		boolean cPressed = false;
		switch (e.getKeyCode()) {
		case KeyEvent.VK_V:
			System.out.println("cPressed");
			cPressed = true;
			break;
		case KeyEvent.VK_CONTROL:
			ctrlPressed = true;
			System.out.println("ctrlPressed");
			break;
		}

		return (ctrlPressed && cPressed);
	}

	/**
	 * запуск поиска по фильтрам
	 */
	private void search() {
		core.localSearch(textFieldLastName.getText(), textFieldFirstName.getText(), textFieldMiddleName.getText(),
				(CompanyDto) comboBoxCompany.getSelectedItem(), (CompanyDto) comboBoxFilial.getSelectedItem(),
				textFieldDepartment.getText(), textFieldPhone.getText(), textFieldPesonPosition.getText(),
				textFieldRoom.getText());

	}
}
