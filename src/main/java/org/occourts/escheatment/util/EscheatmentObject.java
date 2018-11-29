package org.occourts.escheatment.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.codec.digest.DigestUtils;
import org.occourts.escheatment.Constants;
import org.occourts.escheatment.model.User;

/**
* EscheatmentObject contains utility methods used in various areas of the application.
* $Revision: 4668 $     
* $Author: cbarrington $ 
* $Date: 2018-11-15 11:09:49 -0800 (Thu, 15 Nov 2018) $    
*/

abstract public class EscheatmentObject implements Serializable {

	// static final public Logger logger = Logger.getLogger( EscheatmentObject.class
	// );

	/**
	 * Returns the value, if not null, else the nullValue
	 *
	 * @param value
	 * @param nullValue
	 * @return
	 */
	static public Object nvl(Object value, Object nullValue) {
		return (value == null) ? nullValue : value;
	}

	static public String emptyToNull(String s) {
		return (s != null) ? ((s.trim().length() < 1) ? null : s) : s;
	}

	/**
	 * Returns the equalValue, if str1 and str2 are equal, otherwise str1 is
	 * returned
	 *
	 * @param str1       String 1
	 * @param str2       String 2
	 * @param equalValue Equal Value
	 * @return
	 */
	static public String evl(String str1, String str2, String equalValue) {
		return (str1 == str2 || str1.equals(str2)) ? equalValue : str1;
	}

	/**
	 * Returns the equalValue, if str1 and str2 are equal (case-insensitive),
	 * otherwise str1 is returned
	 *
	 * @param str1       String 1
	 * @param str2       String 2
	 * @param equalValue Equal Value
	 * @return
	 */
	static public String evlIgnoreCase(String str1, String str2, String equalValue) {
		return (str1 == str2 || str1.equalsIgnoreCase(str2)) ? equalValue : str1;
	}

	/**
	 * Computes SHA1 for given input and returns as Base64 encoded string. SHA1 is
	 * one-way hash algorithm that transforms input of any size into a (fixed-size)
	 * 40-byte output.
	 *
	 * @param input Input to be converted
	 * @return Base64 encoded SHA1 output of length 40
	 */
	static public String toSHA1(String input) {
		return DigestUtils.shaHex(input).toUpperCase();
	}

	/**
	 * Converts given milliseconds into a printable string format.
	 *
	 * @param duration milliseconds
	 * @return formatted printable milliseconds
	 */
	static public String toDuration(long duration) {
		long millis = duration % 1000;
		long secs = (duration / 1000) % 60;
		long mins = (duration / 1000) / 60;
		return (mins + "m " + secs + "s " + millis + "ms  -- (" + duration + "ms)");
	}

	/**
	 * Converts a given object to a date. Throws exception if unable to convert.
	 * Null inputs cause null return.
	 *
	 * @param obj object to be converted.
	 * @return the date
	 */
	static public java.util.Date toDate(Object obj) {
		if ((obj == null) || (obj instanceof java.util.Date)) {
			return (java.util.Date) obj;
		}

		if (obj instanceof oracle.sql.Datum) {
			try {
				return ((oracle.sql.Datum) obj).timestampValue();
			} catch (SQLException sqle) {
				throw new RuntimeException(sqle);
			}
		} else if (obj instanceof Calendar) {
			return ((Calendar) obj).getTime();
		} else if (obj instanceof String) {
			try {
				return new SimpleDateFormat("MM/dd/yyyy").parse((String) obj);
			} catch (ParseException pe) {
			}
			try {
				return new SimpleDateFormat("MM/dd/yyyy HH:mm").parse((String) obj);
			} catch (ParseException pe) {
			}
			try {
				return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse((String) obj);
			} catch (ParseException pe) {
			}
			throw new ClassCastException("Unknown date format: " + obj.toString());
		} else {
			throw new ClassCastException("Unknown type of the object: " + obj.getClass());
		}
	}

