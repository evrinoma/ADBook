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

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import entity.CompanyDto;
import entity.LevelNode;
import entity.UserDto;
import libs.Core;

import java.awt.CardLayout;
import java.awt.TrayIcon;

import javax.swing.border.EtchedBorder;
import java.awt.Component;

import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;

import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
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
import javax.swing.JEditorPane;
import java.awt.TextArea;
import javax.swing.JList;

public class MainForm {

	private static final String VERSION = "05.12.17v1";
	private static final String NAME = "ADBOOK";
	private static final Dimension DEMENSION_TREE = new Dimension(380, 50);
	private static final Dimension DEMENSION_IMAGE = new Dimension(250, 250);
	private static final Dimension DEMENSION_ICON_MENU = new Dimension(20, 20);
	private static final String EMPTY_IMAGE = "/images/empty.png";
	private static final String USERS_IMAGE = "/images/iphone/Users.png";// "/images/humans.png";
																			// //"/opt/DISK/Develop/Java/Eclipse/EEProjects/browser/src/main/resources/images/empty.png";
	private static final String DOWNLOADS_IMAGE = "/images/iphone/Transmission.png";
	private static final String COPY_EMAILS_IMAGE = "/images/iphone/Mail.png";
	private static final String SAVE_XLS_IMAGE = "/images/iphone/Excel.png";
	private static final String MESSAGES_IMAGE = "/images/iphone/Messages.png";
	private static final String LOGO_IMAGE = "/images/logo.png";
	private static final String PRELOAD_IMAGE = "/images/ajax-loader.gif";
	private static final String PLANS_IMAGE = "/images/plans/";

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

	private JLabel labelStatusBar;
	private JTree tree;
	private JScrollPane treeView;

	private JPanel panelMessages;
	private JPanel panelMessagesEditor;
	private JPanel panelAuth;
	private JTextField textFieldLogin;
	private JPasswordField passwordField;
	private JLabel lblLogin;
	private JLabel lblPassword;
	private JButton buttonAuth;

	DefaultMutableTreeNode top;
	private JPanel panelView;
	private JPanel panelTree;
	private JPanel panelContact;
	private JPanel panelRoom;
	// private SpringLayout sl_panelPerson;
	private JLabel labelPersonHead;
	private JPanel panelQrCode;
	private JPanel panelPerson;
	private JPanel panelDepend;
	private mxGraphComponent graphComponent;
	private JPanel tools;
	private JLabel labelUpdate;
	private JLabel labelCopyMails;
	private JLabel labelSaveXls;

