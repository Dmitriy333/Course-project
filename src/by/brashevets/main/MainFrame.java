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

import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

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
	private JLabel label;
	private static final String READY = "Выполнено";
	private JCheckBox readinessCheckBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
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
				label.setVisible(false);
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment(JLabel.CENTER);
				table.setDefaultRenderer(String.class, centerRenderer);
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
				//label.setVisible(true);
				//table.setVisible(false);
				Date date = dateChooser.getDate();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String dateInStringFormat = dateFormat.format(date);
				Factory factory = Factory.getInstance();
				DealDao dealDao = factory.getDealDao();
				try {
					List<Deal> deals = dealDao
							.getDealByDate(dateInStringFormat);
					if (deals.size() > 0) {
						for (Deal deal : deals) {
							if(deal.getReadiness().equals(READY) && !readinessCheckBox.isSelected()){
								continue;
							}else{
								model.addRow(new Object[] { deal.getReadiness(),
										deal.getImportance(), deal.getDate(),
										deal.getNameOfDeal(), deal.getDescription() });
							}
						}
					}else{
						label.setVisible(true);
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

		JPanel imagePanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				Image img = Toolkit.getDefaultToolkit().getImage(
						MainFrame.class.getResource("/images/font.png"));
				g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
			}
		};
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(
				Alignment.LEADING).addComponent(imagePanel, Alignment.TRAILING,
				GroupLayout.DEFAULT_SIZE, 637, Short.MAX_VALUE));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(
				Alignment.LEADING).addComponent(imagePanel, Alignment.TRAILING,
				GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE));

		JPanel panel = new JPanel();

		JPanel panelMenu = new JPanel();
		panelMenu.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GRAY));
		GroupLayout gl_imagePanel = new GroupLayout(imagePanel);
		gl_imagePanel.setHorizontalGroup(
			gl_imagePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_imagePanel.createSequentialGroup()
					.addGap(23)
					.addComponent(panelMenu, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE)
					.addGap(92)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 408, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(70, Short.MAX_VALUE))
		);
		gl_imagePanel.setVerticalGroup(
			gl_imagePanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_imagePanel.createSequentialGroup()
					.addGap(39)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 453, Short.MAX_VALUE)
					.addGap(24))
				.addGroup(gl_imagePanel.createSequentialGroup()
					.addContainerGap(55, Short.MAX_VALUE)
					.addComponent(panelMenu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(28))
		);

		btnAddDeal = new JButton(
				"\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C \u0441\u043E\u0431\u044B\u0442\u0438\u0435");
		btnAddDeal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addEventFrame = new NewEventFrame() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void dispose() {
						mainFrame.setEnabled(true);
						super.dispose();
					}
				};
				addEventFrame.setVisible(true);
				addEventFrame.setCalendarFrame(mainFrame);
				mainFrame.setEnabled(false);
			}
		});

		JButton btnDeleteDeal = new JButton(
				"\u0423\u0434\u0430\u043B\u0438\u0442\u044C \u0441\u043E\u0431\u044B\u0442\u0438\u0435");
		btnDeleteDeal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row >= 0 && table.getValueAt(row, 0) != null) {
					Deal deal = getDealFromSelectedRow(row);
					Factory factory = Factory.getInstance();
					DealDao dealDao = factory.getDealDao();
					try {
						dealDao.deleteDeal(deal);
						JOptionPane.showMessageDialog(null,"Событие успешно удалено",	"Уведомление", 1);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Выберите дело для удаления ",
							"Некорректное действие", 1);
				}
			}
		});

		btnEndDeal = new JButton(
				"\u0417\u0430\u0432\u0435\u0440\u0448\u0438\u0442\u044C \u0441\u043E\u0431\u044B\u0442\u0438\u0435");
		btnEndDeal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row >= 0 && table.getValueAt(row, 0) != null) {
					Deal deal = getDealFromSelectedRow(row);
					Factory factory = Factory.getInstance();
					DealDao dealDao = factory.getDealDao();
					try {
						deal.setReadiness("Выполнено");
						table.getModel().setValueAt("Выполнено", row, 0);
						dealDao.updateDeal(deal);
						JOptionPane.showMessageDialog(null,"Событие успешно помечено как \"выполненное\"",	"Уведомление", 1);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Выберите дело, чтобы пометить, как \"Выполнено\"",
							"Выберите дело", 1);
				}
			}
		});

		JButton btnEditDeal = new JButton(
				"\u0420\u0435\u0434\u0430\u043A\u0442\u0438\u0440\u043E\u0432\u0430\u0442\u044C \u0441\u043E\u0431\u044B\u0442\u0438\u0435");
		btnEditDeal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				correctSelectedDeal();
			}
		});

		JPanel panelViewDay = new JPanel();
		panelViewDay
				.setBorder(new TitledBorder(
						null,
						"\u041F\u0440\u043E\u0441\u043C\u043E\u0442\u0440 \u0434\u043D\u044F",
						TitledBorder.LEADING, TitledBorder.TOP, null, null));

		readinessCheckBox = new JCheckBox(
				"\u041F\u043E\u043A\u0430\u0437\u044B\u0432\u0430\u0442\u044C \u0432\u044B\u043F\u043E\u043B\u043D\u0435\u043D\u043D\u044B\u0435");
		readinessCheckBox.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				showEventsOfCurrentDay();
			}
		});
		readinessCheckBox.setSelected(true);
		GroupLayout gl_panelMenu = new GroupLayout(panelMenu);
		gl_panelMenu.setHorizontalGroup(
			gl_panelMenu.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMenu.createSequentialGroup()
					.addGap(34)
					.addGroup(gl_panelMenu.createParallelGroup(Alignment.LEADING)
						.addComponent(readinessCheckBox)
						.addGroup(gl_panelMenu.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(btnDeleteDeal, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnAddDeal, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnEndDeal, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnEditDeal, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(panelViewDay, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(24, Short.MAX_VALUE))
		);
		gl_panelMenu.setVerticalGroup(
			gl_panelMenu.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelMenu.createSequentialGroup()
					.addGap(25)
					.addComponent(panelViewDay, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
					.addComponent(btnEndDeal)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnAddDeal)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnDeleteDeal)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnEditDeal)
					.addGap(18)
					.addComponent(readinessCheckBox)
					.addGap(107))
		);

		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setDate(new Date());

		btnViewDay = new JButton(
				"\u041F\u0440\u043E\u0441\u043C\u043E\u0442\u0440\u0435\u0442\u044C \u0434\u0435\u043D\u044C");
		btnViewDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment(JLabel.CENTER);
				table.setDefaultRenderer(String.class, centerRenderer);
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}

				Date date = dateChooser.getDate();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String dateInStringFormat = dateFormat.format(date);
				Factory factory = Factory.getInstance();
				DealDao dealDao = factory.getDealDao();
				try {
					List<Deal> deals = dealDao.getDealByDate(dateInStringFormat);
					if(deals.size()>0){
						label.setVisible(false);
						for (Deal deal : deals) {
							if(deal.getReadiness().equals(READY) && !readinessCheckBox.isSelected()){
								continue;
							}else{
								model.addRow(new Object[] { deal.getReadiness(),
										deal.getImportance(), deal.getDate(),
										deal.getNameOfDeal(), deal.getDescription() });
							}
						}
					}else{
						label.setVisible(true);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// System.out.println(dateInStringFormat);
			}
		});
		GroupLayout gl_panelViewDay = new GroupLayout(panelViewDay);
		gl_panelViewDay
				.setHorizontalGroup(gl_panelViewDay
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelViewDay
										.createSequentialGroup()
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												gl_panelViewDay
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																dateChooser,
																Alignment.TRAILING,
																GroupLayout.PREFERRED_SIZE,
																126,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																btnViewDay,
																Alignment.TRAILING))
										.addContainerGap()));
		gl_panelViewDay.setVerticalGroup(gl_panelViewDay.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panelViewDay
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(btnViewDay)
						.addContainerGap(58, Short.MAX_VALUE)));
		panelViewDay.setLayout(gl_panelViewDay);
		panelMenu.setLayout(gl_panelMenu);

		JScrollPane scrollPane = new JScrollPane();

		table = new JTable() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			// Disable add data function in cell
			@Override
			public boolean isCellEditable(int row, int count) {
				return false;
			}
		};
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (e.getClickCount() == 2) {
					if (table.getValueAt(row, 0) != null) {
						correctSelectedDeal();
					}
				}
			}
		});
		table.setToolTipText("");
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
				new Object[][] { { null, null, "", null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, "", null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null }, },
				new String[] {
						"\u0413\u043E\u0442\u043E\u0432\u043D\u043E\u0441\u0442\u044C",
						"\u0412\u0430\u0436\u043D\u043E\u0441\u0442\u044C \u0434\u0435\u043B\u0430",
						"\u0414\u0430\u0442\u0430",
						"\u041D\u0430\u0437\u0432\u0430\u043D\u0438\u0435 \u0434\u0435\u043B\u0430",
						"\u041E\u043F\u0438\u0441\u0430\u043D\u0438\u0435" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(99);
		table.getColumnModel().getColumn(1).setPreferredWidth(107);
		table.getColumnModel().getColumn(2).setPreferredWidth(84);
		table.getColumnModel().getColumn(3).setPreferredWidth(114);
		table.getColumnModel().getColumn(4).setPreferredWidth(111);
		
		label = new JLabel("\u041D\u0430 \u0441\u0435\u0433\u043E\u0434\u043D\u044F \u0434\u0435\u043B \u043D\u0435\u0442");
		label.setFont(new Font("Malgun Gothic", Font.ITALIC, 17));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 390, GroupLayout.PREFERRED_SIZE)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 390, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
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
	
	private Deal getDealFromSelectedRow(int row){
		String name = (String) table.getValueAt(row, 3);
		String description = (String) table.getValueAt(row, 4);
		Factory factory = Factory.getInstance();
		DealDao dealDao = factory.getDealDao();
		Deal deal = null;
		try {
			deal = dealDao.getDealByInfo(name, description);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return deal;
	}
	private void correctSelectedDeal(){
		int row = table.getSelectedRow();
		if (row >= 0 && table.getValueAt(row, 0) != null) {
			Deal deal = getDealFromSelectedRow(row);
			editEventFrame = new EditEventFrame(deal) {
				private static final long serialVersionUID = 1L;

				@Override
				public void dispose() {
					mainFrame.setEnabled(true);
					super.dispose();
				}
			};
			editEventFrame.setCalendarFrame(mainFrame);
			editEventFrame.setCurrentDeal(deal);
			mainFrame.setEnabled(false);
			editEventFrame.setVisible(true);

		} else {
			JOptionPane.showMessageDialog(null, "Выберите дело для редактирования ",
					"Некорректное действие", 1);
		}
	}
	private void showEventsOfCurrentDay(){
		label.setVisible(false);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(String.class, centerRenderer);
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
		Date date = dateChooser.getDate();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateInStringFormat = dateFormat.format(date);
		Factory factory = Factory.getInstance();
		DealDao dealDao = factory.getDealDao();
		try {
			List<Deal> deals = dealDao
					.getDealByDate(dateInStringFormat);
			if (deals.size() > 0) {
				for (Deal deal : deals) {
					if(deal.getReadiness().equals(READY) && !readinessCheckBox.isSelected()){
						continue;
					}else{
						model.addRow(new Object[] { deal.getReadiness(),
								deal.getImportance(), deal.getDate(),
								deal.getNameOfDeal(), deal.getDescription() });
					}
				}
			}else{
				label.setVisible(true);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
}
