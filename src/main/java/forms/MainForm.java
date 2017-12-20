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
import entity.UserDto;
import entity.UserNode;
import libs.Core;
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

import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

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

public class MainForm {

	private static final boolean isWEB = true;
	private static final String VERSION = "19.12.17v4";
	private static final String NAME = "ADBOOK";
	private static final String NAME_FORM = "Адресная книга";
	private static final Dimension DEMENSION_TREE = new Dimension(380, 50);
	private static final Dimension DEMENSION_IMAGE = new Dimension(250, 250);
	private static final Dimension DEMENSION_ICON_MENU = new Dimension(20, 20);
	private static final boolean IS_LOADER_CLASS_PATH = false;
	private static final String IS_CONTACT_RU = "Контакт в кириллице";
	private static final String IS_CONTACT_EN = "Контакт в транслите";
	private static final String FOLDER_IMAGE = "images";
	private static final String EMPTY_IMAGE = "/empty.png";
	private static final String USERS_IMAGE = "/iphone/Users.png";
	private static final String DOWNLOADS_IMAGE = "/iphone/Transmission.png";
	private static final String COPY_EMAILS_IMAGE = "/iphone/Mail.png";
	private static final String SAVE_XLS_IMAGE = "/iphone/Excel.png";
//	private static final String MESSAGES_IMAGE = "/iphone/Messages.png";
	private static final String LOGO_IMAGE = "/logo.png";
	private static final String PRELOAD_IMAGE = "/ajax-loader.gif";
	private static final String PLANS_IMAGE = "/plans/";

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
	private JLabel labelContactWriPhoneInside;
	private JLabel labelContactWriPhone;
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

	private Core core;

