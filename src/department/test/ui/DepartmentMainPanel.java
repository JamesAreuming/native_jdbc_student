package department.test.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import department.test.dto.Department;
import department.test.panel.DepartmentPanel;
import department.test.panel.DepartmentTblPanel;

@SuppressWarnings("serial")
public class DepartmentMainPanel extends JPanel implements ActionListener {
	private JPanel pContent;
	private DepartmentPanel pDepartment;
	private DepartmentTblPanel pList;
	private JPanel pBtns;
	private JButton btnAdd;
	private JButton btnCancel;
	protected int updateIdx;


	public DepartmentMainPanel() {

		initialize();
	}
	private void initialize() {
		setLayout(new GridLayout(0, 1, 0, 10));
		
		pContent = new JPanel();
		add(pContent);
		pContent.setLayout(new BoxLayout(pContent, BoxLayout.X_AXIS));
		
		pDepartment = new DepartmentPanel();
		GridLayout gridLayout = (GridLayout) pDepartment.getLayout();
		gridLayout.setHgap(0);
		pContent.add(pDepartment);
		
		pList = new DepartmentTblPanel();
		add(pList);
		pList.setPopupMenu(createPopupMenu());
		
		ArrayList<Department> depts = new ArrayList<Department>();
		depts.add(new Department(1, "개발", 5));
		depts.add(new Department(2, "인사", 2));
		depts.add(new Department(3, "영업", 3));
		depts.add(new Department(4, "기획", 6));
		depts.add(new Department(5, "마케팅", 7));
		
		pList.loadData(depts);
		
//		pList.setLayout(new BorderLayout(0, 0));
		
		pBtns = new JPanel();
		add(pBtns);
		
		btnAdd = new JButton("추가");
		btnAdd.addActionListener(this);
		pBtns.add(btnAdd);
		
		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		pBtns.add(btnCancel);
	}
	private JPopupMenu createPopupMenu() {
		JPopupMenu popMenu = new JPopupMenu();
		
		JMenuItem updateItem = new JMenuItem("수정");
		updateItem.addActionListener(myPopMenuListener);
		popMenu.add(updateItem);
		
		JMenuItem deleteItem = new JMenuItem("삭제");
		deleteItem.addActionListener(myPopMenuListener);
		popMenu.add(deleteItem);
		
		return popMenu;
	}
	ActionListener myPopMenuListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("수정")) {
				Department upDpt = pList.getSelectedItem();
				updateIdx = pList.getSelectedRowIdx();
				pDepartment.setItem(upDpt);
				btnAdd.setText("수정");
				pList.clearSelection();
				//System.out.println(updateIdx);
			}
			if (e.getActionCommand().equals("삭제")) {
				try {
					pList.removeRow(); //제거해라
				}catch(RuntimeException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		}
	};

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel) {
			btnCancelActionPerformed(e);
		}
		if (e.getSource() == btnAdd) {
			if(e.getActionCommand().equals("추가")) {
				btnAddActionPerformed(e);
			}else {
				btnUpdateActionPerformed(e);
			}
		}
	}
	private void btnUpdateActionPerformed(ActionEvent e) {
		Department upDpt = pDepartment.getItem();
		pList.updateRow(upDpt, updateIdx);
		btnAdd.setText("추가");
		pDepartment.clearTf();
		
		System.out.println(updateIdx);
	
	}
	protected void btnAddActionPerformed(ActionEvent e) {
		Department dpt = pDepartment.getItem();
		pList.addItem(dpt);
		pDepartment.clearTf();
	}
	protected void btnCancelActionPerformed(ActionEvent e) {
		pDepartment.clearTf();
	}
}
