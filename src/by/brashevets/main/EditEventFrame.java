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
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

import by.brashevets.dao.DealDao;
import by.brashevets.entity.deal.Deal;
import by.brashevets.entity.enums.Importance;
import by.brashevets.entity.enums.Readiness;
import by.brashevets.factory.Factory;

import com.toedter.calendar.JDateChooser;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditEventFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldName;
	private JSpinner spinnerTime;
	private JComboBox comboBoxImportance;
	private JDateChooser dateChooser;
	private JButton buttonEditEvent;
	private JTextPane textPane;
	private Deal currentDeal;
	private MainFrame calendarFrame;
	private JButton btnDeleteEvent;

	/**
	 * Launch the application.
//	 */

	/**
	 * Create the frame.
	 */
	public EditEventFrame(final Deal currentDeal) {
		this.currentDeal = currentDeal; 
		setTitle("\u0420\u0435\u0434\u0430\u043A\u0442\u0438\u0440\u043E\u0432\u0430\u043D\u0438\u0435 \u0441\u043E\u0431\u044B\u0442\u0438\u044F");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 401, 387);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new TitledBorder(null, "\u041E\u043F\u0438\u0441\u0430\u043D\u0438\u0435 \u0441\u043E\u0431\u044B\u0442\u0438\u044F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		textPane.setText(currentDeal.getDescription());
		
		comboBoxImportance = new JComboBox();
		comboBoxImportance.setModel(new DefaultComboBoxModel(new String[] {"\u041E\u0447\u0435\u043D\u044C \u0432\u0430\u0436\u043D\u043E\u0435", "\u0412\u0430\u0436\u043D\u043E\u0435", "\u041E\u0431\u044B\u0447\u043D\u043E\u0435"}));
		for(int i = 0; i < comboBoxImportance.getItemCount(); i++ ){
			if(comboBoxImportance.getItemAt(i).equals(currentDeal.getImportance())){
				comboBoxImportance.setSelectedIndex(i);
				break;
			}
		}
		
		
		JLabel lblNewLabel = new JLabel("\u0421\u0442\u0435\u043F\u0435\u043D\u044C \u0432\u0430\u0436\u043D\u043E\u0441\u0442\u0438:");
		
		textFieldName = new JTextField();
		textFieldName.setColumns(10);
		textFieldName.setText(currentDeal.getNameOfDeal());
		JLabel label = new JLabel("\u041D\u0430\u0437\u0432\u0430\u043D\u0438\u0435 \u0434\u0435\u043B\u0430:");
		
		JLabel label_1 = new JLabel("\u0412\u0440\u0435\u043C\u044F:");
		
		spinnerTime = new JSpinner();
		spinnerTime.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY));
		JSpinner.DateEditor de_spinnerTime = new JSpinner.DateEditor(spinnerTime, "HH:mm");
		spinnerTime.setEditor(de_spinnerTime);
		
		JLabel label_2 = new JLabel("\u0414\u0430\u0442\u0430:");
		
		dateChooser = new JDateChooser();
		DateFormat formatter ; 
		Date date = null ; 
		   formatter = new SimpleDateFormat("yyyy-MM-dd");
		   try {
			   date = formatter.parse(currentDeal.getDate());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		   dateChooser.setDateFormatString("yyyy-MM-dd");
		   dateChooser.setDate(date);
		
		buttonEditEvent = new JButton("\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C \u0438\u0437\u043C\u0435\u043D\u0435\u043D\u0438\u044F");
		buttonEditEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				Deal deal = null;
//				try{
					String nameOfDeal = textFieldName.getText();
					String description = textPane.getText();
					Date date = dateChooser.getDate();
					Date timeDate = (Date)spinnerTime.getValue();
					String importance = (String)comboBoxImportance.getSelectedItem();
					String readiness = "Не готово";
					
					java.util.Date utilDate = date;
				    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				    Date date1 = ((SpinnerDateModel) spinnerTime.getModel()).getDate();
				    Time beginTime = new Time(((SpinnerDateModel) spinnerTime.getModel()).getDate().getTime());
				    Time startTime = new Time(((SpinnerDateModel) spinnerTime.getModel()).getDate().getTime());
				    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				    currentDeal.setDate(dateFormat.format(date));
				    currentDeal.setNameOfDeal(nameOfDeal);
					currentDeal.setDescription(description);
					currentDeal.setImportance(importance);
					currentDeal.setReadiness(readiness);
					Factory factory = Factory.getInstance();
					DealDao dealDao = factory.getDealDao();
					try {
						dealDao.updateDeal(currentDeal);
						JOptionPane.showMessageDialog(null,"Событие успешно обновлено",	"Уведомление", 1);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}				
			}
		});
		
		btnDeleteEvent = new JButton("\u0423\u0434\u0430\u043B\u0438\u0442\u044C \u0441\u043E\u0431\u044B\u0442\u0438\u0435");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(5)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 362, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(146)
								.addComponent(dateChooser, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addContainerGap()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(label, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
										.addGap(30)
										.addComponent(textFieldName))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
											.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
											.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
										.addGap(30)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
											.addComponent(spinnerTime)
											.addComponent(comboBoxImportance, 0, 106, Short.MAX_VALUE))))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(38)
							.addComponent(buttonEditEvent)
							.addGap(18)
							.addComponent(btnDeleteEvent, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(8, Short.MAX_VALUE))
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
						.addComponent(label_1)
						.addComponent(spinnerTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(label_2)
						.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(54)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(buttonEditEvent)
						.addComponent(btnDeleteEvent))
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