	private boolean isLockRenderTree = false;
	private boolean isLockRenderComboBox = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frmHandbook.setVisible(true);
					if (!isWEB) {
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
		core = new Core();
		if (!isWEB) {
			if (!core.sendServerSocket()) {
				core.runServerSocket();
				createForm();
			} else {
				System.exit(0);
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
	}

	private void addWindowListener() {
		if (!isWEB) {
			frmHandbook.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					Object[] options = { "Свернуть", "Закрыть" };
					int n = JOptionPane.showOptionDialog(frmHandbook, "", "", JOptionPane.YES_NO_OPTION,
							JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

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

	private UserDto getFirstUser(ArrayList<CompanyDto> companys) {
		UserDto search = null;
		for (CompanyDto company : companys) {
			if (!company.getFilials().isEmpty()) {
				search = getFirstUser(company.getFilials());
				if (null != search) {
					return search;
				}
			} else {
				if (!company.getUsers().isEmpty()) {
					for (UserDto user : company.getUsersSortedByCn().values()) {
						return user;
					}
				}
			}
		}
		return null;
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
		if (isWEB) {
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

		JPanel panelPerson = new JPanel();
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
		tools.setPreferredSize(new Dimension(20, 30));
		tools.setMinimumSize(new Dimension(20, 30));
		tools.setMaximumSize(new Dimension(20, 30));
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
		clearMessagesAttachment();
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

		labelContactWriPhoneInside = new JLabel();
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactWriPhoneInside, 5, SpringLayout.EAST,
				labelContactMobilePhone);
		sl_panelContact.putConstraint(SpringLayout.SOUTH, labelContactWriPhoneInside, 0, SpringLayout.SOUTH,
				labelContactPhoneInside);
		sl_panelContact.putConstraint(SpringLayout.EAST, labelContactWriPhoneInside, 0, SpringLayout.EAST, panel);
		panel.add(labelContactWriPhoneInside);

		labelContactWriPhone = new JLabel();
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactWriPhone, 5, SpringLayout.EAST,
				labelContactMobilePhone);
		sl_panelContact.putConstraint(SpringLayout.SOUTH, labelContactWriPhone, 0, SpringLayout.SOUTH,
				labelContactPhone);
		sl_panelContact.putConstraint(SpringLayout.EAST, labelContactWriPhone, 0, SpringLayout.EAST, panel);
		panel.add(labelContactWriPhone);

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
		sl_panelContact.putConstraint(SpringLayout.EAST, labelContactWriMail, 0, SpringLayout.EAST, panel);
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

		Dimension demension = new Dimension(70, 20);
		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setPreferredSize(demension);
		lblLogin.setMinimumSize(demension);
		lblLogin.setMaximumSize(demension);
		sl_panelAuth.putConstraint(SpringLayout.NORTH, lblLogin, 10, SpringLayout.NORTH, panelAuth);
		sl_panelAuth.putConstraint(SpringLayout.WEST, lblLogin, 10, SpringLayout.WEST, panelAuth);
		panelAuth.add(lblLogin);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setPreferredSize(demension);
		lblPassword.setMinimumSize(demension);
		lblPassword.setMaximumSize(demension);
		sl_panelAuth.putConstraint(SpringLayout.NORTH, lblPassword, 30, SpringLayout.NORTH, lblLogin);
		sl_panelAuth.putConstraint(SpringLayout.WEST, lblPassword, 10, SpringLayout.WEST, panelAuth);
		panelAuth.add(lblPassword);

		demension = new Dimension(100, 20);
		textFieldLogin = new JTextField();
		textFieldLogin.setEnabled(false);
		sl_panelAuth.putConstraint(SpringLayout.NORTH, textFieldLogin, 10, SpringLayout.NORTH, panelAuth);
		sl_panelAuth.putConstraint(SpringLayout.WEST, textFieldLogin, 0, SpringLayout.EAST, lblLogin);
		textFieldLogin.setPreferredSize(demension);
		textFieldLogin.setMinimumSize(demension);
		textFieldLogin.setMaximumSize(demension);
		panelAuth.add(textFieldLogin);

		passwordField = new JPasswordField();
		sl_panelAuth.putConstraint(SpringLayout.NORTH, passwordField, 30, SpringLayout.NORTH, textFieldLogin);
		sl_panelAuth.putConstraint(SpringLayout.WEST, passwordField, 0, SpringLayout.EAST, lblPassword);
		passwordField.setPreferredSize(demension);
		passwordField.setMinimumSize(demension);
		passwordField.setMaximumSize(demension);
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

		listMessagesEditorTo = new JList();
		listMessagesEditorTo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPaneMessagesEditorTo = new JScrollPane(listMessagesEditorTo);
		scrollPaneMessagesEditorTo.setPreferredSize(new Dimension(262, 22));
		scrollPaneMessagesEditorTo.setMinimumSize(new Dimension(262, 22));
		scrollPaneMessagesEditorTo.setMaximumSize(new Dimension(262, 22));
		sl_panelMessagesEditor.putConstraint(SpringLayout.NORTH, scrollPaneMessagesEditorTo, 0, SpringLayout.NORTH,
				labelMessagesEditorTo);
		sl_panelMessagesEditor.putConstraint(SpringLayout.WEST, scrollPaneMessagesEditorTo, 10, SpringLayout.EAST,
				labelMessagesEditorSubject);
		sl_panelMessagesEditor.putConstraint(SpringLayout.EAST, scrollPaneMessagesEditorTo, -10, SpringLayout.EAST,
				panelMessagesEditor);
		panelMessagesEditor.add(scrollPaneMessagesEditorTo);

		textAreaMessagesEditor = new JTextArea();
		textAreaMessagesEditor.setWrapStyleWord(true);
		sl_panelMessagesEditor.putConstraint(SpringLayout.NORTH, textAreaMessagesEditor, 30, SpringLayout.NORTH,
				labelMessagesEditorSubject);
		sl_panelMessagesEditor.putConstraint(SpringLayout.WEST, textAreaMessagesEditor, 10, SpringLayout.WEST,
				panelMessagesEditor);
		sl_panelMessagesEditor.putConstraint(SpringLayout.SOUTH, textAreaMessagesEditor, -144, SpringLayout.SOUTH,
				panelMessagesEditor);
		sl_panelMessagesEditor.putConstraint(SpringLayout.EAST, textAreaMessagesEditor, -10, SpringLayout.EAST,
				panelMessagesEditor);
		panelMessagesEditor.add(textAreaMessagesEditor);

		buttonMessagesEditorAttachment = new JButton("Добавить вложение");
		sl_panelMessagesEditor.putConstraint(SpringLayout.NORTH, buttonMessagesEditorAttachment, 10, SpringLayout.SOUTH,
				textAreaMessagesEditor);
		sl_panelMessagesEditor.putConstraint(SpringLayout.WEST, buttonMessagesEditorAttachment, 10, SpringLayout.WEST,
				panelMessagesEditor);
		panelMessagesEditor.add(buttonMessagesEditorAttachment);
		//
		JScrollPane paneMessagesEditorAttachment = new JScrollPane();
		sl_panelMessagesEditor.putConstraint(SpringLayout.NORTH, paneMessagesEditorAttachment, 10, SpringLayout.SOUTH,
				textAreaMessagesEditor);
		sl_panelMessagesEditor.putConstraint(SpringLayout.WEST, paneMessagesEditorAttachment, 10, SpringLayout.EAST,
				buttonMessagesEditorAttachment);
		sl_panelMessagesEditor.putConstraint(SpringLayout.SOUTH, paneMessagesEditorAttachment, -10, SpringLayout.SOUTH,
				panelMessagesEditor);
		sl_panelMessagesEditor.putConstraint(SpringLayout.EAST, paneMessagesEditorAttachment, -10, SpringLayout.EAST,
				panelMessagesEditor);
		panelMessagesEditor.add(paneMessagesEditorAttachment);

		listMessagesEditorAttachment = new JList();
		paneMessagesEditorAttachment.setViewportView(listMessagesEditorAttachment);

		buttonMessagesEditorAttachmentClear = new JButton("Очистить вложения");
		sl_panelMessagesEditor.putConstraint(SpringLayout.NORTH, buttonMessagesEditorAttachmentClear, 10,
				SpringLayout.SOUTH, buttonMessagesEditorAttachment);
		sl_panelMessagesEditor.putConstraint(SpringLayout.WEST, buttonMessagesEditorAttachmentClear, 0,
				SpringLayout.WEST, labelMessagesEditorFrom);
		panelMessagesEditor.add(buttonMessagesEditorAttachmentClear);
	}

	private void createTabPerson(JPanel panel) {
		SpringLayout sl_panelPerson = new SpringLayout();
		panel.setLayout(sl_panelPerson);

		JPanel panelQrCode = new JPanel();
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

		JLabel labelPersonFio = new JLabel("ФИО:");
		sl_panelPerson.putConstraint(SpringLayout.NORTH, labelPersonFio, 33, SpringLayout.SOUTH, panelQrCode);
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonFio, 0, SpringLayout.WEST, panelQrCode);
		panel.add(labelPersonFio);

		JLabel labelPersonCompany = new JLabel("Компания:");
		sl_panelPerson.putConstraint(SpringLayout.NORTH, labelPersonCompany, 6, SpringLayout.SOUTH, labelPersonFio);
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonCompany, 0, SpringLayout.WEST, panelQrCode);
		panel.add(labelPersonCompany);

		JLabel labelPersonDepartment = new JLabel("Отдел:");
		sl_panelPerson.putConstraint(SpringLayout.NORTH, labelPersonDepartment, 6, SpringLayout.SOUTH,
				labelPersonCompany);
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonDepartment, 0, SpringLayout.WEST, panelQrCode);
		panel.add(labelPersonDepartment);

		JLabel labelPersonPosition = new JLabel("Должность:");
		sl_panelPerson.putConstraint(SpringLayout.NORTH, labelPersonPosition, 6, SpringLayout.SOUTH,
				labelPersonDepartment);
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonPosition, 0, SpringLayout.WEST, panelQrCode);
		panel.add(labelPersonPosition);

		JLabel labelPersonMail = new JLabel("Электронная почта:");
		sl_panelPerson.putConstraint(SpringLayout.NORTH, labelPersonMail, 6, SpringLayout.SOUTH, labelPersonPosition);
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonMail, 0, SpringLayout.WEST, panelQrCode);
		panel.add(labelPersonMail);