	private Core core;
	private JLabel labelMessagesEditorFrom;
	private JLabel labelMessagesEditorTo;
	private JLabel labelMessagesEditorSubject;
	private JTextArea textFieldMessagesEditorTo;
	private JTextField textFieldMessagesEditorSubject;
	private JLabel labelMessagesEditorWriFrom;
	private JButton buttonMessagesEditorSend;
	private JTextArea textAreaMessagesEditor;
	private JButton buttonMessagesEditorAttachment;
	private JButton buttonMessagesEditorClear;
	private JList listMessagesEditorAttachment;
	private JButton buttonMessagesEditorAttachmentClear;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frmHandbook.setVisible(true);
					window.frmHandbook.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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
		if (!core.isRunningProcess(NAME)) {
			createForm();
		} else {
			System.exit(0);
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
		frmHandbook.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				createTray();
			}
		});
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

	public void setTreeNode(List<CompanyDto> companys, boolean isInit) {
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		root.removeAllChildren();
		model.reload();

		for (CompanyDto company : companys) {
			DefaultMutableTreeNode nodes = createCompanyTreeNode(company);
			if (null != nodes) {
				addToTopCompanyTreeNode(createCompanyTreeNode(company));
			}
		}

		expandTree(isInit);
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

	private void addToCompanyUserTreeNode(DefaultMutableTreeNode top, CompanyDto company) {
		for (UserDto user : company.getUsersSortedByCn().values()) {
			top.add(new DefaultMutableTreeNode(user));
		}
	}

	private DefaultMutableTreeNode createCompanyTreeNode(CompanyDto company) {
		DefaultMutableTreeNode companyTreeNode = null;
		if (!company.getUsers().isEmpty()) {
			companyTreeNode = new DefaultMutableTreeNode(company);
			addToCompanyUserTreeNode(companyTreeNode, company);
		} else if (!company.getFilials().isEmpty()) {
			companyTreeNode = new DefaultMutableTreeNode(company);
			for (CompanyDto filial : company.getFilials()) {
				DefaultMutableTreeNode filialTreeNode = createCompanyTreeNode(filial);
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
		/*
		 * mxStylesheet stylesheet = graph.getStylesheet(); HashMap<String,
		 * Object> edgeStyle = new HashMap<String, Object>();
		 * edgeStyle.put(mxConstants.STYLE_EDGE,
		 * mxConstants.EDGESTYLE_ORTHOGONAL);
		 * edgeStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
		 * edgeStyle.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
		 * edgeStyle.put(mxConstants.STYLE_STROKECOLOR, "#000000");
		 * edgeStyle.put(mxConstants.STYLE_FONTCOLOR, "#000000");
		 * edgeStyle.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "#ffffff");
		 * edgeStyle.put(mxConstants.STYLE_FONTSIZE, 5);
		 * edgeStyle.put(mxConstants.STYLE_RESIZABLE, 0);
		 * stylesheet.setDefaultEdgeStyle(edgeStyle);
		 */
		/*
		 * HashMap<String, Object> vertexStyle = new HashMap<String, Object>();
		 * vertexStyle.put(mxConstants.STYLE_SHAPE,
		 * mxConstants.SHAPE_CONNECTOR);
		 * vertexStyle.put(mxConstants.STYLE_ENDARROW,
		 * mxConstants.ARROW_CLASSIC);
		 * vertexStyle.put(mxConstants.STYLE_FONTSIZE, 2);
		 * vertexStyle.put(mxConstants.STYLE_RESIZABLE, 0);
		 * stylesheet.setDefaultEdgeStyle(edgeStyle);
		 * stylesheet.setDefaultVertexStyle(vertexStyle);
		 */
		/*
		 * graph.setStylesheet(stylesheet);
		 */
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

		graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				Object cell = graphComponent.getCellAt(e.getX(), e.getY());

				if (cell != null) {
					// System.out.println("cell="+graph.getLabel(cell));
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @wbp.parser.entryPoint
	 */
	private void componentsInitialize() {
		frmHandbook = new JFrame();
		ImageIcon icon = getResourceImage(LOGO_IMAGE);
		frmHandbook.setIconImage(icon.getImage());
		frmHandbook.setTitle("HandBook");
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

		panelView = new JPanel();
		splitPaneVert.setRightComponent(panelView);
		panelView.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panelView.add(tabbedPane);

		panelMessages = new JPanel();
		panelMessages.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelMessages.setMinimumSize(new Dimension(10, 250));
		tabbedPane.addTab("Рассылка", panelMessages);
		createTabMessages();

		panelPerson = new JPanel();
		panelPerson.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelPerson.setMinimumSize(new Dimension(10, 250));
		tabbedPane.addTab("Персональная информация", panelPerson);
		createTabPerson(panelPerson);

		panelContact = new JPanel();
		panelContact.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelContact.setMinimumSize(new Dimension(10, 250));
		tabbedPane.addTab("Контакты", panelContact);
		createTabContact(panelContact);

		panelRoom = new JPanel();
		panelRoom.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		tabbedPane.addTab("Схема", panelRoom);
		createTabRoom(panelRoom);

		panelDepend = new JPanel();
		panelDepend.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelDepend.setMinimumSize(new Dimension(10, 250));
		tabbedPane.addTab("Подчинённые", panelDepend);
		panelDepend.setLayout(new CardLayout(0, 0));
		graphComponent = new mxGraphComponent(new mxGraph());
		graphComponent.setToolTips(true);
		panelDepend.add(graphComponent, "panelDepend");

		createPanelStatus();

		addListners();
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

		final TrayIcon trayIcon = new TrayIcon(image, "Контакты", trayPopupMenu);
		// кликаем по менюшке Развернуть
		MenuItem action = new MenuItem("Развернуть");
		action.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frmHandbook.setVisible(true);
				systemTray.remove(trayIcon);
			}
		});
		trayPopupMenu.add(action);

		// кликаем по менюшке Выход
		MenuItem close = new MenuItem("Выход");
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				core.removeRunningProcess();
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

		tools = new JPanel();
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
				entity.enable();
			}
		}
		panelFSM.repaint();
	}

	private void lockPanelFSM() {
		comboBoxCompany.setSelectedIndex(0);
		comboBoxFilial.setSelectedIndex(0);
		lockPanelFSM(true);
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

	public void addMessagePreloader() {
		if (null != panelAuth) {
			panelAuth.hide();
		}
		createPanelPreloader(panelMessages);
		core.authorizeOnMail(textFieldLogin.getText(), passwordField.getText());
	}

	public void removeMessagePreload() {
		removePreload(panelMessages);
	}

	public void showMessageEditorPanel() {
		this.labelMessagesEditorWriFrom.setText(core.getMailAthorizedUser());
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

	private void clearMessages() {
		this.textFieldMessagesEditorSubject.setText("");
		this.textFieldMessagesEditorTo.setText("");
		this.textAreaMessagesEditor.setText("");
		clearMessagesAttachment();
	}

	private void clearMessagesAttachment() {
		DefaultListModel listModel = new DefaultListModel();
		listModel.clear();
		this.listMessagesEditorAttachment.setModel(listModel);
		core.clearMailAttachmet();
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
		sl_panelContact.putConstraint(SpringLayout.NORTH, labelContactСountry, 10, SpringLayout.NORTH, panelContact);
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactСountry, 10, SpringLayout.WEST, panelContact);
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

		JLabel labelContactPhoneInside = new JLabel("Телефон (внутр.):");
		sl_panelContact.putConstraint(SpringLayout.NORTH, labelContactPhoneInside, 6, SpringLayout.SOUTH,
				labelContactRoom);
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactPhoneInside, 0, SpringLayout.WEST,
				labelContactСountry);
		panel.add(labelContactPhoneInside);

		JLabel labelContactPhone = new JLabel("Телефон:");
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
		sl_panelContact.putConstraint(SpringLayout.EAST, labelContactWriСountry, 0, SpringLayout.EAST, panelContact);
		panel.add(labelContactWriСountry);

		labelContactWriRegion = new JLabel();
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactWriRegion, 5, SpringLayout.EAST,
				labelContactMobilePhone);
		sl_panelContact.putConstraint(SpringLayout.SOUTH, labelContactWriRegion, 0, SpringLayout.SOUTH,
				labelContactRegion);
		sl_panelContact.putConstraint(SpringLayout.EAST, labelContactWriRegion, 0, SpringLayout.EAST, panelContact);
		panel.add(labelContactWriRegion);

		labelContactWriTown = new JLabel();
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactWriTown, 5, SpringLayout.EAST,
				labelContactMobilePhone);
		sl_panelContact.putConstraint(SpringLayout.SOUTH, labelContactWriTown, 0, SpringLayout.SOUTH, labelContactTown);
		sl_panelContact.putConstraint(SpringLayout.EAST, labelContactWriTown, 0, SpringLayout.EAST, panelContact);
		panel.add(labelContactWriTown);

		labelContactWritPostIndex = new JLabel();
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactWritPostIndex, 5, SpringLayout.EAST,
				labelContactMobilePhone);
		sl_panelContact.putConstraint(SpringLayout.SOUTH, labelContactWritPostIndex, 0, SpringLayout.SOUTH,
				labelContactPostIndex);
		sl_panelContact.putConstraint(SpringLayout.EAST, labelContactWritPostIndex, 0, SpringLayout.EAST, panelContact);
		panel.add(labelContactWritPostIndex);

		labelContactWriStreet = new JLabel();
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactWriStreet, 5, SpringLayout.EAST,
				labelContactMobilePhone);
		sl_panelContact.putConstraint(SpringLayout.SOUTH, labelContactWriStreet, 0, SpringLayout.SOUTH,
				labelContactStreet);
		sl_panelContact.putConstraint(SpringLayout.EAST, labelContactWriStreet, 0, SpringLayout.EAST, panelContact);
		panel.add(labelContactWriStreet);

		labelContactWriBirth = new JLabel();
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactWriBirth, 5, SpringLayout.EAST,
				labelContactMobilePhone);
		sl_panelContact.putConstraint(SpringLayout.SOUTH, labelContactWriBirth, 0, SpringLayout.SOUTH,
				labelContactBirth);
		sl_panelContact.putConstraint(SpringLayout.EAST, labelContactWriBirth, 0, SpringLayout.EAST, panelContact);
		panel.add(labelContactWriBirth);

		labelContactWriRoom = new JLabel();
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactWriRoom, 5, SpringLayout.EAST,
				labelContactMobilePhone);
		sl_panelContact.putConstraint(SpringLayout.SOUTH, labelContactWriRoom, 0, SpringLayout.SOUTH, labelContactRoom);
		sl_panelContact.putConstraint(SpringLayout.EAST, labelContactWriRoom, 0, SpringLayout.EAST, panelContact);
		panel.add(labelContactWriRoom);

		labelContactWriPhoneInside = new JLabel();
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactWriPhoneInside, 5, SpringLayout.EAST,
				labelContactMobilePhone);
		sl_panelContact.putConstraint(SpringLayout.SOUTH, labelContactWriPhoneInside, 0, SpringLayout.SOUTH,
				labelContactPhoneInside);
		sl_panelContact.putConstraint(SpringLayout.EAST, labelContactWriPhoneInside, 0, SpringLayout.EAST,
				panelContact);
		panel.add(labelContactWriPhoneInside);

		labelContactWriPhone = new JLabel();
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactWriPhone, 5, SpringLayout.EAST,
				labelContactMobilePhone);
		sl_panelContact.putConstraint(SpringLayout.SOUTH, labelContactWriPhone, 0, SpringLayout.SOUTH,
				labelContactPhone);
		sl_panelContact.putConstraint(SpringLayout.EAST, labelContactWriPhone, 0, SpringLayout.EAST, panelContact);
		panel.add(labelContactWriPhone);

		labelContactWriMobilePhone = new JLabel();
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactWriMobilePhone, 5, SpringLayout.EAST,
				labelContactMobilePhone);
		sl_panelContact.putConstraint(SpringLayout.SOUTH, labelContactWriMobilePhone, 0, SpringLayout.SOUTH,
				labelContactMobilePhone);
		sl_panelContact.putConstraint(SpringLayout.EAST, labelContactWriMobilePhone, 0, SpringLayout.EAST,
				panelContact);
		panel.add(labelContactWriMobilePhone);

		labelContactWriMail = new JLabel();
		sl_panelContact.putConstraint(SpringLayout.WEST, labelContactWriMail, 5, SpringLayout.EAST,
				labelContactMobilePhone);
		sl_panelContact.putConstraint(SpringLayout.SOUTH, labelContactWriMail, 0, SpringLayout.SOUTH, labelContactMail);
		sl_panelContact.putConstraint(SpringLayout.EAST, labelContactWriMail, 0, SpringLayout.EAST, panelContact);
		panel.add(labelContactWriMail);

	}

	private void createTabMessages() {
		panelMessages.setLayout(new CardLayout(0, 0));

		SpringLayout sl_panelAuth = new SpringLayout();
		panelAuth = new JPanel();
		panelAuth.setLayout(sl_panelAuth);
		panelMessages.add(panelAuth, "panelAuth");

		Dimension demension = new Dimension(70, 20);
		lblLogin = new JLabel("Login:");
		lblLogin.setPreferredSize(demension);
		lblLogin.setMinimumSize(demension);
		lblLogin.setMaximumSize(demension);
		sl_panelAuth.putConstraint(SpringLayout.NORTH, lblLogin, 10, SpringLayout.NORTH, panelAuth);
		sl_panelAuth.putConstraint(SpringLayout.WEST, lblLogin, 10, SpringLayout.WEST, panelAuth);
		panelAuth.add(lblLogin);

		lblPassword = new JLabel("Password:");
		lblPassword.setPreferredSize(demension);
		lblPassword.setMinimumSize(demension);
		lblPassword.setMaximumSize(demension);
		sl_panelAuth.putConstraint(SpringLayout.NORTH, lblPassword, 30, SpringLayout.NORTH, lblLogin);
		sl_panelAuth.putConstraint(SpringLayout.WEST, lblPassword, 10, SpringLayout.WEST, panelAuth);
		panelAuth.add(lblPassword);

		demension = new Dimension(100, 20);
		textFieldLogin = new JTextField();
		textFieldLogin.setText("nikolns@ite-ng.ru");
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

		labelMessagesEditorFrom = new JLabel("От:");
		sl_panelMessagesEditor.putConstraint(SpringLayout.NORTH, labelMessagesEditorFrom, 20, SpringLayout.NORTH,
				panelMessagesEditor);
		sl_panelMessagesEditor.putConstraint(SpringLayout.WEST, labelMessagesEditorFrom, 10, SpringLayout.WEST,
				panelMessagesEditor);
		panelMessagesEditor.add(labelMessagesEditorFrom);

		labelMessagesEditorTo = new JLabel("Кому:");
		sl_panelMessagesEditor.putConstraint(SpringLayout.NORTH, labelMessagesEditorTo, 30, SpringLayout.NORTH,
				labelMessagesEditorFrom);
		sl_panelMessagesEditor.putConstraint(SpringLayout.WEST, labelMessagesEditorTo, 10, SpringLayout.WEST,
				panelMessagesEditor);
		panelMessagesEditor.add(labelMessagesEditorTo);

		labelMessagesEditorSubject = new JLabel("Тема:");
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

		textFieldMessagesEditorTo = new JTextArea();
		textFieldMessagesEditorTo.setWrapStyleWord(true);
		textFieldMessagesEditorTo.setEditable(false);
		sl_panelMessagesEditor.putConstraint(SpringLayout.NORTH, textFieldMessagesEditorTo, 0, SpringLayout.NORTH,
				labelMessagesEditorTo);
		sl_panelMessagesEditor.putConstraint(SpringLayout.WEST, textFieldMessagesEditorTo, 10, SpringLayout.EAST,
				labelMessagesEditorSubject);
		sl_panelMessagesEditor.putConstraint(SpringLayout.EAST, textFieldMessagesEditorTo, -10, SpringLayout.EAST,
				panelMessagesEditor);
		panelMessagesEditor.add(textFieldMessagesEditorTo);

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

		JPanel panelMessagesEditorAttachment = new JPanel();
		sl_panelMessagesEditor.putConstraint(SpringLayout.NORTH, panelMessagesEditorAttachment, 10, SpringLayout.SOUTH,
				textAreaMessagesEditor);
		sl_panelMessagesEditor.putConstraint(SpringLayout.WEST, panelMessagesEditorAttachment, 10, SpringLayout.EAST,
				buttonMessagesEditorAttachment);
		sl_panelMessagesEditor.putConstraint(SpringLayout.SOUTH, panelMessagesEditorAttachment, -10, SpringLayout.SOUTH,
				panelMessagesEditor);
		sl_panelMessagesEditor.putConstraint(SpringLayout.EAST, panelMessagesEditorAttachment, -10, SpringLayout.EAST,
				panelMessagesEditor);
		panelMessagesEditor.add(panelMessagesEditorAttachment);
		panelMessagesEditorAttachment.setLayout(new CardLayout(0, 0));

		listMessagesEditorAttachment = new JList();
		panelMessagesEditorAttachment.add(listMessagesEditorAttachment);

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

		JLabel labelPersonFio = new JLabel("ФИО:");
		sl_panelPerson.putConstraint(SpringLayout.NORTH, labelPersonFio, 13, SpringLayout.SOUTH, panelQrCode);
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

		JLabel labelPersonPhoneInside = new JLabel("Телефон (внутр.):");
		sl_panelPerson.putConstraint(SpringLayout.NORTH, labelPersonPhoneInside, 6, SpringLayout.SOUTH,
				labelPersonMail);
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonPhoneInside, 0, SpringLayout.WEST, panelQrCode);
		panel.add(labelPersonPhoneInside);

		JLabel labelPersonRoom = new JLabel("Комната:");
		sl_panelPerson.putConstraint(SpringLayout.NORTH, labelPersonRoom, 6, SpringLayout.SOUTH,
				labelPersonPhoneInside);
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
		sl_panelPerson.putConstraint(SpringLayout.EAST, labelPersonWriDescription, 0, SpringLayout.EAST, panelPerson);
		panel.add(labelPersonWriDescription);

		labelPersonWriFio = new JLabel();
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonWriFio, 5, SpringLayout.EAST, labelPersonMail);
		sl_panelPerson.putConstraint(SpringLayout.SOUTH, labelPersonWriFio, 0, SpringLayout.SOUTH, labelPersonFio);
		sl_panelPerson.putConstraint(SpringLayout.EAST, labelPersonWriFio, 0, SpringLayout.EAST, panelPerson);
		panel.add(labelPersonWriFio);

		labelPersonWriCompany = new JLabel();
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonWriCompany, 5, SpringLayout.EAST, labelPersonMail);
		sl_panelPerson.putConstraint(SpringLayout.SOUTH, labelPersonWriCompany, 0, SpringLayout.SOUTH,
				labelPersonCompany);
		sl_panelPerson.putConstraint(SpringLayout.EAST, labelPersonWriCompany, 0, SpringLayout.EAST, panelPerson);
		panel.add(labelPersonWriCompany);

		labelPersonWriDepartment = new JLabel();
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonWriDepartment, 5, SpringLayout.EAST,
				labelPersonMail);
		sl_panelPerson.putConstraint(SpringLayout.SOUTH, labelPersonWriDepartment, 0, SpringLayout.SOUTH,
				labelPersonDepartment);
		sl_panelPerson.putConstraint(SpringLayout.EAST, labelPersonWriDepartment, 0, SpringLayout.EAST, panelPerson);
		panel.add(labelPersonWriDepartment);

		labelPersonWriRoom = new JLabel();
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonWriRoom, 5, SpringLayout.EAST, labelPersonMail);
		sl_panelPerson.putConstraint(SpringLayout.SOUTH, labelPersonWriRoom, 0, SpringLayout.SOUTH, labelPersonRoom);
		sl_panelPerson.putConstraint(SpringLayout.EAST, labelPersonWriRoom, 0, SpringLayout.EAST, panelPerson);
		panel.add(labelPersonWriRoom);

		labelPersonWriPhoneInside = new JLabel();
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonWriPhoneInside, 5, SpringLayout.EAST,
				labelPersonMail);
		sl_panelPerson.putConstraint(SpringLayout.SOUTH, labelPersonWriPhoneInside, 0, SpringLayout.SOUTH,
				labelPersonPhoneInside);
		sl_panelPerson.putConstraint(SpringLayout.EAST, labelPersonWriPhoneInside, 0, SpringLayout.EAST, panelPerson);
		panel.add(labelPersonWriPhoneInside);

		labelPersonWriMail = new JLabel();
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonWriMail, 5, SpringLayout.EAST, labelPersonMail);
		sl_panelPerson.putConstraint(SpringLayout.SOUTH, labelPersonWriMail, 0, SpringLayout.SOUTH, labelPersonMail);
		sl_panelPerson.putConstraint(SpringLayout.EAST, labelPersonWriMail, 0, SpringLayout.EAST, panelPerson);
		panel.add(labelPersonWriMail);

		labelPersonWriHead = new JLabel();
		sl_panelPerson.putConstraint(SpringLayout.WEST, labelPersonWriHead, 5, SpringLayout.EAST, labelPersonMail);
		sl_panelPerson.putConstraint(SpringLayout.SOUTH, labelPersonWriHead, 0, SpringLayout.SOUTH, labelPersonHead);
		sl_panelPerson.putConstraint(SpringLayout.EAST, labelPersonWriHead, 0, SpringLayout.EAST, panelPerson);
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

		// rows, cols, initX, initY
		SpringUtilities.makeCompactGrid(panelFSM, 8, 2, 6, 6, 20, 6);
	}

	private ImageIcon resizeIcon(ImageIcon image, JLabel label) {
		return new ImageIcon(image.getImage().getScaledInstance(label.getWidth(), label.getHeight(),
				image.getImage().SCALE_DEFAULT));
	}

	private void updatePanelView(UserDto user) {
		String mail = new String(user.getOtherTelephone() + ((0 == user.getOtherTelephone().length()) ? "" : "p*")
				+ user.getTelephoneNumber());

		labelPersonWriDescription.setText(user.getDescription());
		labelPersonWriFio.setText(user.getCn());
		labelPersonWriCompany.setText(user.getCompany());
		labelPersonWriDepartment.setText(user.getDepartment());

		if (!user.getManager().isEmpty()) {
			labelPersonHead.setVisible(true);
			labelPersonWriHead.setText(core.getUserManger(user));
		} else {
			labelPersonHead.setVisible(false);
			labelPersonWriHead.setText("");

		}
		// core.getUserDependency(user);
		showGraph(core.getUserDependency(user));

		labelPersonWriRoom.setText(user.getPhysicalDeliveryOfficeName());
		labelPersonWriPhoneInside.setText(mail);
		labelPersonWriMail.setText(user.getMail());

		labelContactWriСountry.setText(user.getCo());
		labelContactWriRegion.setText(user.getSt());
		labelContactWriTown.setText(user.getL());
		labelContactWritPostIndex.setText(user.getPostalCode());
		labelContactWriStreet.setText(user.getStreetAddress());
		labelContactWriBirth.setText(user.getInfo());
		labelContactWriRoom.setText(user.getPhysicalDeliveryOfficeName());
		labelContactWriPhoneInside.setText(mail);
		labelContactWriPhone.setText(user.getHomePhone());
		labelContactWriMobilePhone.setText(user.getMobile());
		labelContactWriMail.setText(user.getMail());

		labelRoomPic.setIcon(resizeIcon((null != user.getPhysicalDeliveryOfficeName())
				? getResourceImage(PLANS_IMAGE + user.getPhysicalDeliveryOfficeName() + ".jpg")
				: getResourceImage(USERS_IMAGE), labelRoomPic));

		labelPersonPic.setIcon(resizeIcon(
				(null != user.getJpegPhoto()) ? new ImageIcon(user.getJpegPhoto()) : getResourceImage(USERS_IMAGE),
				labelPersonPic));

		labelPersonQrCode.setIcon(core.createQrCodeWithLogo(getResourceFile(LOGO_IMAGE), user.getVCard(),
				labelPersonQrCode.getWidth(), labelPersonQrCode.getHeight()));
	}

	private URL getResourceFile(String nameFile) {
		return this.getClass().getResource(nameFile);
	}

	private ImageIcon getResourceImage(String nameFile) {
		URL url = getResourceFile(nameFile);
		return (null == url) ? new ImageIcon(getResourceFile(EMPTY_IMAGE)) : new ImageIcon(url);
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
				
				if (selectedValue instanceof CompanyDto) {
					String Dn = ((CompanyDto) selectedValue).getDn();
					String parentDn = ((CompanyDto) selectedValue).getParentDn(); 
					if (((CompanyDto) selectedValue).getFilials().size() != 0) {
						for (int selectedId : tree.getSelectionRows()) {
							TreePath treePath = tree.getPathForRow(selectedId);
							DefaultMutableTreeNode selectedItem = (DefaultMutableTreeNode) treePath
									.getLastPathComponent();
							if (null != selectedItem) {
								Object value = selectedItem.getUserObject();
								if (value instanceof CompanyDto) {
									if (((CompanyDto) value).getParentDn().equals(Dn)) {
										tree.getSelectionModel().removeSelectionPath(treePath);
									}
								} else if (value instanceof UserDto) {
									String userDn = ((UserDto) value).getCompanyDn();
									for (CompanyDto filial:(((CompanyDto) selectedValue).getFilials())) {									
										if (filial.getDn().equals(userDn)) {
											tree.getSelectionModel().removeSelectionPath(treePath);
											break;
										}
									}	
								}
							}
						}
					} else {						
						for (int selectedId : tree.getSelectionRows()) {
							TreePath treePath = tree.getPathForRow(selectedId);
							DefaultMutableTreeNode selectedItem = (DefaultMutableTreeNode) treePath
									.getLastPathComponent();
							if (null != selectedItem) {
								Object value = selectedItem.getUserObject();
								if (value instanceof UserDto) {
									if (((UserDto) value).getCompanyDn().equals(Dn)) {
										tree.getSelectionModel().removeSelectionPath(treePath);
									}
								} else if (value instanceof CompanyDto) {
									if (((CompanyDto) value).getDn().equals(parentDn)) {
										tree.getSelectionModel().removeSelectionPath(treePath);
									}
								}
							}
						}
						tree.getSelectionModel().addSelectionPath(eTree);
					}
				} else if (selectedValue instanceof UserDto) {
					String Dn = ((UserDto) selectedValue).getCompanyDn();
					for (int selectedId : tree.getSelectionRows()) {
						TreePath treePath = tree.getPathForRow(selectedId);
						DefaultMutableTreeNode selectedItem = (DefaultMutableTreeNode) treePath.getLastPathComponent();
						if (null != selectedItem) {
							Object value = selectedItem.getUserObject();
							if (value instanceof CompanyDto) {
								if ((((CompanyDto) value).getFilials().size() != 0)) {
									for (CompanyDto filial:(((CompanyDto) value).getFilials())) {										
										if (filial.getDn().equals(Dn)) {
											tree.getSelectionModel().removeSelectionPath(treePath);
											break;
										}
									}									
								} else if (((CompanyDto) value).getDn().equals(Dn)) {
									tree.getSelectionModel().removeSelectionPath(treePath);
								}
							}
						}
					}
					updatePanelView((UserDto) selectedValue);
				}

				getSelectedUsersMails();
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
				if (filterOnlyAlphabetic(e)) {
					e.consume();
				}
			}
		});

		textFieldPesonPosition.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (filterOnlyAlphabetic(e)) {
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

		comboBoxFilial.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				search();
			}
		});

		textFieldPesonPosition.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				search();
			}
		});

		comboBoxCompany.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
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
				search();
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
				core.putStringToClipboard(getSelectedUsersMails());
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
				addMessagePreloader();
			}
		});

		passwordField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addMessagePreloader();
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
	}

	private String getSelectedUsersMails() {
		String mails = "";
		TreePath[] paths = tree.getSelectionPaths();
		if (paths != null) {
			mails = core.toStringUserMails(getListUsersFromTreePath(paths));
			setMessagesEditorToMails(mails);
		}

		return mails;
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
				users.putAll(company.getUsers());
			}

		}
		return users;
	}

	private void setMessagesEditorToMails(String mails) {
		if (textFieldMessagesEditorTo.isVisible()) {
			textFieldMessagesEditorTo.setText(mails);
			textFieldMessagesEditorTo.setToolTipText(mails);
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

	private boolean filterOnlyAlphabetic(KeyEvent e) {
		if (Character.isAlphabetic(e.getKeyChar())) {
			return false;
		}
		return true;
	}

	private boolean filterOnlyDigit(KeyEvent e) {
		if (Character.isDigit(e.getKeyChar())) {
			return false;
		}
		return true;
	}

	private void search() {
		String lastName = textFieldLastName.getText();
		String firstName = textFieldFirstName.getText();
		String middleName = textFieldMiddleName.getText();
		CompanyDto company = (CompanyDto) comboBoxCompany.getSelectedItem();
		CompanyDto filial = (CompanyDto) comboBoxFilial.getSelectedItem();
		String department = textFieldDepartment.getText();
		String phone = textFieldPhone.getText();
		String pesonPosition = textFieldPesonPosition.getText();
		core.localSearch(lastName, firstName, middleName, company, filial, department, phone, pesonPosition);
	}
}


 



