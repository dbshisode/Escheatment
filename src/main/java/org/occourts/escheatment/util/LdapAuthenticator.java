package org.occourts.escheatment.util;

import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPSearchResults;

/**
* LdapAuthenticator contains methods necessary to connect to a domain controller using LDAP 
* for user authentication (validation of user password stored in AD)
* $Revision: 4529 $     
* $Author: cbarrington $ 
* $Date: 2018-10-05 11:12:33 -0700 (Fri, 05 Oct 2018) $    
*/

public class LdapAuthenticator extends EscheatmentObject {
	public String getHost() {
		return (host == null || host.trim().length() < 1) ? getDefaultHost() : host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return (port != null) ? port : (secure ? getDefaultSecurePort() : getDefaultPort());
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public boolean isSecure() {
		return secure;
	}

	public void setSecure(boolean secure) {
		if (secure) {
			throw new UnsupportedOperationException("Secure LDAP service is not currently supported.");
		}
		this.secure = secure;
	}

	public String getUid() {
		return (uid == null || uid.trim().length() < 1) ? getDefaultUid() : uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getSearchRoot() {
		return (searchRoot == null || searchRoot.trim().length() < 1) ? getDefaultSearchRoot() : searchRoot;
	}

	public void setSearchRoot(String searchRoot) {
		this.searchRoot = searchRoot;
	}

	public boolean authenticate(String user, char[] pass) throws Exception {
		synchronized (lock) {
			LDAPConnection lc = new LDAPConnection();
			/*
			 * if ( secure ) { // secure socket factory lc = new LDAPConnection( ssf ); }
			 */

			try {
				// Connect to the server.
				lc.connect(getHost(), getPort());

				String criteria = getUid() + "=" + user;
				LDAPSearchResults results = lc.search(getSearchRoot(), LDAPConnection.SCOPE_SUB, criteria,
						new String[] { "GroupMembership", "userPassword", "password", "pass", "sn", "UID" }, false);

				if (!results.hasMore()) {
					return false;
				}

				List<LDAPEntry> entries = new Vector<LDAPEntry>();
				while (results.hasMore()) {
					entries.add(results.next());
				}

				if (entries.size() > 1) {
					throw new Exception("Multiple DN's found: " + entries.size());
				}

				try {
					lc.bind(LDAPConnection.LDAP_V3, entries.get(0).getDN(), new String(pass).getBytes("UTF8"));
					return lc.isBound();
				} catch (LDAPException ldape) {
					// logger.info( "LDAPException", ldape );
					if (LDAPException.INVALID_CREDENTIALS == ldape.getResultCode()) {
						return false;
					}
					throw new Exception("LDAP failure", ldape);
				}

			} finally {
				release(lc);
			}
		}
	}

	public List<LdapPerson> findPersonByLastName(String lastName) throws Exception {
		final String[] attr = new String[] { "cn", "title", "fullName", "givenName", "sn", "mail", "ou" };

		synchronized (lock) {
			LDAPConnection lc = new LDAPConnection();
			/*
			 * if ( secure ) { // secure socket factory lc = new LDAPConnection( ssf ); }
			 */

			try {
				// Connect to the server.
				lc.connect(getHost(), getPort());

				String criteria = "(&(objectClass=person)(sn=" + lastName + "*))";
				LDAPSearchResults results = lc.search(getSearchRoot(), LDAPConnection.SCOPE_SUB, criteria, attr, false);

				List<LdapPerson> persons = new Vector<LdapPerson>();
				while (results.hasMore()) {
					LDAPEntry result = results.next();

					persons.add(new LdapPerson(getValue(result.getAttribute(attr[0])),
							getValue(result.getAttribute(attr[1])), getValue(result.getAttribute(attr[2])),
							getValue(result.getAttribute(attr[3])), getValue(result.getAttribute(attr[4])),
							getValue(result.getAttribute(attr[5])), getValue(result.getAttribute(attr[6]))));
				}

				return persons;
			} finally {
				release(lc);
			}
		}
	}

	static protected String getValue(LDAPAttribute attr) {
		return attr == null ? null : attr.getStringValue();
	}

	protected String getAuthProperty(String key) {
		try {
			return authProperties.getString(key);
		} catch (java.util.MissingResourceException mre) {
			return null;
		}
	}

	protected int getDefaultPort() {
		return LDAPConnection.DEFAULT_PORT;
	}

	protected int getDefaultSecurePort() {
		return LDAPConnection.DEFAULT_SSL_PORT;
	}

	protected String getDefaultHost() {
		return "172.22.16.42"; // "CJCMastr.occourts.org";
	}

	protected String getDefaultUid() {
		return "cn";
	}

	protected String getDefaultSearchRoot() {
		return "o=OCSC";
	}

	protected LdapAuthenticator() {
		String value = getAuthProperty("ldap.host");
		if (value != null && value.trim().length() > 0) {
			setHost(value);
		}

		value = getAuthProperty("ldap.port");
		if (value != null && value.trim().length() > 0) {
			setPort(toInteger(value));
		}

		value = getAuthProperty("ldap.secure");
		if (value != null && value.trim().length() > 0) {
			setSecure("true".equalsIgnoreCase(value));
		}

		value = getAuthProperty("ldap.uid");
		if (value != null && value.trim().length() > 0) {
			setUid(value);
		}

		value = getAuthProperty("ldap.searchRoot");
		if (value != null && value.trim().length() > 0) {
			setSearchRoot(value);
		}
	}

	public class LdapPerson {

		public String getUserId() {
			return userId;
		}

		public String getTitle() {
			return title;
		}

		public String getFullName() {
			return fullName;
		}

		public String getFirstName() {
			return firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public String getEmail() {
			return email;
		}

		public String getDepartment() {
			return department;
		}

		public LdapPerson(String userId, String title, String fullName, String firstName, String lastName, String email,
				String department) {
			this.userId = userId;
			this.title = title;
			this.fullName = fullName;
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
			this.department = department;
		}

		private String userId;
		private String title;
		private String fullName;
		private String firstName;
		private String lastName;
		private String email;
		private String department;
	}

	final private Object lock = new Object();
	protected String host;
	protected Integer port;
	protected boolean secure;
	protected String uid;
	protected String searchRoot;
	static final ResourceBundle authProperties = ResourceBundle.getBundle("escheatment");

}
