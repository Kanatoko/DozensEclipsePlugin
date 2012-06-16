package net.jumperz.net;

import java.io.*;
import java.net.*;

public class MParameterFactory
{
// --------------------------------------------------------------------------------
public static MAbstractParameter getParameter( String name, String value, int type, String charset )
throws IOException
{
if( type == MAbstractParameter.URI || type == MAbstractParameter.BODY )
	{
		//�����R�[�h�ɍ��킹��URL�G���R�[�h���s��
	return new MParameter( URLEncoder.encode( name, charset ), value, type );
	}
else if( type == MAbstractParameter.MULTIPART )
	{
		//name, value�ɂ�Java������String�I�u�W�F�N�g�������Ă���̂�latin1�`���ɖ߂�
		//�����R�[�h���̂ɂ͐G��Ȃ�
	name  = new String( name. getBytes( charset ), "ISO-8859-1" );
	value = new String( value.getBytes( charset ), "ISO-8859-1" );
	return new MMultipartParameter( name, value, type );
	}
else
	{
	throw new IOException( "Invalid type" );
	}
}
// --------------------------------------------------------------------------------
}