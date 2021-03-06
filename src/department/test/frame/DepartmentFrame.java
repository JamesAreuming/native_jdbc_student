package department.test.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import department.test.dto.Department;
import department.test.dto.Employee;
import department.test.panel.DepartmentPanel;
import department.test.panel.DepartmentTblPanel;
import department.test.ui.DlgEmployee;
import department.test.ui.service.DepartmentUIService;

@SuppressWarnings("serial")
public class DepartmentFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JPanel pContent;
	private DepartmentTblPanel pList;
	private DepartmentPanel pDepartment;
	private JPanel pBtns;
	private JButton btnAdd;
	private JButton btnCancel;
	private DepartmentUIService service;
	private DlgEmployee dialog;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DepartmentFrame frame = new DepartmentFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public DepartmentFrame() {
		service = new DepartmentUIService(); //private DepartmentUIService service;를 선언 했으니 생성
		dialog = new DlgEmployee();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		initialize();
	}
	
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("부서 관리");
		setBounds(100, 100, 450, 468);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		pContent = new JPanel();
		contentPane.add(pContent);
		pContent.setLayout(new BorderLayout(0, 0));
		
		pDepartment = new DepartmentPanel();
		pContent.add(pDepartment, BorderLayout.CENTER);
		
		pBtns = new JPanel();
		contentPane.add(pBtns);
		
		btnAdd = new JButton("추가");
		btnAdd.addActionListener(this);
		pBtns.add(btnAdd);
		
		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		pBtns.add(btnCancel);
		
		pList = new DepartmentTblPanel();
		contentPane.add(pList);
		
		List<Department> depts = service.showDepartments();
		pList.loadData(depts);
		
		pList.setPopupMenu(createPopupMenu());
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel) {
			btnCancelActionPerformed(e);
		}
		if (e.getSource() == btnAdd) {
			if (e.getActionCommand().contentEquals("추가")) {
				btnAddActionPerformed(e);
			}else {
				btnUpdateActionPerformed(e);
			}
		}
	}
	
	private void btnUpdateActionPerformed(ActionEvent e) {
		Department newDept = pDepartment.getItem();
		try {
			service.modifyDepartment(newDept);
			pList.updateRow(newDept, pList.getSelectedRowIdx());
			btnAdd.setText("추가");
			pDepartment.clearTf();
			JOptionPane.showMessageDialog(null, "부서가 수정되었습니다.");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}

	protected void btnAddActionPerformed(ActionEvent e) {
		try {
			Department newDept = pDepartment.getItem();
			service.addDepartment(newDept);
			pList.addItem(newDept);
			pDepartment.clearTf();
			JOptionPane.showMessageDialog(null, "부서가 추가되었습니다.");
		}catch(Exception e1) {
			SQLException e2 = (SQLException) e1;
			if (e2.getErrorCode() == 1062) {
				JOptionPane.showMessageDialog(null, "부서번호가 중복");
				System.err.println(e2.getMessage());
				return;
			}
			e1.printStackTrace();
		}
	}
	
	protected void btnCancelActionPerformed(ActionEvent e) {
		pDepartment.clearTf();
	}
	
	private JPopupMenu createPopupMenu() {
		JPopupMenu popMenu = new JPopupMenu();
		
		JMenuItem updateItem = new JMenuItem("수정");
		updateItem.addActionListener(myPopMenuListener);
		popMenu.add(updateItem);
		
		JMenuItem deleteItem = new JMenuItem("삭제");
		deleteItem.addActionListener(myPopMenuListener);
		popMenu.add(deleteItem);
		
		JMenuItem showEmployee = new JMenuItem("소속사원");
		showEmployee.addActionListener(myPopMenuListener);
		popMenu.add(showEmployee);
		
		return popMenu;
	}
	
	ActionListener myPopMenuListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("수정")) {
				//JOptionPane.showMessageDialog(null, "수정");
				Department upDept = pList.getSelectedItem();
				pDepartment.setItem(upDept);
				btnAdd.setText("수정");
			}
			if (e.getActionCommand().equals("삭제")) {
				//JOptionPane.showMessageDialog(null, "삭제");
				Department delDept = pList.getSelectedItem();
				try {
					service.removeDepartment(delDept);
					pList.removeRow();
					JOptionPane.showMessageDialog(null, "삭제되었습니다.");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}				
			}
			
			if(e.getActionCommand().equals("소속사원")) {
				Department selectedDept = pList.getSelectedItem(); // 선택한 부서정보
				//JOptionPane.showMessageDialog(null, "소속사원"+selectedDept);
				
				List<Employee> list = service.showEmployeeGroupByDno(selectedDept);
				
				//dialog = new DlgEmployee();
				
				dialog.setEmpList(list); //EmpList
				//dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setTitle(selectedDept.getDeptName()+"부서");
				//System.out.println(e.getSource());
				dialog.setVisible(true);
			}
		}
	}; 
}
