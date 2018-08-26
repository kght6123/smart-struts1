package jp.kght6123.smart.struts1.struct;

import java.io.Serializable;
import java.sql.Connection;

import jp.kght6123.smart.struts1.form.FormImpl;

import org.slf4j.Logger;

public class Structure <F extends FormImpl> implements AutoCloseable, Serializable {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** コネクション */
	private Connection connection;
	
	/** ロガー */
	private final Logger logger;
	
	/** Form */
	private final F form;
	
	public Structure(final F form, final Connection connection, final Logger logger) {
		super();
		this.form = form;
		this.connection = connection;
		this.logger = logger;
	}

	private void connectionClose() throws Exception {
		if (this.connection != null && !this.connection.isClosed()) {
			this.connection.rollback();
			this.connection.close();
			this.connection = null;
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	public Logger getLogger() {
		return logger;
	}
	public F getForm() {
		return form;
	}
	
	@Override
	public void close() throws Exception {
		try {
			connectionClose();
		} catch (Exception e) {
			logger.error("Connection close error.", e);
		}
	}
}