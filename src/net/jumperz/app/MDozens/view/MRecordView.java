package net.jumperz.app.MDozens.view;

import java.util.*;
import java.util.List;

import net.jumperz.app.MDozens.MContext;
import net.jumperz.app.MDozens.dialog.MLoginDialog;
import net.jumperz.gui.MSwtUtil;
import net.jumperz.net.dozens.MSession;

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

private Action deleteAction;
private MContext context = MContext.getInstance();
private List columnNameList;
private Set ttlSet = new HashSet();
//--------------------------------------------------------------------------------
public MRecordView()
{
instance = this;
context.register1( instance );

ttlSet.add( "60" );
ttlSet.add( "900" );
ttlSet.add( "3600" );
ttlSet.add( "7200" );
ttlSet.add( "86400" );
}
//--------------------------------------------------------------------------------
private void updateGui()
{
TableItem[] items = table.getSelection();
boolean oneItem = false;
if( items.length == 1 )
	{
	oneItem = true;
	}

deleteAction.setEnabled( oneItem );
}
//--------------------------------------------------------------------------------
public void init2()
{
parent.setLayout( new FormLayout() );

table = new Table( parent, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI );
table.setHeaderVisible( true );
table.setLinesVisible( true );

table.setLayoutData(new FormData());
table.addMouseListener( new MouseAdapter() {
	@Override
	public void mouseDown(MouseEvent e) {
	onMouseDown( e );
	}
});

table.addListener( SWT.MouseDoubleClick, this );

FormData d1 = new FormData();
d1.top = new FormAttachment(0);
d1.left = new FormAttachment( 0, 1 );
d1.right = new FormAttachment( 100, -1 );
d1.bottom = new FormAttachment( 100, -1 );
table.setLayoutData( d1 );

menuManager = new MenuManager();
Menu contextMenu = menuManager.createContextMenu( table );
table.setMenu( contextMenu );

{	//deleteAction
deleteAction = new Action(){ public void run() { //--------
delete();
}};//-----
deleteAction.setText( "Delete" );
setActionImage( deleteAction, "bullet_delete.png" );
menuManager.add( deleteAction );
deleteAction.setEnabled( false );
dropDownMenu.add( deleteAction );
}

}
//--------------------------------------------------------------------------------
private void delete()
{
TableItem[] items = table.getSelection();
if( items.length != 1 )
	{
	return;
	}

MessageBox dialog = new MessageBox( shell, SWT.ICON_WARNING | SWT.OK | SWT.CANCEL);
dialog.setText("Confirm Delete");
dialog.setMessage("Do you really want to delete?");
int returnCode = dialog.open();
if( returnCode == SWT.OK )
	{
	Map recordData = ( Map )items[ 0 ].getData();
	context.deleteRecord( ( String )recordData.get( "id" ) );
	}
}
//--------------------------------------------------------------------------------
protected void onTableDoubleClick( final int x, final int y )
{

final TableEditor editor = new TableEditor( table );

shell.getDisplay().asyncExec( new Runnable(){ public void run()	{//*****

Point point = new Point( x, y );
final TableItem item = table.getItem( point );
if( item == null )
	{
	return;
	}
int columnIndex = -1;
for( int i = 0; i < table.getColumnCount(); ++i )
	{
	if( item.getBounds( i ).contains( point ) )
		{
		columnIndex = i;
		break;
		}
	}
if( columnIndex <= 2 )
	{
	return;
	}

Control control = null;
boolean isCombo = false;

boolean isTTL = false;
if( columnNameList.get( columnIndex ).equals( "ttl" ) )
	{
	isTTL = true;
	}
final boolean isTTL2 = isTTL;
/*
if( columnNameList.get( columnIndex ).equals( "ttl" ) )
	{
	isCombo = true;
	control = new Combo( table, SWT.READ_ONLY );
	Combo combo = ( Combo )control;
	combo.add( "60" );
	combo.add( "900" );
	combo.add( "3600" );
	combo.add( "7200" );
	combo.add( "86400" );
	//combo.select( 0 );
	}
else
*/
	{
	control = new Text( table, SWT.NONE | SWT.BORDER );
	Text text = ( Text )control;
	text.setText( item.getText( columnIndex ) );
	}


editor.horizontalAlignment = SWT.LEFT;
editor.grabHorizontal = true;
editor.setEditor( control, item, columnIndex );

final int selectedColumn = columnIndex;
final Control control2 = control;

Listener textListener = new Listener(){
public void handleEvent( final Event e )
{
debug( "type:" + e.type );
switch( e.type )
	{
	case SWT.FocusOut :
		//updateDocument( item, selectedColumn, clazz, text.getText() );
		//updateRecord( item, selectedColumn, control2 );
		debug( "focusout" );
		control2.dispose();
		break;
	case SWT.Modify :
		debug( "modify" );
		updateRecord( item, selectedColumn, control2, isTTL2 );
		break;
	case SWT.Traverse :
		debug( "traverse" );
		switch( e.detail )
			{
			case SWT.TRAVERSE_RETURN :
				debug( "return" );
				updateRecord( item, selectedColumn, control2, isTTL2 );
				break;
			case SWT.TRAVERSE_ESCAPE :
				debug( "escape" );
				control2.dispose();
				e.doit = false;
				break;
			}
		break;
	}
}
};

control.addListener( SWT.FocusOut, textListener );
if( isCombo )
	{
	control.addListener( SWT.Modify, textListener );
	}
else
	{
	control.addListener( SWT.Traverse, textListener );
	}
//text.selectAll();
control.setFocus();

}});//*****
}
//--------------------------------------------------------------------------------
private void updateRecord( TableItem item, int columnIndex, Control control, boolean isTTL )
{
try
	{
	String value = null;
	if( control instanceof Text )
		{
		Text text = ( Text )control;
		value = text.getText();
		}
	else
		{
		Combo combo = ( Combo )control;
		value = combo.getText();
		}
	
	if( isTTL )
		{
		if( !ttlSet.contains( value ) )
			{
			MessageBox mb = new MessageBox( shell, SWT.ICON_ERROR );
			mb.setMessage( "Invalid TTL. Choose from " + ttlSet );
			mb.setText( "TTL value error" );
			mb.open();
			return;
			}
		}
	
	String columnName = ( String )columnNameList.get( columnIndex );
	
	Map recordData = ( Map )item.getData();
	String recordId = ( String )recordData.get( "id" );
	
	context.updateRecord( recordId, columnName, value );	
	}
finally
	{
	control.dispose();
	}
}
//--------------------------------------------------------------------------------
private void onMouseDown( MouseEvent e )
{
updateGui();
}
//--------------------------------------------------------------------------------
public void setFocus()
{
}
// --------------------------------------------------------------------------------
protected void handleEvent2( Event event )
{
if( event.widget == table )
	{
	switch( event.type )
		{
		case SWT.Selection:
			onTableSelect();
			break;
		case SWT.MouseDoubleClick:
			onTableDoubleClick( event );
			break;
		}
	}
}
//--------------------------------------------------------------------------------
protected void onTableSelect()
{
updateGui();
}
//--------------------------------------------------------------------------------
private void onRecord( final Map recordMap )
{
shell.getDisplay().asyncExec( new Runnable(){ public void run()	{//-----

columnNameList = new ArrayList();
clearTableSwt();

if( !recordMap.containsKey( "record" ) )
	{
	return;
	}

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
		item.setData( recordData );
		
		for( int k = 0; k < columnNameList.size(); ++k )
			{
			String key = ( String )columnNameList.get( k );
			String value = ( String )recordData.get( key );
			if( value != null )
				{
				item.setText( k, value );
				}
			}
		}
	}

}});//-----

updateGui();
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
