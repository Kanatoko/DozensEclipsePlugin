package net.jumperz.app.MDozens.test;

import net.jumperz.app.MDozens.api.*;

public class MTest
{
//--------------------------------------------------------------------------------
public static void main( String[] args )
throws Exception
{
if( args.length != 2 )
	{
	System.err.println( "Usage: java net.jumperz.app.MDozens.test.MTest user apiKey" );
	return;
	}

MSession session = new MSession( args[ 0 ], args[ 1 ] );
session.init();
}
//--------------------------------------------------------------------------------
}