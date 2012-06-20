package net.jumperz.app.MDozens.view;

import java.util.*;
import java.util.List;

import net.jumperz.app.MDozens.MContext;
import net.jumperz.app.MDozens.api.MSession;
import net.jumperz.app.MDozens.dialog.MLoginDialog;
import net.jumperz.gui.MSwtUtil;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.DialogMessageArea;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import net.jumperz.util.*;

public class MRecordView
extends MAbstractView
implements MObserver1
{
public static MRecordView instance;

private Action loginAction;
private MContext context = MContext.getInstance();
private List columnNameList;

//--------------------------------------------------------------------------------
public MRecordView()
{
instance = this;
context.register1( instance );
}
//--------------------------------------------------------------------------------
public void init2()
{
parent.setLayout( new FormLayout() );

table = new Table( parent, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI );
table.addMouseListener(new MouseAdapter() {
	@Override
	public void mouseDown(MouseEvent e) {
	onMouseDown( e );
	}
});
table.setHeaderVisible( true );
table.setLinesVisible( true );
FormData d1 = new FormData();
d1.top = new FormAttachment(0);
d1.left = new FormAttachment( 0, 1 );
d1.right = new FormAttachment( 100, -1 );
d1.bottom = new FormAttachment( 100, -1 );
table.setLayoutData( d1 );

menuManager = new MenuManager();
Menu contextMenu = menuManager.createContextMenu( table );
table.setMenu( contextMenu );
}
//--------------------------------------------------------------------------------
private void onMouseDown( MouseEvent e )
{
}
//--------------------------------------------------------------------------------
public void setFocus()
{
}
//--------------------------------------------------------------------------------
private void onRecord( final Map recordMap )
{
debug( recordMap );

shell.getDisplay().asyncExec( new Runnable(){ public void run()	{//-----

columnNameList = new ArrayList();
clearTableSwt();

List recordList = ( List )recordMap.get( "record" );
if( recordList.size() > 0 )
	{
		//draw table column
	Map firstRecord = ( Map )recordList.get( 0 );
	Iterator p = firstRecord.keySet().iterator();
	while( p.hasNext() )
		{
		String key = ( String )p.next();
		TableColumn column = new TableColumn( table, SWT.NONE );
		column.setText( key );	
		columnNameList.add( key );
		}
	
	MSwtUtil.getTableColumnWidthFromProperties2( "recordTable" , table, prop );
	
	for( int i = 0; i < recordList.size(); ++i )
		{
		Map recordData = ( Map )recordList.get( i );
		TableItem item = new TableItem( table, SWT.NONE );
		
		for( int k = 0; k < columnNameList.size(); ++k )
			{
			item.setText( k, ( String )recordData.get( columnNameList.get( k ) ) );
			}
		}
	}

}});//-----
}

//--------------------------------------------------------------------------------
public void update()
{
int state = context.getState();
if( state == STATE_RECORD_SUCCESS )
	{
	Map recordMap = context.getRecordMap();
	onRecord( recordMap );
	}
}
//--------------------------------------------------------------------------------
}
