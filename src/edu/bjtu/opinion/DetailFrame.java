package edu.bjtu.opinion;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class DetailFrame implements MouseListener, Listener{

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private Label label = null;
	private Label label1 = null;
	private Label label2 = null;
	private Label label3 = null;
	private Label label5 = null;
	private Label label6 = null;
	private CLabel cLabelTitle = null;
	private CLabel cLabelAuthor = null;
	private CLabel cLabelTime = null;
	private CLabel cLabelID = null;
	private CLabel cLabelURL = null;
	private Text textAreaContent = null;
	private Button buttonLast = null;
	private Button buttonNext = null;
	private CLabel cLabel = null;
	private Button buttonFirst = null;
	private Button buttonPrev = null;
	private Table table = null;
	/**
	 * id, title, author, time, content, renum, url
	 */
	private String[] texts = null;
	private int type = 0;
	private int totalPage = 0;
	private int currPage = 0;
	public DetailFrame(){
		texts = new String[]{"...", "...", "...", "...", "...", "..."};
		createSShell();		
	}	
	
	public DetailFrame(String[] texts, int type){
		this.type = type + 1;
		this.texts = texts;
		createSShell();
	}
	
	public void show(){
		Display display = Display.getDefault();
		sShell.open();
		DBUtils utils = DBUtils.getInstance();
		totalPage = utils.getPages(type, texts[0]);
		currPage = 0;		
		showExtraction();
		while (!sShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
	
	public void showPost(){
		Display display = Display.getDefault();
		Point size = sShell.getSize();
		size.y = 151;
		sShell.setSize(size);
		sShell.open();
		while (!sShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
	
	private void showExtraction(){
		DBUtils utils = DBUtils.getInstance();
		List<String[]> list = utils.getReplies(type, texts[0], currPage);
		table.removeAll();
		for(String[] ts: list){
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(ts);
		}
		cLabel.setText("共" + totalPage + "页  第" + (currPage + 1) + "页");
	}
	
	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		sShell = new Shell(Display.getDefault(), SWT.CLOSE);
		sShell.setText("详细信息");
		sShell.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/res/detail.png")));
		sShell.setSize(new Point(640, 480));
		sShell.setLayout(null);
		label = new Label(sShell, SWT.NONE);
		label.setBounds(new Rectangle(16, 10, 30, 12));
		label.setText("标题");
		label1 = new Label(sShell, SWT.NONE);
		label1.setBounds(new Rectangle(16, 30, 30, 12));
		label1.setText("作者");
		label2 = new Label(sShell, SWT.NONE);
		label2.setBounds(new Rectangle(151, 30, 30, 12));
		label2.setText("时间");
		label6 = new Label(sShell, SWT.NONE);
		label6.setBounds(new Rectangle(356, 30, 32, 12));
		label6.setText("ID");
		label5 = new Label(sShell, SWT.NONE);
		label5.setBounds(new Rectangle(16, 50, 30, 12));
		label5.setText("地址");
		label3 = new Label(sShell, SWT.NONE);
		label3.setBounds(new Rectangle(16, 70, 30, 12));
		label3.setText("内容");
		cLabelTitle = new CLabel(sShell, SWT.NONE);
		cLabelTitle.setText(texts[1].replaceAll("\\n", "").replaceAll("\\r", ""));
		cLabelTitle.setBounds(new Rectangle(48, 8, 567, 18));
		cLabelAuthor = new CLabel(sShell, SWT.NONE);
		cLabelAuthor.setText(texts[2]);
		cLabelAuthor.setBounds(new Rectangle(48, 28, 100, 18));
		cLabelTime = new CLabel(sShell, SWT.NONE);
		cLabelTime.setText(texts[3]);
		cLabelTime.setBounds(new Rectangle(178, 28, 177, 18));
		cLabelID = new CLabel(sShell, SWT.NONE);
		cLabelID.setText(texts[0]);
		cLabelID.setBounds(new Rectangle(392, 28, 222, 18));
		cLabelURL = new CLabel(sShell, SWT.NONE);
		cLabelURL.setText(texts[5]);
		cLabelURL.setBounds(new Rectangle(48, 48, 567, 18));
		textAreaContent = new Text(sShell, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		textAreaContent.setBounds(new Rectangle(48, 68, 566, 54));
		textAreaContent.setEditable(false);
		textAreaContent.setText(texts[4]);
		table = new Table(sShell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setBounds(new Rectangle(2, 151, 627, 300));
		table.addListener(SWT.MouseDoubleClick, this);
		int[] widths = new int[] { 30, 100, 100, 100, 100, 100 };
		String[] texts = new String[] { "ID", "标题", "回复作者", "回复时间", "回复内容", "地址" };
		int columnCount = widths.length;
		for (int index = 0; index < columnCount; index++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setWidth(widths[index]);
			column.setText(texts[index]);
			// column.pack();
		}	

		buttonFirst = new Button(sShell, SWT.NONE);
		buttonFirst.setBounds(new Rectangle(297, 125, 75, 25));
		buttonFirst.setText("首页");
		buttonFirst.addMouseListener(this);
		buttonPrev = new Button(sShell, SWT.NONE);
		buttonPrev.setBounds(new Rectangle(381, 125, 75, 25));
		buttonPrev.setText("上一页");
		buttonPrev.addMouseListener(this);
		buttonNext = new Button(sShell, SWT.NONE);
		buttonNext.setBounds(new Rectangle(465, 125, 75, 25));
		buttonNext.setText("下一页");
		buttonNext.addMouseListener(this);
		buttonLast = new Button(sShell, SWT.NONE);
		buttonLast.setBounds(new Rectangle(549, 125, 75, 25));
		buttonLast.setText("尾页");
		buttonLast.addMouseListener(this);
		cLabel = new CLabel(sShell, SWT.NONE);
		cLabel.setText("共100页 第1页");
		cLabel.setBounds(new Rectangle(178, 128, 110, 18));
	}

	@Override
	public void mouseDoubleClick(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDown(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseUp(MouseEvent e) {
		if (e.widget.equals(buttonFirst)) {// action when click buttonFirst
			currPage = 0;
			showExtraction();
		} else if (e.widget.equals(buttonPrev)) {// action when click buttonPrev
			if (currPage > 0) {
				currPage--;
			}
			showExtraction();
		} else if (e.widget.equals(buttonNext)) {// action when click buttonNext
			if (currPage < totalPage - 1) {
				currPage++;
			}
			showExtraction();
		} else if (e.widget.equals(buttonLast)) {// action when click buttonLast
			if(totalPage > 0)
				currPage = totalPage - 1;
			showExtraction();
		} else {

		}
	}

	@Override
	public void handleEvent(Event e) {
		if (e.widget.equals(table)) {
			if (table.getSelectionCount() > 0) {
				TableItem item = table.getSelection()[0];
				String[] texts = new String[] { item.getText(0), item.getText(1),
						item.getText(2), item.getText(3), item.getText(4), item.getText(5),
						item.getText(6) };
				DetailFrame frm = new DetailFrame(texts, type);
				frm.showPost();
			}
		}

	}
	
	

}
