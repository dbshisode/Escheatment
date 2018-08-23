package org.occourts.escheatment.util;

import java.util.List;
import java.util.Vector;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPSearchResults;

/**
 *
 * @author v2dao
 */
public class ActiveDirectory extends LdapAuthenticator {
	public String getNtDomain() {
		return (ntDomain == null || ntDomain.trim().length() < 1) ? getDefaultNtDomain() : ntDomain;
	}

	public void setNtDomain(String ntDomain) {
		this.ntDomain = ntDomain;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	private char[] getAdminPass() {
		return adminPass;
	}

	public void setAdminPass(String adminPass) {
		this.adminPass = ((adminPass != null) ? adminPass.toCharArray() : null);
	}

	@Override
	public boolean authenticate(String user, char[] pass) throws Exception {
		synchronized (lock) {
			LDAPConnection lc = new LDAPConnection();
			/*
			 * if ( secure ) { // secure socket factory lc = new LDAPConnection( ssf ); }
			 */

			try {
				// Connect to the server.
				lc.connect(getHost(), getPort());

				try {
					lc.bind(LDAPConnection.LDAP_V3, toDistinguishedName(user), new String(pass).getBytes("UTF8"));
					return lc.isBound();
				} catch (LDAPException ldape) {
					// logger.info( "LDAPException", ldape );
					if (LDAPException.INVALID_CREDENTIALS == ldape.getResultCode()) {
						// System.out.println(getHost());
						// System.out.println(ldape.getResultCode());
						return false;
					}
					throw new Exception("LDAP failure", ldape);
				}

			} finally {
				release(lc);
			}
		}
	}

	@Override
	public List<LdapPerson> findPersonByLastName(String lastName) throws Exception {
		// final String[] attr = new String[]{ "mailNickname", "title", "fullName",
		// "givenName", "sn", "mail", "department" };
		final String[] attr = new String[] { "sAMAccountName"/* UserID */, "title", "fullName",
				"givenName"/* FirstName */, "sn"/* LastName */, "mail"/* Email */, "department" };

		synchronized (lock) {
			LDAPConnection lc = new LDAPConnection();
			/*
			 * if ( secure ) { // secure socket factory lc = new LDAPConnection( ssf ); }
			 */

			try {
				// Connect to the server.
				lc.connect(getHost(), getPort());

				// Bind needed for the MS Active Directory
				lc.bind(LDAPConnection.LDAP_V3, toDistinguishedName(getAdminId()),
						new String(getAdminPass()).getBytes("UTF8"));
				if (!lc.isBound()) {
					throw new Exception("Failed to bind as " + getAdminId());
				}

				String criteria = "(&(objectClass=person)(sn=" + lastName + "*))";
				LDAPSearchResults results = lc.search(getSearchRoot(), LDAPConnection.SCOPE_SUB, criteria, attr, false);

				List<LdapPerson> persons = new Vector<LdapPerson>();
				while (results.hasMore()) {
					try {
						LDAPEntry result = results.next();

						persons.add(new LdapPerson(getValue(result.getAttribute(attr[0])),
								getValue(result.getAttribute(attr[1])), getValue(result.getAttribute(attr[2])),
								getValue(result.getAttribute(attr[3])), getValue(result.getAttribute(attr[4])),
								getValue(result.getAttribute(attr[5])), getValue(result.getAttribute(attr[6]))));
					} catch (Exception e) {
						// ignore
					}
				}

				return persons;
			} finally {
				release(lc);
			}
		}
	}

	protected String toDistinguishedName(String user) {
		return (user == null || user.length() < 1 || getNtDomain() == null || getNtDomain().length() < 1) ? user
				: (getNtDomain() + "\\" + user);
	}

	@Override
	protected String getDefaultHost() {
		return "172.22.21.137";
	}

	@Override
	protected String getDefaultSearchRoot() {
		return "DC=ocsuperior,DC=occourts,DC=org";
	}

	protected String getDefaultNtDomain() {
		return "ocsuperior";
	}

	public ActiveDirectory() {
		String value = getAuthProperty("ad.ntDomain");
		if (value != null && value.trim().length() > 0) {
			setNtDomain(value);
		}

		value = getAuthProperty("ad.admin.id");
		if (value != null && value.trim().length() > 0) {
			setAdminId(value);
		}
		value = getAuthProperty("ad.admin.pass");
		if (value != null && value.trim().length() > 0) {
			setAdminPass(value);
		}
	}

	final private Object lock = new Object();
	protected String ntDomain;
	protected String adminId;
	private char[] adminPass;
}
