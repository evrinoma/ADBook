package forms;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
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
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import entity.CompanyDto;
import entity.UserDto;
import libs.Core;

import java.awt.CardLayout;
import java.awt.Toolkit;

import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTabbedPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Enumeration;
import java.util.List;

import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class MainForm {

	private static final Dimension DEMENSION_TREE = new Dimension(380, 50);
	private static final Dimension DEMENSION_IMAGE = new Dimension(250, 250);
	private static final String EMPTY_IMAGE = "/opt/DISK/Develop/Java/Eclipse/EEProjects/browser/src/main/resources/images/empty.png";
	private static final String LOGO_IMAGE = "/opt/DISK/Develop/Java/Eclipse/EEProjects/browser/src/main/resources/images/logo.gif";

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

	private JFrame frmHandbook;
	private JTextField textFieldLastName;
	private JTextField textFieldFirstName;
	private JTextField textFieldMiddleName;
	private JComboBox<CompanyDto> comboBoxCompany;
	private JTextField textFieldPhone;
	private JTextField textFieldDescription;
	private JLabel labelStatusBar;
	private JTree tree;
	private Core core;

	DefaultMutableTreeNode top;
	private JPanel panelView;
	private JPanel panelContact;
	private SpringLayout sl_panelPerson;
	private JLabel labelPersonHead;
	private JPanel panelQrCode;
	private JPanel panelPerson;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frmHandbook.setVisible(true);
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
		componentsInitialize();
		core.setMainForm(this);
		core.ldapSearch();
	}

	public void setStatusBar(String status) {
		labelStatusBar.setText(status);
	}

	public void setCompanySelector() {
		for (CompanyDto company : core.getCompanys().all()) {
			comboBoxCompany.addItem(company);
		}
	}

	public void setTreeNode(List<CompanyDto> companys,boolean isInit) {
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
		for (UserDto user : company.getUsers()) {
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

	/**
	 * Initialize the contents of the frame.
	 */
	private void componentsInitialize() {
		frmHandbook = new JFrame();
		frmHandbook.setIconImage(Toolkit.getDefaultToolkit().getImage(LOGO_IMAGE));
		frmHandbook.setTitle("HandBook");
		frmHandbook.setBounds(100, 100, 952, 768);
		frmHandbook.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHandbook.getContentPane().setLayout(new BorderLayout(0, 0));

		JSplitPane splitPaneHorz = new JSplitPane();
		splitPaneHorz.setOrientation(JSplitPane.VERTICAL_SPLIT);
		frmHandbook.getContentPane().add(splitPaneHorz);

		JSplitPane splitPaneVert = new JSplitPane();
		splitPaneVert.setMinimumSize(DEMENSION_TREE);
		splitPaneVert.setPreferredSize(DEMENSION_TREE);
		splitPaneHorz.setRightComponent(splitPaneVert);

		JPanel panelTree = new JPanel();
		panelTree.setPreferredSize(DEMENSION_TREE);
		panelTree.setMinimumSize(DEMENSION_TREE);
		panelTree.setLayout(new CardLayout(0, 0));
		splitPaneVert.setLeftComponent(panelTree);

		JPanel panelSearch = new JPanel();
		panelSearch.setLayout(new BorderLayout(0, 0));
		splitPaneHorz.setLeftComponent(panelSearch);
		createPanelFSM(panelSearch);

		panelView = new JPanel();
		splitPaneVert.setRightComponent(panelView);
		panelView.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panelView.add(tabbedPane);

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

		// tabbedPane.addTab("Сотрудники", panelPhoto);

		// SpringUtilities.makeCompactGrid(panelView, 1, 2, 6, 6, 20, 6);

		top = new DefaultMutableTreeNode("Сотрудники");

		tree = new JTree(top);
		JScrollPane treeView = new JScrollPane(tree);
		panelTree.add(treeView);

		createPanelStatus();

		addListners();
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
		labelPersonPic.setIcon(resizeIcon(new ImageIcon(EMPTY_IMAGE), labelPersonPic));
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
		JPanel panelFSM = new JPanel(layoutFSM);
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

		comboBoxCompany = new JComboBox();
		comboBoxCompany.addItem(new CompanyDto());
		comboBoxCompany.setToolTipText("Комания");
		JLabel labelCompany = new JLabel(comboBoxCompany.getToolTipText() + ':');
		labelCompany.setMinimumSize(new Dimension(50, 0));

		textFieldPhone = new JTextField();
		textFieldPhone.setToolTipText("Номер телефона");
		textFieldPhone.setHorizontalAlignment(SwingConstants.LEFT);
		JLabel labelPhone = new JLabel(textFieldPhone.getToolTipText() + ':');
		labelPhone.setMinimumSize(new Dimension(50, 0));

		textFieldDescription = new JTextField();
		textFieldDescription.setToolTipText("Должность");
		textFieldDescription.setHorizontalAlignment(SwingConstants.LEFT);
		JLabel labelDescription = new JLabel(textFieldDescription.getToolTipText() + ':');
		labelDescription.setMinimumSize(new Dimension(50, 0));

		panelFSM.add(labelSurName);
		panelFSM.add(textFieldLastName);
		panelFSM.add(labelFirstName);
		panelFSM.add(textFieldFirstName);
		panelFSM.add(labelMiddleName);
		panelFSM.add(textFieldMiddleName);
		panelFSM.add(labelCompany);
		panelFSM.add(comboBoxCompany);
		panelFSM.add(labelPhone);
		panelFSM.add(textFieldPhone);
		panelFSM.add(labelDescription);
		panelFSM.add(textFieldDescription);

		// rows, cols, initX, initY
		SpringUtilities.makeCompactGrid(panelFSM, 6, 2, 6, 6, 20, 6);
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
		labelPersonWriHead.setText(user.getManager());
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

		labelPersonPic.setIcon(resizeIcon(
				(null != user.getJpegPhoto()) ? new ImageIcon(user.getJpegPhoto()) : new ImageIcon(EMPTY_IMAGE),
				labelPersonPic));
		labelPersonQrCode.setIcon(
				core.createQrCode(user.getVCard(), labelPersonQrCode.getWidth(), labelPersonQrCode.getHeight()));
	}

	private void addListners() {
		// TODO Auto-generated method stub
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				/* if nothing is selected */
				if (node == null)
					return;
				Object selectedValue = node.getUserObject();
				if (selectedValue instanceof UserDto) {
					updatePanelView((UserDto) node.getUserObject());
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
		textFieldDescription.addKeyListener(new KeyAdapter() {
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

		textFieldDescription.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				search();
			}
		});

		comboBoxCompany.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				search();
			}
		});
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
		String phone = textFieldPhone.getText();
		String description = textFieldDescription.getText();
		core.localSearch(lastName, firstName, middleName, company, phone, description);
	}

}
