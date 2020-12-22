package com.proyecto.util;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Iterator;

import org.hibernate.Query;
import org.hibernate.Session;

@SuppressWarnings("deprecation")
public class Pruebas {

	private Session session;
	private Iterator<BigInteger> iter;
	private long batchSize;

	public void DefaultPostgresKeyServer(Session sess, long batchFetchSize) {
		this.session = sess;
		batchSize = batchFetchSize;
		iter = Collections.<BigInteger>emptyList().iterator();
	}

	@SuppressWarnings({ "unchecked",  "rawtypes" })
	public Long getNextKey() {
		if (!iter.hasNext()) {
			Query query = session.createSQLQuery(
					"SELECT nextval( 'mySchema.mySequence' ) FROM generate_series( 1, " + batchSize + " )");
			iter = (Iterator<BigInteger>) query.list().iterator();
		}
		return iter.next().longValue();
	}
}
