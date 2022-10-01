


//Vaccine Table Project

//Project ini adalah sebuah program Java GUI untuk menampilkan
//Nama Vaksin, Harga Vaksin dan Stok Vaksin.

//Kelompok 22
//2440030241 - Reynald Slamat Putra
//2440062924 - Charles Christopher
//2440046984 - Elliot Lie Arifin

//Operasi yang bisa dilakukan pada program ini adalah :

//1. Add Vaccine 
//dengan generate random ID, Nama Vaksin, Harga Vaksin dan Stok Vaksinnya. Setelah itu, membuat sebuah objek untuk penjelasan melalui factory yang telah dibuat berdasarkan Harga Vaksin yang diinput pengguna. Lalu menampilkan informasi apakah vaksin tergolong murah atau mahal berdasarkan harganya, jika >50.000 termasuk golongan mahal yang diambil dari hasil pembuatan objek di factory.

//2. Update Vaccine
//Hampir mirip dengan Add, dengan mengklik salah satu baris pada Table Vaccine, akan menampilkan informasi dari vaksin tersebut pada sebuah text field yang bisa diubah untuk diupdate. Proses menampilkan vaksin murah atau mahal juga sama dengan fitur Add.

//3. Delete Vaccine 
//Memilih salah satu baris pada table, lalu klik button delete, akan menghapus vaksin itu pada table.

//4. Menampilkan Vaccine Table
//Selalu memunculkan table Vaccine

//Implementasi 2 design pattern:
//Singleton = Koneksi DB pada class database.java
//Factory = Pembuatan object pada class readerFactory.java

//Penggunaan inheritance dan abstract kami terapkan pada class reader.java (interface)
//Penggunaan encapsulation kami terapkan pada ExpensiveReader dan CheapReader

//Pengimplementasian database transaction terjadi pada fitur add, update, dan delete
//dengan menggunakan SELECT, INSERT INTO, SET, dan DELETE di class Vaccine_Table

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.peer.ComponentPeer;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.w3c.dom.events.MouseEvent;
public class Vaccine_Table {

	private JFrame frame;
	private JTextField txtID;
	private JTextField txtName;
	private JTextField txtPrice;
	private JTextField txtStock;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnAdd;
	private JButton btnDelete;
	private JButton btnUpdate;
	DefaultTableModel model;
	
	private void initialize_gui()
	{
		frame = new JFrame("Bumame Application");
		frame.setBounds(100, 100, 539, 437);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(176, 196, 222));
		panel.setBounds(0, 0, 531, 410);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblID = new JLabel("ID:");
		lblID.setBounds(21, 83, 46, 14);
		panel.add(lblID);
		
		JLabel lblName = new JLabel("Vaksin:");
		lblName.setBounds(21, 105, 46, 14);
		panel.add(lblName);
		
		JLabel lblPrice = new JLabel("Harga:");
		lblPrice.setBounds(21, 127, 46, 14);
		panel.add(lblPrice);
		
		JLabel lblStock = new JLabel("Stok:");
		lblStock.setBounds(21, 148, 46, 14);
		panel.add(lblStock);
		
		txtID = new JTextField();
		txtID.setBounds(67, 81, 132, 17);
		panel.add(txtID);
		txtID.setColumns(10);
		
		txtName = new JTextField();
		txtName.setBounds(67, 101, 132, 17);
		txtName.setColumns(10);
		panel.add(txtName);
		
		txtPrice = new JTextField();
		txtPrice.setBounds(67, 124, 132, 17);
		panel.add(txtPrice);
		txtPrice.setColumns(10);
		
		txtStock = new JTextField();
		txtStock.setBounds(67, 145, 132, 17);
		panel.add(txtStock);
		txtStock.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(224, 48, 297, 339);
		panel.add(scrollPane);
		
		table = new JTable();
		tableClicked();
		
		btnAdd = new JButton("Add");
		btnAdd.setBounds(21, 280, 75, 23);
		panel.add(btnAdd);
		addButtonEvent();
		
		btnDelete = new JButton("Delete");
		btnDelete.setBounds(21, 324, 75, 23);
		panel.add(btnDelete);
		deleteButtonEvent();
		