		JLabel labelPersonPhoneInside = new JLabel("Телефон:");
		sl_panelPerson.putConstraint(SpringLayout.NORTH, labelPersonPhoneInside, 6, SpringLayout.SOUTH,
				labelPersonMail);
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonPhoneInside, 0, SpringLayout.WEST, panelQrCode);
		panel.add(labelPersonPhoneInside);

		JLabel labelPersonPhoneSmall = new JLabel("Телефон (внутр.):");
		sl_panelPerson.putConstraint(SpringLayout.NORTH, labelPersonPhoneSmall, 6, SpringLayout.SOUTH,
				labelPersonPhoneInside);
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonPhoneSmall, 0, SpringLayout.WEST, panelQrCode);
		panel.add(labelPersonPhoneSmall);

		JLabel labelPersonRoom = new JLabel("Комната:");
		sl_panelPerson.putConstraint(SpringLayout.NORTH, labelPersonRoom, 6, SpringLayout.SOUTH, labelPersonPhoneSmall);
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonRoom, 0, SpringLayout.WEST, panelQrCode);
		panel.add(labelPersonRoom);

		labelPersonHead = new JLabel("Руководитель:");
		sl_panelPerson.putConstraint(SpringLayout.NORTH, labelPersonHead, 6, SpringLayout.SOUTH, labelPersonRoom);
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonHead, 0, SpringLayout.WEST, panelQrCode);
		panelQrCode.setLayout(new BorderLayout(0, 0));
		panel.add(labelPersonHead);

		labelPersonWriDescription = new JLabel();
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonWriDescription, 5, SpringLayout.EAST,
				labelPersonMail);
		sl_panelPerson.putConstraint(SpringLayout.SOUTH, labelPersonWriDescription, 0, SpringLayout.SOUTH,
				labelPersonPosition);
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
		sl_panelPerson.putConstraint(SpringLayout.EAST, labelPersonWriMail, 0, SpringLayout.EAST, panel);
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
		TitledBorder borderFSM = new TitledBorder("Поиск по адресной книге");
		borderFSM.setTitlePosition(TitledBorder.TOP);
		panelFSM.setBorder(borderFSM);
		panelSearch.add(panelFSM);

		textFieldLastName = new JTextField("");
		textFieldLastName.setToolTipText("Фамилия");
		textFieldLastName.setHorizontalAlignment(SwingConstants.LEFT);
		JLabel labelSurName = new JLabel(textFieldLastName.getToolTipText() + ':');
		layoutFSM.putConstraint(SpringLayout.WEST, labelSurName, 5, SpringLayout.WEST, panelFSM);
		labelSurName.setMinimumSize(new Dimension(50, 0));

		textFieldFirstName = new JTextField();
		textFieldFirstName.setToolTipText("Имя");
		textFieldFirstName.setHorizontalAlignment(SwingConstants.LEFT);
		JLabel labelFirstName = new JLabel(textFieldFirstName.getToolTipText() + ':');
		labelFirstName.setMinimumSize(new Dimension(50, 0));

		textFieldMiddleName = new JTextField();
		textFieldMiddleName.setToolTipText("Отчество");
		textFieldMiddleName.setHorizontalAlignment(SwingConstants.LEFT);
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
		JLabel labelDepartment = new JLabel(textFieldDepartment.getToolTipText() + ':');
		labelDepartment.setMinimumSize(new Dimension(50, 0));

		textFieldPhone = new JTextField();
		textFieldPhone.setToolTipText("Номер телефона");
		textFieldPhone.setHorizontalAlignment(SwingConstants.LEFT);
		JLabel labelPhone = new JLabel(textFieldPhone.getToolTipText() + ':');
		labelPhone.setMinimumSize(new Dimension(50, 0));

		textFieldPesonPosition = new JTextField();
		textFieldPesonPosition.setToolTipText("Должность");
		textFieldPesonPosition.setHorizontalAlignment(SwingConstants.LEFT);
		JLabel labelPesonPosition = new JLabel(textFieldPesonPosition.getToolTipText() + ':');
		labelPesonPosition.setMinimumSize(new Dimension(50, 0));

		textFieldRoom = new JTextField();
		textFieldRoom.setToolTipText("Комната");
		textFieldRoom.setHorizontalAlignment(SwingConstants.LEFT);
		JLabel labelRoom = new JLabel(textFieldRoom.getToolTipText() + ':');
		textFieldRoom.setMinimumSize(new Dimension(50, 0));

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

		// rows, cols, initX, initY
		SpringUtilities.makeCompactGrid(panelFSM, 9, 2, 6, 6, 20, 6);
	}

	private ImageIcon resizeIcon(ImageIcon image, JLabel label) {
		return new ImageIcon(image.getImage().getScaledInstance(label.getWidth(), label.getHeight(),
				image.getImage().SCALE_DEFAULT));
	}

	private void updatePanelView() {
		if (core.getUser() instanceof UserDto) {
			String mail = new String("<html><a href=\"mailto:" + core.getUser().getMail() + "\">"
					+ core.getUser().getMail() + "</a></html>");
			String phone = new String(core.getUser().getOtherTelephone()
					+ ((0 == core.getUser().getOtherTelephone().length()) ? "" : "p*")
					+ core.getUser().getTelephoneNumber());

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
			// core.getUserDependency(user);
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
			labelContactWriPhoneInside.setText(phone);
			labelContactWriPhone.setText(core.getUser().getHomePhone());
			labelContactWriMobilePhone.setText(core.getUser().getMobile());
			labelContactWriMail.setText(mail);
			labelContactWriMail.setToolTipText(core.getUser().getMail());

			labelRoomPic.setIcon(resizeIcon((null != core.getUser().getPhysicalDeliveryOfficeName())
					? getResourceImage(PLANS_IMAGE + core.getUser().getPhysicalDeliveryOfficeName() + ".jpg")
					: getResourceImage(USERS_IMAGE), labelRoomPic));

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
				if (filterOnlyAlphabetic(e)) {
					e.consume();
				}
			}
		});
		textFieldMiddleName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (filterOnlyAlphabetic(e)) {
					e.consume();
				}
			}
		});
		textFieldFirstName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (filterOnlyAlphabetic(e)) {
					e.consume();
				}
			}
		});
		textFieldPhone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (filterOnlyDigit(e)) {
					e.consume();
				}
			}
		});
		textFieldDepartment.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (filterOnlyAlphabetic(e) & filterOnlySpace(e)) {
					e.consume();
				}
			}
		});

		textFieldPesonPosition.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (filterOnlyAlphabetic(e) & filterOnlySpace(e)) {
					e.consume();
				}
			}
		});

		textFieldLastName.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				search();
			}
		});

		textFieldMiddleName.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				search();
			}
		});

		textFieldFirstName.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				search();
			}
		});

		textFieldPhone.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				search();
			}
		});

		textFieldDepartment.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				search();
			}
		});

		textFieldPesonPosition.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				search();
			}
		});

		textFieldRoom.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				search();
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

		labelUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addTreePreloader();
				core.loadData(false);
				labelUpdate.setBorder(null);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				labelUpdate.setBorder(new LineBorder(new Color(214, 217, 223)));
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
				labelCopyMails.setBorder(new LineBorder(new Color(214, 217, 223)));
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
				labelSaveXls.setBorder(new LineBorder(new Color(214, 217, 223)));
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
				Desktop desktop = Desktop.getDesktop();
				try {
					desktop.browse(new URI("mailto:" + labelContactWriMail.getToolTipText()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				desktop = null;
			}
		});
		labelPersonWriMail.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Desktop desktop = Desktop.getDesktop();
				try {
					desktop.browse(new URI("mailto:" + labelPersonWriMail.getToolTipText()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				desktop = null;
			}
		});
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
				for (UserDto user : users.values()) {
					if (core.isMailValid(user.getMail())) {
						listModel.addElement(user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName()
								+ " <" + user.getMail() + ">");
					}
				}
				this.listMessagesEditorTo.setModel(listModel);
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
					textAreaMessagesEditor.getText(), textFieldLogin.getText(), passwordField.getText());
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
	 * фильтрация только цыфр
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
	 * запуск поиска по фильтрам
	 */
	private void search() {
		core.localSearch(textFieldLastName.getText(), textFieldFirstName.getText(), textFieldMiddleName.getText(),
				(CompanyDto) comboBoxCompany.getSelectedItem(), (CompanyDto) comboBoxFilial.getSelectedItem(),
				textFieldDepartment.getText(), textFieldPhone.getText(), textFieldPesonPosition.getText(),
				textFieldRoom.getText());

	}
}
