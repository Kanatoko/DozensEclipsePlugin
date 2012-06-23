/*
 * Created on Jun 23, 2012
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.jumperz.app.MDozens;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.console.IConsoleConstants;


public class MPerspectiveFactory1 implements IPerspectiveFactory
{

public void createInitialLayout( IPageLayout layout )
{
String editorArea = layout.getEditorArea();
layout.setEditorAreaVisible( false );

IFolderLayout leftFolder	= layout.createFolder( "left", IPageLayout.LEFT, 0.35f, editorArea );
IFolderLayout rightFolder	= layout.createFolder( "right", IPageLayout.LEFT, 0.65f, editorArea );
IFolderLayout bottomFolder	= layout.createFolder( "bottom", IPageLayout.BOTTOM, 0.75f, "right"  );

leftFolder.addView( net.jumperz.app.MDozens.view.MDomainView.class.getName() );
rightFolder.addView( net.jumperz.app.MDozens.view.MRecordView.class.getName() );
bottomFolder.addView( IConsoleConstants.ID_CONSOLE_VIEW );

}

}