	/**
	 * Converts a given object to a calendar. Throws exception if unable to convert.
	 * Null inputs cause null return.
	 *
	 * @param obj object to be converted.
	 * @return the calendar
	 */
	static public Calendar toCalendar(Object obj) {
		if ((obj == null) || (obj instanceof Calendar)) {
			return (Calendar) obj;
		}
		if (obj instanceof oracle.sql.Datum) {
			Calendar cal = Calendar.getInstance();
			try {
				cal.setTime(((oracle.sql.Datum) obj).timestampValue());
				return cal;
			} catch (SQLException sqle) {
				throw new RuntimeException(sqle);
			}
		} else if (obj instanceof java.util.Date) {
			Calendar cal = Calendar.getInstance();
			cal.setTime((java.util.Date) obj);
			return cal;
		} else if (obj instanceof String) {
			// TODO
			throw new ClassCastException("Unknown type of the object: " + obj.getClass());
		} else {
			throw new ClassCastException("Unknown type of the object: " + obj.getClass());
		}
	}

	/**
	 * Converts a given object to a string. Null inputs cause null return.
	 *
	 * @param obj object to be converted.
	 * @return the string
	 */
	static public String toString(Object obj) {
		if (null == obj) {
			return null;
		}

		if (obj instanceof java.io.Reader) {
			java.io.Reader reader = (java.io.Reader) obj;
			char[] buff = new char[10 * 1024 * 1024];
			StringWriter string = null;
			try {
				string = new StringWriter();
				int count = 0;
				while (-1 != (count = reader.read(buff))) {
					if (count > 0) {
						string.write(buff, 0, count);
					}
				}
				string.flush();
				return string.toString();
			} catch (IOException ioe) {
				throw new RuntimeException(ioe);
			} finally {
				release(string);
			}
		}

		return obj.toString();
	}

	static public byte[] toBytes(Object obj) {
		if (null == obj) {
			return null;
		}

		if (obj instanceof InputStream) {
			InputStream in = (InputStream) obj;
			byte[] buff = new byte[10 * 1024 * 1024];
			ByteArrayOutputStream out = null;
			try {
				out = new ByteArrayOutputStream();
				int count = 0;
				while (-1 != (count = in.read(buff))) {
					if (count > 0) {
						out.write(buff, 0, count);
					}
				}
				out.flush();
				return out.toByteArray();
			} catch (IOException ioe) {
				throw new RuntimeException(ioe);
			} finally {
				release(out);
			}
		}

		return (byte[]) obj;
	}

	/**
	 * Converts a given object to a short. Throws exception if unable to convert.
	 * Null inputs cause null return.
	 *
	 * @param obj object to be converted.
	 * @return the short
	 */
	static public Short toShort(Object obj) {
		if ((obj == null) || (obj instanceof Short)) {
			return (Short) obj;
		}
		if (obj instanceof Number) {
			return ((Number) obj).shortValue();
		} else if (obj instanceof String) {
			BigDecimal number = new BigDecimal(obj.toString());
			return number.shortValue();
		} else {
			throw new ClassCastException("Unknown type of the object: " + obj.getClass());
		}
	}

	/**
	 * Converts a given object to an integer. Throws exception if unable to convert.
	 * Null inputs cause null return.
	 *
	 * @param obj object to be converted.
	 * @return the integer
	 */
	static public Integer toInteger(Object obj) {
		if ((obj == null) || (obj instanceof Integer)) {
			return (Integer) obj;
		}
		if (obj instanceof Number) {
			return ((Number) obj).intValue();
		} else if (obj instanceof String) {
			BigDecimal number = new BigDecimal(obj.toString());
			return number.intValue();
		} else {
			throw new ClassCastException("Unknown type of the object: " + obj.getClass());
		}
	}

