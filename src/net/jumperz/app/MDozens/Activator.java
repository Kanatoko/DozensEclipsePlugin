package net.jumperz.app.MDozens;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator
extends AbstractUIPlugin
{
public static final String PLUGIN_ID = "net.jumperz.app.MDozens";
private static Activator plugin;
private volatile Shell shell;
//--------------------------------------------------------------------------------
public synchronized void setShell( Shell s )
{
if( shell == null )
	{
	shell = s;
	//MMenuManager.getInstance().initMenus();
	}
}
//--------------------------------------------------------------------------------
public Activator()
{
}
//--------------------------------------------------------------------------------
public void start( BundleContext context )
throws Exception
{
super.start(context);
plugin = this;
}
//--------------------------------------------------------------------------------
public void stop( BundleContext context )
throws Exception
{
plugin = null;
super.stop(context);
}
//--------------------------------------------------------------------------------
public static Activator getDefault()
{
return plugin;
}
//--------------------------------------------------------------------------------
}
