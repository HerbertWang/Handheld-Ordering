package com.alex.food.utils;

import java.io.IOException;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

/**
 * 
 * @author ALEX
 *
 */
public class ExtendedSoapSerializationEnvelope extends
		SoapSerializationEnvelope {

	public ExtendedSoapSerializationEnvelope(int version) {
		super(version);
	}

	@Override
	protected void writeProperty(XmlSerializer writer, Object object,
			PropertyInfo type) throws IOException {
		if (null == object) {
			writer.attribute(xsi, "nil", "true");
			return;
		}
		super.writeProperty(writer, object, type);
	}

	// @Override
	// public void write(XmlSerializer writer) throws IOException {
	//
	// writer.setPrefix("i", xsi);
	// writer.setPrefix("d", xsd);
	// writer.setPrefix("c", enc);
	// writer.setPrefix("v", env);
	// writer.startTag(env, "Envelope");
	// writer.startTag(env, "Header");
	// writeHeader(writer);
	// writer.endTag(env, "Header");
	// writer.startTag(env, "Body");
	// writeBody(writer);
	// writer.endTag(env, "Body");
	// writer.endTag(env, "Envelope");
	//
	// // super.write(writer);
	// }
}
