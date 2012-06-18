package net.jumperz.app.MDozens.api;

import net.jumperz.app.MDozens.*;
import net.jumperz.util.MStreamUtil;
import net.arnx.jsonic.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class MSession
extends MAbstractLogAgent
implements MConstants
{
private String user;
private String apiKey;

private String authToken;
//--------------------------------------------------------------------------------
public MSession( String user, String apiKey )
{
this.user = user;
this.apiKey = apiKey;
}
//--------------------------------------------------------------------------------
private Map getApiResponse( String apiPath, Map header )
throws IOException
{
URL url = new URL( "https://dozens.jp" + apiPath );
URLConnection conn = url.openConnection();
Iterator p = header.keySet().iterator();
while( p.hasNext() )
	{
	String key = ( String )p.next();
	String value = ( String )header.get( key );
	conn.addRequestProperty( key, value );
	debug( key + ":" + value );
	}
conn.addRequestProperty( "Connection", "close" );

System.out.println( "--1--" );
HttpURLConnection urlConn = ( HttpURLConnection )conn;
int statusCode = urlConn.getResponseCode();
if( statusCode != 200 )
	{
	throw new IOException( "Auth failed: statusCode=" + statusCode );
	}

System.out.println( "--2--" );
String responseBody = MStreamUtil.streamToString( conn.getInputStream() );
System.out.println( responseBody );
System.out.println( "--3--" );
Map result = ( Map )JSON.decode( responseBody );
System.out.println( result );
debug( result );
return result;
}
//--------------------------------------------------------------------------------
public void init()
throws IOException
{
Map header = new HashMap();
header.put( "X-Auth-User", user );
header.put( "X-Auth-Key", apiKey );

Map result = getApiResponse( "/api/authorize.json", header );
if( !result.containsKey( "auth_token" ) )
	{
	throw new IOException( "Auth failed: auth_token not found. " + result );
	}
authToken = ( String )result.get( "auth_token" );
System.out.println( authToken );
debug( authToken );
}
//--------------------------------------------------------------------------------
public Map getZone()
throws IOException
{
Map header = new HashMap();
header.put( "X-Auth-Token", authToken );
Map result = getApiResponse( "/api/zone.json", header );
return result;
}
//--------------------------------------------------------------------------------
}