	/**
	 * Converts a given object to a long. Throws exception if unable to convert.
	 * Null inputs cause null return.
	 *
	 * @param obj object to be converted.
	 * @return the long
	 */
	static public Long toLong(Object obj) {
		if ((obj == null) || (obj instanceof Long)) {
			return (Long) obj;
		}
		if (obj instanceof Number) {
			return ((Number) obj).longValue();
		} else if (obj instanceof String) {
			BigDecimal number = new BigDecimal(obj.toString());
			return number.longValue();
		} else {
			throw new ClassCastException("Unknown type of the object: " + obj.getClass());
		}
	}

	/**
	 * Converts a given object to a big decimal. Throws exception if unable to
	 * convert. Null inputs cause null return.
	 *
	 * @param obj object to be converted.
	 * @return the big decimal
	 */
	static public BigDecimal toBigDecimal(Object obj) {
		if ((obj == null) || (obj instanceof BigDecimal)) {
			return (BigDecimal) obj;
		}
		if (obj instanceof Number) {
			return new BigDecimal(((Number) obj).doubleValue());
		} else if (obj instanceof String) {
			return new BigDecimal(obj.toString());
		} else {
			throw new ClassCastException("Unknown type of the object: " + obj.getClass());
		}
	}

	static public String dateToString(java.util.Date date) {
		return dateToString(date, "MM/dd/yyyy");
	}

