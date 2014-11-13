package by.brashevets.main;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import by.brashevets.dao.DealDao;
import by.brashevets.entity.deal.Deal;


import by.brashevets.factory.Factory;

import javax.swing.UIManager;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

import java.awt.event.WindowFocusListener;
import java.awt.event.WindowEvent;

import javax.swing.ListSelectionModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.border.MatteBorder;

import java.awt.Color;

import com.toedter.calendar.JDateChooser;

import javax.swing.border.TitledBorder;
import java.awt.event.WindowAdapter;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static MainFrame mainFrame;
	private JPanel contentPane;
	JTable table;
	private NewEventFrame addEventFrame;
	private EditEventFrame editEventFrame;
	private Deal currentDeal;
	private JDateChooser dateChooser;
	private JButton btnEndDeal;
	private JButton btnAddDeal;
	private JButton btnViewDay;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					mainFrame = frame;
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
	public MainFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment( JLabel.CENTER );
				table.setDefaultRenderer(String.class, centerRenderer);
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				while (model.getRowCount()>0) {
				  model.removeRow(0);
				}
				
				Date date = dateChooser.getDate();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String dateInStringFormat = dateFormat.format(date);
				Factory factory = Factory.getInstance();
				DealDao dealDao = factory.getDealDao();
				try {
					for (Deal deal : dealDao.getDealByDate(dateInStringFormat)) {
						model.addRow(new Object[]{deal.getReadiness(), deal.getImportance(),
								deal.getDate(), deal.getNameOfDeal(), deal.getDescription()});
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		setTitle("\u0420\u0430\u0441\u043F\u043E\u0440\u044F\u0434\u043E\u043A \u0434\u043D\u044F");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 830, 555);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel imagePanel = new JPanel(){				
		@Override
         public void paintComponent(Graphics g) {  
              Image img = Toolkit.getDefaultToolkit().getImage(  
                        MainFrame.class.getResource("/images/font.png"));  
              g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);  
         }  
		};
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(imagePanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 637, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(imagePanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
		);
		
		JPanel panel = new JPanel();
		
		JPanel panelMenu = new JPanel();
		panelMenu.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		GroupLayout gl_imagePanel = new GroupLayout(imagePanel);
		gl_imagePanel.setHorizontalGroup(
			gl_imagePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_imagePanel.createSequentialGroup()
					.addGap(23)
					.addComponent(panelMenu, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE)
					.addGap(100)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(50, Short.MAX_VALUE))
		);
		gl_imagePanel.setVerticalGroup(
			gl_imagePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_imagePanel.createSequentialGroup()
					.addGap(39)
					.addGroup(gl_imagePanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(panelMenu, GroupLayout.PREFERRED_SIZE, 427, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 443, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(24, Short.MAX_VALUE))
		);
		
		btnAddDeal = new JButton("\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C \u0434\u0435\u043B\u043E");
		btnAddDeal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addEventFrame = new NewEventFrame(){
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void dispose(){
						mainFrame.setEnabled(true);
						super.dispose();
					}
				};
				addEventFrame.setVisible(true);
				addEventFrame.setCalendarFrame(mainFrame);
				mainFrame.setEnabled(false);
			}
		});
		
		JButton btnDeleteDeal = new JButton("\u0423\u0434\u0430\u043B\u0438\u0442\u044C \u0434\u0435\u043B\u043E");
		btnDeleteDeal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Factory factory = Factory.getInstance();
				DealDao dealDao = factory.getDealDao();
				try {
					dealDao.getDealByInfo("asdf","asdf");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btnEndDeal = new JButton("\u0417\u0430\u0432\u0435\u0440\u0448\u0438\u0442\u044C \u0434\u0435\u043B\u043E");
		btnEndDeal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					Deal selectedDeal = new Deal();
					int row = table.getSelectedRow();
					int column = table.getSelectedColumn();
					if(row > 0 && column >0){
						if(table.getValueAt(row, 0)!=null){
							String name = (String)table.getValueAt(row, 3);
							String description =(String)table.getValueAt(row, 4);
							Factory factory = Factory.getInstance();
							DealDao dealDao = factory.getDealDao();
							Deal deal = null;
							try {
								deal = dealDao.getDealByInfo(name, description);
								JOptionPane.showMessageDialog(null, deal.getNameOfDeal(),"You changed " + row + " row", 1);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
													
						}
					}else{
						JOptionPane.showMessageDialog(null, "�������� ����, ����� ��������, ��� '�����������'","�������� ����", 1);
					}
			}
		});
		
		JButton btnEditDeal = new JButton("\u0420\u0435\u0434\u0430\u043A\u0442\u0438\u0440\u043E\u0432\u0430\u0442\u044C \u0434\u0435\u043B\u043E");
		btnEditDeal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row >= 0 && table.getValueAt(row, 0)!=null){
						String name = (String)table.getValueAt(row, 3);
						String description =(String)table.getValueAt(row, 4);
						Factory factory = Factory.getInstance();
						DealDao dealDao = factory.getDealDao();
						Deal deal = null;
						try {
							deal = dealDao.getDealByInfo(name, description);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						editEventFrame = new EditEventFrame(deal){
							private static final long serialVersionUID = 1L;
							@Override
							public void dispose(){
								mainFrame.setEnabled(true);
								super.dispose();
							}
						};
						editEventFrame.setCalendarFrame(mainFrame);
						editEventFrame.setCurrentDeal(deal);
						mainFrame.setEnabled(false);
						editEventFrame.setVisible(true);
												
				}else{
					JOptionPane.showMessageDialog(null, "�������� ���� ", "������������ ��������", 1);
				}
			}
		});
		
		JPanel panelViewDay = new JPanel();
		panelViewDay.setBorder(new TitledBorder(null, "\u041F\u0440\u043E\u0441\u043C\u043E\u0442\u0440 \u0434\u043D\u044F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_panelMenu = new GroupLayout(panelMenu);
		gl_panelMenu.setHorizontalGroup(
			gl_panelMenu.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMenu.createSequentialGroup()
					.addGroup(gl_panelMenu.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelMenu.createSequentialGroup()
							.addGap(22)
							.addComponent(panelViewDay, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelMenu.createSequentialGroup()
							.addGap(34)
							.addGroup(gl_panelMenu.createParallelGroup(Alignment.LEADING)
								.addComponent(btnDeleteDeal, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnAddDeal, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnEndDeal, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnEditDeal))))
					.addContainerGap(38, Short.MAX_VALUE))
		);
		gl_panelMenu.setVerticalGroup(
			gl_panelMenu.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelMenu.createSequentialGroup()
					.addContainerGap(16, Short.MAX_VALUE)
					.addComponent(panelViewDay, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
					.addGap(26)
					.addComponent(btnEndDeal)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnAddDeal)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnDeleteDeal)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnEditDeal)
					.addGap(148))
		);
		
		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setDate(new Date());
		
		btnViewDay = new JButton("\u041F\u0440\u043E\u0441\u043C\u043E\u0442\u0440\u0435\u0442\u044C \u0434\u0435\u043D\u044C");
		btnViewDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment( JLabel.CENTER );
				table.setDefaultRenderer(String.class, centerRenderer);
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				while (model.getRowCount()>0) {
				  model.removeRow(0);
				}
				
				Date date = dateChooser.getDate();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String dateInStringFormat = dateFormat.format(date);
				Factory factory = Factory.getInstance();
				DealDao dealDao = factory.getDealDao();
				try {
					for (Deal deal : dealDao.getDealByDate(dateInStringFormat)) {
						model.addRow(new Object[]{deal.getReadiness(), deal.getImportance(),
								deal.getDate(), deal.getNameOfDeal(), deal.getDescription()});
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println(dateInStringFormat);
			}
		});
		GroupLayout gl_panelViewDay = new GroupLayout(panelViewDay);
		gl_panelViewDay.setHorizontalGroup(
			gl_panelViewDay.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelViewDay.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panelViewDay.createParallelGroup(Alignment.LEADING)
						.addComponent(dateChooser, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnViewDay, Alignment.TRAILING))
					.addContainerGap())
		);
		gl_panelViewDay.setVerticalGroup(
			gl_panelViewDay.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelViewDay.createSequentialGroup()
					.addContainerGap()
					.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnViewDay)
					.addContainerGap(58, Short.MAX_VALUE))
		);
		panelViewDay.setLayout(gl_panelViewDay);
		panelMenu.setLayout(gl_panelMenu);
		
		JScrollPane scrollPane = new JScrollPane();
		
		table = new JTable(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			//Disable add data function in cell
			@Override
			public boolean isCellEditable(int row, int count){
				return false;
			}
		};
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				int column = table.getSelectedColumn();
				if(e.getClickCount() == 2){
					if(table.getValueAt(row, 0)!=null){
						Deal selectedDeal = new Deal();
						String name = (String)table.getValueAt(row, 3);
						String description =(String)table.getValueAt(row, 4);
						Factory factory = Factory.getInstance();
						DealDao dealDao = factory.getDealDao();
						Deal deal = null;
						try {
							deal = dealDao.getDealByInfo(name, description);
							JOptionPane.showMessageDialog(null, deal.getNameOfDeal(),"You selected " + row + " row", 1);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
												
					}
				}
			}
		});
		table.setToolTipText("");
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, "", null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, "", null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"\u0413\u043E\u0442\u043E\u0432\u043D\u043E\u0441\u0442\u044C", "\u0412\u0430\u0436\u043D\u043E\u0441\u0442\u044C \u0434\u0435\u043B\u0430", "\u0414\u0430\u0442\u0430", "\u041D\u0430\u0437\u0432\u0430\u043D\u0438\u0435 \u0434\u0435\u043B\u0430", "\u041E\u043F\u0438\u0441\u0430\u043D\u0438\u0435"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(99);
		table.getColumnModel().getColumn(1).setPreferredWidth(107);
		table.getColumnModel().getColumn(2).setPreferredWidth(84);
		table.getColumnModel().getColumn(3).setPreferredWidth(114);
		table.getColumnModel().getColumn(4).setPreferredWidth(111);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 390, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		imagePanel.setLayout(gl_imagePanel);
		contentPane.setLayout(gl_contentPane);
	}

	public Deal getCurrentDeal() {
		return currentDeal;
	}

	public void setCurrentDeal(Deal currentDeal) {
		this.currentDeal = currentDeal;
	}
}
