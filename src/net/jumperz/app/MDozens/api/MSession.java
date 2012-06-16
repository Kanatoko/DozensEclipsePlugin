package net.jumperz.app.MDozens.api;

import net.jumperz.app.MDozens.*;
import net.jumperz.util.MStreamUtil;

import java.net.*;
import java.io.*;

public class MSession
extends MAbstractLogAgent
implements MConstants
{
private String user;
private String apiKey;
//--------------------------------------------------------------------------------
public MSession( String user, String apiKey )
{
this.user = user;
this.apiKey = apiKey;
}
//--------------------------------------------------------------------------------
//private static 
//--------------------------------------------------------------------------------
public void init()
throws IOException
{
URL url = new URL( "https://dozens.jp/api/authorize.json" );
URLConnection conn = url.openConnection();
conn.addRequestProperty( "X-Auth-User", user );
conn.addRequestProperty( "X-Auth-Key", apiKey );
debug( MStreamUtil.streamToString( conn.getInputStream() ) );
}
//--------------------------------------------------------------------------------
}