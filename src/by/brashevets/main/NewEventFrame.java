package by.brashevets.main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

import by.brashevets.dao.DealDao;
import by.brashevets.entity.deal.Deal;
import by.brashevets.factory.Factory;

import com.toedter.calendar.JDateChooser;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewEventFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldName;
	private JSpinner spinnerTime;
	private JComboBox<Object> comboBoxImportance;
	private JDateChooser dateChooser;
	private JButton buttonAddEvent;
	private JTextPane textPane;
	private Deal currentDeal;
	private MainFrame calendarFrame;

	/**
	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		try {
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					AddEventFrame frame = new AddEventFrame();
//					addEventFrame = frame;
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public NewEventFrame() {
		setTitle("\u041D\u043E\u0432\u043E\u0435 \u0441\u043E\u0431\u044B\u0442\u0438\u0435");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 401, 387);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new TitledBorder(null, "\u041E\u043F\u0438\u0441\u0430\u043D\u0438\u0435 \u0441\u043E\u0431\u044B\u0442\u0438\u044F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		comboBoxImportance = new JComboBox<Object>();
		comboBoxImportance.setModel(new DefaultComboBoxModel<Object>(new String[] {"\u041E\u0447\u0435\u043D\u044C \u0432\u0430\u0436\u043D\u043E\u0435", "\u0412\u0430\u0436\u043D\u043E\u0435", "\u041E\u0431\u044B\u0447\u043D\u043E\u0435"}));
		comboBoxImportance.setSelectedIndex(2);
		
		JLabel lblNewLabel = new JLabel("\u0421\u0442\u0435\u043F\u0435\u043D\u044C \u0432\u0430\u0436\u043D\u043E\u0441\u0442\u0438:");
		
		textFieldName = new JTextField();
		textFieldName.setColumns(10);
		
		JLabel label = new JLabel("\u041D\u0430\u0437\u0432\u0430\u043D\u0438\u0435 \u0434\u0435\u043B\u0430:");
		
		JLabel label_1 = new JLabel("\u0412\u0440\u0435\u043C\u044F:");
		
		spinnerTime = new JSpinner();
		spinnerTime.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY));
		JSpinner.DateEditor de_spinnerTime = new JSpinner.DateEditor(spinnerTime, "HH:mm");
		spinnerTime.setEditor(de_spinnerTime);
		
		JLabel label_2 = new JLabel("\u0414\u0430\u0442\u0430:");
		
		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setDate(new Date());

		buttonAddEvent = new JButton("\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C \u0441\u043E\u0431\u044B\u0442\u0438\u0435");
		buttonAddEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String nameOfDeal = textFieldName.getText();
					String description = textPane.getText();
					Date date = dateChooser.getDate();
					String importance = (String)comboBoxImportance.getSelectedItem();
					currentDeal = new Deal();
					String readiness = "Не готово";
					
				    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				    currentDeal.setDate(dateFormat.format(date));
				    currentDeal.setNameOfDeal(nameOfDeal);
					currentDeal.setDescription(description);
					currentDeal.setImportance(importance);
					currentDeal.setReadiness(readiness);
					calendarFrame.setCurrentDeal(currentDeal); 
					calendarFrame.setVisible(true);
					Factory factory = Factory.getInstance();
					DealDao dealDao = factory.getDealDao();
					try {
						dealDao.addDeal(currentDeal);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					dispose();
					JOptionPane.showMessageDialog(null, "Новое событие " + currentDeal.getNameOfDeal()+ " успешно добавлено на " + currentDeal.getDate()+"." ,
							"Уведомление", 1);
				}finally{
					
				}
				
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(5)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 362, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
								.addGap(146)
								.addComponent(dateChooser, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
								.addContainerGap()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(label, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
										.addGap(30)
										.addComponent(textFieldName))
									.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
											.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
											.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
										.addGap(30)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
											.addComponent(spinnerTime)
											.addComponent(comboBoxImportance, 0, 106, Short.MAX_VALUE)))))))
					.addContainerGap(8, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(131, Short.MAX_VALUE)
					.addComponent(buttonAddEvent)
					.addGap(115))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(label)
						.addComponent(textFieldName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(lblNewLabel))
						.addComponent(comboBoxImportance, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(spinnerTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(label_2)
						.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(54)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(buttonAddEvent)
					.addContainerGap(17, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}

	public Deal getCurrentDeal() {
		return currentDeal;
	}

	public void setCurrentDeal(Deal currentDeal) {
		this.currentDeal = currentDeal;
	}

	public MainFrame getCalendarFrame() {
		return calendarFrame;
	}

	public void setCalendarFrame(MainFrame calendarFrame) {
		this.calendarFrame = calendarFrame;
	}
}
