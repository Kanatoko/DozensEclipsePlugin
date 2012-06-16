package net.jumperz.app.MDozens;

import org.eclipse.ui.part.ViewPart;
import org.eclipse.jface.action.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Composite;
import net.jumperz.util.*;
import java.util.*;
import java.io.*;

public class MUtil
{
//--------------------------------------------------------------------------------
public static  Image getImage( Device device, String imageFileName )
{
InputStream in = null;
try
	{
	in = MStreamUtil.getResourceStream( "net/jumperz/app/MDozens/resources/" + imageFileName );
	ImageData imageData = new ImageData( in );
	return new Image( device, imageData );
	}
catch( Exception e )
	{
	e.printStackTrace();
	//MEventManager.getInstance().fireErrorEvent( e );
	return null;
	}
finally
	{
	MStreamUtil.closeStream( in );
	}
}
//--------------------------------------------------------------------------------
public static boolean treeItemSelected( Tree tree, TreeItem treeItem )
{
if( treeItem == null )
	{
	return false;
	}
if( tree == null )
	{
	return false;
	}
TreeItem[] selected = tree.getSelection();
if( selected != null 
 && selected.length == 1
 && selected[ 0 ] == treeItem
 )
	{
	return true;
	}
else
	{
	return false;
	}
}
//--------------------------------------------------------------------------------
}