	static public String dateToString(java.util.Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	static public java.util.Date truncateTime(java.util.Date date) {
		return toDate(dateToString(date));
	}

	static public java.util.Date removeTime(java.util.Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * Splits a list into arrays of given sizes. Length of the list must be greater
	 * or equal to the sum of given sizes.
	 *
	 * @param row   The list to be split
	 * @param sizes Split arrays' sizes
	 * @return Array constructed from given list
	 */
	static public Object[][] split(List<Object> row, int[] sizes) {
		Object[][] tables = new Object[sizes.length][];

		for (int i = 0; i < sizes.length; i++) {
			tables[i] = new Object[sizes[i]];
		}

		int column = 0;
		for (Object[] table : tables) {
			for (int i = 0; i < table.length; i++) {
				table[i] = row.get(column++);
			}
		}

		return tables;
	}

	/**
	 * Rolls back (if applicable) and closes the resource. Any errors are suppressed
	 * if thrown. Accepts objects of following types: <br/>
	 * <br/>
	 * <code>java.io.Closeable</code> <br/>
	 * <code>java.sql.ResultSet</code> <br/>
	 * <code>java.sql.Statement</code> <br/>
	 * <code>java.sql.Connection</code> <br/>
	 * <code>javax.persistence.Transaction</code> <br/>
	 * <code>javax.persistence.DbFactory</code> <br/>
	 * <code>com.filenet.is.ra.cci.FN_IS_CciInteraction</code> <br/>
	 * <code>com.filenet.is.ra.cci.FN_IS_CciConnection</code> <br/>
	 * <code>com.novell.ldap.LDAPConnection</code>
	 *
	 * @param resource Resource object to be closed/released
	 */
	static public void release(Object resource) {
		if (null == resource) {
			// do nothing
			return;
		}

		if (resource instanceof java.sql.Connection) {
			// Connection type of object, attempt rollback and close
			java.sql.Connection conn = (java.sql.Connection) resource;
			try {
				conn.rollback();
			} catch (Exception e) {
			} finally {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}

		} else if (resource instanceof Closeable) {
			// Closeable type of object (i.e., streams), just close it
			try {
				((Closeable) resource).close();
			} catch (Exception e) {
			}

		} else if (resource instanceof java.sql.ResultSet) {
			// ResultSet type of object, just close it
			try {
				((java.sql.ResultSet) resource).close();
			} catch (Exception e) {
			}

		} else if (resource instanceof java.sql.Statement) {
			// Statement type of object, just close it
			try {
				((java.sql.Statement) resource).close();
			} catch (Exception e) {
			}

		} else if (resource instanceof com.novell.ldap.LDAPConnection) {
			// com.novell.ldap.LDAPConnection type of object, disconnect it
			try {
				((com.novell.ldap.LDAPConnection) resource).disconnect();
			} catch (Exception e) {
			}

		} else {
			// raise exception, given object is of unknown type to us
			throw new ClassCastException("Unsupported resource type: " + resource.getClass().getName());

		}
	}

	/**
	 * Loads a SQL from a .sql file residing in the org.occourts.elf.civil.sql
	 * package. The file must contain a valid SQL string.
	 *
	 * @param filename name of the file (minus extension .sql).
	 * @return the SQL string
	 */
	static public String loadSql(String filename) {
		InputStream in = null;
		BufferedReader file = null;

		try {
			in = EscheatmentObject.class.getClassLoader()
					.getResourceAsStream("org/occourts/elf/civil/sql/" + filename + ".sql");
			file = new BufferedReader(new InputStreamReader(in));
			String line = file.readLine();
			StringBuilder sql = new StringBuilder(line);
			while (null != (line = file.readLine())) {
				sql.append("\n");
				sql.append(line);
			}
			return sql.toString();
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		} finally {
			EscheatmentObject.release(in);
			EscheatmentObject.release(file);
		}
	}

	static public String setSqlVar(String sql, String name, String value) {
		return sql.replace("/*" + name + "*/", value);
	}

	static public String escapeSqlVarchar(String varchar) {
		return (null == varchar) ? varchar : varchar.replace("'", "''");
	}

	/**
	 * Loads text contents of a resource file residing in the
	 * org.occourts.elf.civil.res package. The file must be a non-empty text file.
	 *
	 * @param filename name of the file plus extension (e.g.,: DocTypes.txt)
	 * @return the text contents of the file
	 */
	static public String loadResource(String filename) {
		InputStream in = null;
		BufferedReader file = null;

		try {
			in = EscheatmentObject.class.getClassLoader().getResourceAsStream("org/occourts/elf/civil/res/" + filename);
			file = new BufferedReader(new InputStreamReader(in));
			String line = file.readLine();
			StringBuilder txt = new StringBuilder(line);
			while (null != (line = file.readLine())) {
				txt.append("\n");
				txt.append(line);
			}
			return txt.toString();
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		} finally {
			EscheatmentObject.release(in);
			EscheatmentObject.release(file);
		}
	}

	static public String getResource(ResourceBundle bundle, String key) {
		try {
			return bundle.getString(key);
		} catch (MissingResourceException mre) {
			return null;
		}
	}

	static public String toMacAddress(byte[] mac) {
		if (mac == null || mac.length < 1) {
			return null;
		}

		String addr = "";
		for (byte b : mac) {
			String hex = Integer.toHexString(b).toUpperCase();
			addr += (((hex.length() == 1) ? ("0" + hex)
					: ((hex.length() > 2) ? (hex.substring(hex.length() - 2)) : (hex))) + ":");
		}
		if (addr.endsWith(":")) {
			addr = addr.substring(0, addr.length() - 1);
		}
		return addr;
	}

	static public String toIpAddress(byte[] ip) {
		if (ip == null || ip.length < 1) {
			return null;
		}

		String addr = "";
		for (byte b : ip) {
			addr += (((b < 0) ? (256 + b) : b) + ".");
		}
		if (addr.endsWith(".")) {
			addr = addr.substring(0, addr.length() - 1);
		}
		return addr;
	}

	static public String toHostName() {
		try {
			InetAddress ip = InetAddress.getLocalHost();
			return ip.getCanonicalHostName() + " [" + toIpAddress(ip.getAddress()) + "]";
		} catch (Exception e) {
			return null;
		}
	}

	static public boolean isAdmin(User loggedinUser) {
		return loggedinUser!=null && 
				loggedinUser.getUserRoleAdmin()!=null && 
				loggedinUser.getUserRoleAdmin().intValue() >= Constants.FUNC_ADMIN_ROLE;
	}	
	
	static public String getBaseFilePath() {
		String path = SpringBeanUtil.getInstance().getProperty(Constants.PDF_OUTPUT_PATH);
		return path;
	}
	
}
