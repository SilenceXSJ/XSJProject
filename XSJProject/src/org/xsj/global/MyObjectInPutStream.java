package org.xsj.global;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

public class MyObjectInPutStream  extends ObjectInputStream{

	protected MyObjectInPutStream() throws IOException, SecurityException {
		super();
	}
	public MyObjectInPutStream(InputStream in) throws IOException{
		super(in);
	}
	@Override
	protected void readStreamHeader() throws IOException,
			StreamCorruptedException {
		// TODO Auto-generated method stub
//		super.readStreamHeader();
	}
}