		btnUpdate = new JButton("Update");
		btnUpdate.setBounds(133, 280, 75, 23);
		panel.add(btnUpdate);
		updateButtonEvent();
	}
	private boolean isInt(JTextField input, String message)
	{
		try {
			int num = Integer.parseInt(input.getText());
			return true;
		} catch (NumberFormatException e) {
			System.out.println("Error: " + e.getMessage());
			return false;
		}
	}
	private String generateID() 
	{
		String ID = "PD-";
		for (int i=0; i<3; i++)
		{
			ID += (int) Math.floor(Math.random()*9 + 1);
		}
		return ID;
	}
	private void clear_form()
	{
		txtID.setEditable(false);
		txtID.setText(null);
		txtName.setText(null);
		txtPrice.setText(null);
		txtStock.setText(null);
	}
	private void initialize_tables()
	{
		table.setBackground(new Color (176, 196, 222));
		scrollPane.setViewportView(table);
		model = new DefaultTableModel();
		model.addColumn("No.");
		model.addColumn("ID");
		model.addColumn("Vaksin");
		model.addColumn("Harga");
		model.addColumn("Stok");
	}
	public void addButtonEvent()
	{
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				// TODO Auto-generated method stub
				try {
					if (txtName.getText().equals("") || txtPrice.getText().equals("") || txtStock.getText().equals(""))
					{
						JOptionPane.showMessageDialog(null, "Please fill complete information!");
					}
					else
					{
						if (isInt(txtPrice, txtPrice.getText()) && isInt(txtStock, txtStock.getText()))
						{
							String query = "INSERT INTO menu VALUES ('" + generateID() + "','"+txtName.getText()+"', '"+txtPrice.getText()+"', '"+txtStock.getText()+"')";
							java.sql.Connection con = database.configDB();
							java.sql.PreparedStatement ps = con.prepareStatement(query);
							ps.execute();
							JOptionPane.showMessageDialog(null, "Data stored successfuly!");
							String desc = null;
							Integer vacPrice = Integer.valueOf(txtPrice.getText());
							//implementasi factory untuk membuat object apakah vaksin tergolong murah atau mahal
							//yang akan diproses oleh factory
                            desc = readerFactory.getreader(vacPrice).read();
                            JOptionPane.showMessageDialog(null, desc);   
                            
						}
						else JOptionPane.showMessageDialog(null, "Menu price and stock must be integer!");
					}
					display_data();
					clear_form();
				} catch (SQLException e) {
					System.out.println("Error:" + e.getMessage());
				}
			}
		});
	}
	private void tableClicked()
	{
		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				int row = table.rowAtPoint(e.getPoint());
				String ID = (String) table.getValueAt(row, 1);
				txtID.setText(ID);
				
				String name = (String) table.getValueAt(row, 2);
				txtName.setText(name);
				
				String price = (String) table.getValueAt(row, 3);
				txtPrice.setText(price);
				
				String stock = (String) table.getValueAt(row, 4);
				txtStock.setText(stock);
				
			}
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			
		});
	}
	public void deleteButtonEvent()
	{
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				// TODO Auto-generated method stub
				int selectedIndex = table.getSelectedRow();
				if (selectedIndex >= 0)
				{
					try {
						String query = "DELETE FROM menu WHERE menu.ID LIKE '" + txtID.getText() + "'";
						java.sql.Connection con = database.configDB();
						java.sql.PreparedStatement ps = con.prepareStatement(query);
						ps.execute();
						JOptionPane.showMessageDialog(null, "Data removed successfuly!");
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
					display_data();
					clear_form();
				}
				else JOptionPane.showMessageDialog(null, "Please select a row from data table first!");
			}
		});
	}
	public void updateButtonEvent()
	{
		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				int selectedIndex = table.getSelectedRow();
				
				if (selectedIndex >= 0)
				{
					if (txtName.getText().isEmpty() || txtPrice.getText().isEmpty() || txtStock.getText().isEmpty()) JOptionPane.showMessageDialog(null, "Please fill all the fields!");
					else
					{
						try {
							String query = "UPDATE menu SET menu.name = '"+txtName.getText()+"', menu.price = '"+txtPrice.getText()+"', menu.stock = '"+txtStock.getText()+"' WHERE menu.id LIKE '" + txtID.getText() + "'";
							System.out.println(query);
							java.sql.Connection con = database.configDB();
							java.sql.PreparedStatement ps = con.prepareStatement(query);
							ps.execute();
							JOptionPane.showMessageDialog(null, "Data updated successfuly!");
							String desc = null;
							Integer vacPrice = Integer.valueOf(txtPrice.getText());
							
							//implementasi factory untuk membuat object apakah vaksin tergolong murah atau mahal
							//yang akan diproses oleh factory
                            desc = readerFactory.getreader(vacPrice).read();
                            JOptionPane.showMessageDialog(null, desc);   
						} catch (SQLException e) {
							JOptionPane.showMessageDialog(null, e.getMessage());
						}
					} 
					display_data();
					clear_form();
					
				}
				else JOptionPane.showMessageDialog(null, "Please select a row from data table first!");
			}
		});
		
	}
	public void display_data()
	{
		clear_form();
		initialize_tables();
		//collecting and displaying data from database to GUI
		try {
			int index = 1;
			String query = "SELECT * FROM menu";
			java.sql.Connection con = database.configDB();
			java.sql.Statement st = con.createStatement();
			java.sql.ResultSet res = st.executeQuery(query);
			
			while(res.next()) {
				model.addRow(new Object[] {index++, res.getString(1), res.getString(2), res.getString(3), res.getString(4)});
			}
			table.setModel(model);
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public Vaccine_Table() {
		// TODO Auto-generated constructor stub
		initialize_gui();
		display_data();
	}
	public static void main (String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vaccine_Table pt = new Vaccine_Table();
					pt.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

}
