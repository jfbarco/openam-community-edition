/* -*- Mode: C++; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * The contents of this file are subject to the Netscape Public
 * License Version 1.1 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.mozilla.org/NPL/
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 *
 * The Original Code is mozilla.org code.
 *
 * The Initial Developer of the Original Code is Netscape
 * Communications Corporation.  Portions created by Netscape are
 * Copyright (C) 1999 Netscape Communications Corporation. All
 * Rights Reserved.
 *
 * Contributor(s): 
 */
package com.sun.identity.shared.ldap;

import java.io.*;
import java.net.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Creates an SSL socket connection to an LDAP Server.  This class
 * implements the <CODE>LDAPSSLSocketFactoryExt</CODE> interface.
 * <P>
 *
 * To construct an object of this class, you need to specify the
 * name of a class that implements the <CODE>javax.net.ssl.SSLSocket</CODE>
 * interface.  If you do not specify a class name, the class
 * <CODE>com.sun.identity.shared.net.SSLSocket</CODE> is used by default.
 * <P>
 *
 * If you are using a Java VM that provides certificate database
 * management, you can authenticate
 * your client to a secure LDAP server by using certificates.
 * <P>
 *
 * @version 1.0
 * @see LDAPSSLSocketFactoryExt
 * @see LDAPConnection#LDAPConnection(com.sun.identity.shared.ldap.LDAPSocketFactory)
 * @deprecated As of ForgeRock OpenAM 10.
 */
public class LDAPSSLSocketFactory implements LDAPSSLSocketFactoryExt,
    java.io.Serializable {    

    static final long serialVersionUID = -3331456736649381427L;

    /**
     * Indicates if client authentication is on.
     */
    private boolean m_clientAuth = false;
    /**
     * Name of class implementing SSLSocket.
     */
    private String m_packageName = "com.sun.identity.shared.net.SSLSocket";

    /**
     * The cipher suites
     */
    private Object m_cipherSuites = null;

    /**
     * Constructs an <CODE>LDAPSSLSocketFactory</CODE> object using
     * the default SSL socket implementation,
     * <CODE>com.sun.identity.shared.net.SSLSocket</CODE>.
     */
    public LDAPSSLSocketFactory() {
    }

    /**
     * Constructs an <CODE>LDAPSSLSocketFactory</CODE> object using
     * the default SSL socket implementation,
     * <CODE>com.sun.identity.shared.net.SSLSocket</CODE>.
     *
     * @param clientAuth <CODE>true</CODE> if certificate-based client
     * authentication is desired. By default, client authentication is
     * not used.
     */
    public LDAPSSLSocketFactory(boolean clientAuth) {
        m_clientAuth = clientAuth;
    }

    /**
     * Constructs an <CODE>LDAPSSLSocketFactory</CODE> object using
     * the specified class. The class must implement the interface
     * <CODE>javax.net.ssl.SSLSocket</CODE>.
     * @param className the name of a class implementing
     * the <CODE>javax.net.ssl.SSLSocket</CODE> interface.
     * Pass <code>null</code> for this parameter to use the
     * default SSL socket implementation,
     * <CODE>com.sun.identity.shared.net.SSLSocket</CODE>.
     */
    public LDAPSSLSocketFactory(String className) {
        m_packageName = new String(className);
    }

    /**
     * Constructs an <CODE>LDAPSSLSocketFactory</CODE> object using
     * the specified class. The class must implement the interface
     * <CODE>javax.net.ssl.SSLSocket</CODE>.
     * @param className the name of a class implementing
     * the <CODE>javax.net.ssl.SSLSocket</CODE> interface.
     * Pass <code>null</code> for this parameter to use the
     * default SSL socket implementation,
     * <CODE>com.sun.identity.shared.net.SSLSocket</CODE>.
     *
     * @param clientAuth <CODE>true</CODE> if certificate-based client
     * authentication is desired. By default, client authentication is
     * not used.
     */
    public LDAPSSLSocketFactory(String className, boolean clientAuth) {
        m_packageName = new String(className);
        m_clientAuth = clientAuth;
    }

    /**
     * The constructor with the specified package for security and the specified
     * cipher suites.
     * @param className the name of a class implementing the interface
     * <CODE>javax.net.ssl.SSLSocket</CODE>.
     * Pass <code>null</code> for this parameter to use the
     * default SSL socket implementation,
     * <CODE>com.sun.identity.shared.net.SSLSocket</CODE>.
     *
     * @param cipherSuites the cipher suites to use for SSL connections
     */
    public LDAPSSLSocketFactory(String className, Object cipherSuites) {
        m_packageName = new String(className);
        m_cipherSuites = cipherSuites;
    }

    /**
     * The constructor with the specified package for security and the specified
     * cipher suites.
     * @param className the name of a class implementing the interface
     * <CODE>javax.net.ssl.SSLSocket</CODE>.
     * Pass <code>null</code> for this parameter to use the
     * default SSL socket implementation,
     * <CODE>com.sun.identity.shared.net.SSLSocket</CODE>.
     *
     * @param cipherSuites the cipher suites to use for SSL connections
     * @param clientAuth <CODE>true</CODE> if certificate-based client
     * authentication is desired. By default, client authentication is
     * not used.
     */
    public LDAPSSLSocketFactory(String className, Object cipherSuites,
      boolean clientAuth) {
        m_packageName = new String(className);
        m_cipherSuites = cipherSuites;
        m_clientAuth = clientAuth;
    }

    /**
     * Enables certificate-based client authentication for an
     * application. The application must be running in a Java VM
     * that provides transparent certificate database management.
     *
     * Call this method before you call <CODE>makeSocket</CODE>.
     * @see com.sun.identity.shared.ldap.LDAPSSLSocketFactory#isClientAuth
     * @see com.sun.identity.shared.ldap.LDAPSSLSocketFactory#makeSocket
     * Note: enableClientAuth() is deprecated. This method is replaced
     * by any one of the following constructors:
     * <p>
     * <CODE>LDAPSSLSocketFactory(boolean)</CODE>
     * <CODE>LDAPSSLSocketFactory(java.lang.String, boolean)</CODE>
     * <CODE>LDAPSSLSocketFactory(java.lang.String, java.lang.Object, boolean)</CODE>
     */
    public void enableClientAuth() {
        m_clientAuth = true;
    }


    /**
     * <B>This method is currently not implemented</B>.
     * Enables client authentication for an application that uses
     * an external (file-based) certificate database.
     * Call this method before you call <CODE>makeSocket</CODE>.
     * @param certdb the pathname for the certificate database
     * @param keydb the pathname for the private key database
     * @param keypwd the password for the private key database
     * @param certnickname the alias for the certificate
     * @param keynickname the alias for the key
     * @see com.sun.identity.shared.ldap.LDAPSSLSocketFactory#isClientAuth
     * @see com.sun.identity.shared.ldap.LDAPSSLSocketFactory#makeSocket
     * @exception LDAPException Since this method is not yet implemented,
     * calling this method throws an exception.
     * Note: <CODE>enableClientAuth(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)</CODE> is deprecated. 
     * This method is replaced by any one of the following constructors:
     * <p>
     * <CODE>LDAPSSLSocketFactory(boolean)</CODE>
     * <CODE>LDAPSSLSocketFactory(java.lang.String, boolean)</CODE>
     * <CODE>LDAPSSLSocketFactory(java.lang.String, java.lang.Object, boolean)</CODE>
     */
    public void enableClientAuth(String certdb, String keydb, String keypwd,
      String certnickname, String keynickname) throws LDAPException {
        throw new LDAPException("Client auth not supported now");
    }

    /**
     * Returns <code>true</code> if client authentication is enabled.
     * @see com.sun.identity.shared.ldap.LDAPSSLSocketFactory
     */
    public boolean isClientAuth() {
        return m_clientAuth;
    }

    /**
     * Returns the name of the class that implements SSL sockets for this factory.
     *
     * @return the name of the class that implements SSL sockets for this factory.
     */
    public String getSSLSocketImpl() {
        return m_packageName;
    }

    /**
     * Returns the suite of ciphers used for SSL connections made through
     * sockets created by this factory.
     *
     * @return the suite of ciphers used.
     */
    public Object getCipherSuites() {
        return m_cipherSuites;
    }

    /**
     * Returns a socket to the LDAP server with the specified
     * host name and port number.
     * @param host the host to connect to
     * @param port the port number
     * @return the socket to the host name and port number.
     * @exception LDAPException A socket to the specified host and port
     * could not be created.
     * @see com.sun.identity.shared.ldap.LDAPSSLSocketFactory
     */    
    public Socket makeSocket(String host, int port)
      throws LDAPException {

        Socket s = null;

        if (m_clientAuth) {
            try {
                /* Check if running in Communicator; if so, enable client
                   auth */
                String[] types = { "java.lang.String" };
                java.lang.reflect.Method m =
                    DynamicInvoker.getMethod(
                        "com.sun.identity.shared.security.PrivilegeManager",
                        "enablePrivilege",
                        types );
                if (m != null) {
                    Object[] args = new Object[1];
                    args[0] = new String("ClientAuth");
                    m.invoke( null, args);
                }
            } catch (Exception e) {
                String msg = "LDAPSSLSocketFactory.makeSocket: invoking " +
                    "enablePrivilege: " + e.toString();
                throw new LDAPException(msg, LDAPException.PARAM_ERROR);
            }
        }

        try {
            String cipherClassName = null;
            if (m_cipherSuites != null)
                cipherClassName = m_cipherSuites.getClass().getName();

            /* Instantiate the SSLSocketFactory implementation, and
               find the right constructor */
            Class c = Class.forName(m_packageName);
            java.lang.reflect.Constructor[] m = c.getConstructors();
            for (int i = 0; i < m.length; i++) {
                /* Check if the signature is right: String, int */
                Class[] params = m[i].getParameterTypes();
                if ( (m_cipherSuites == null) && (params.length == 2) &&
                     (params[0].getName().equals("java.lang.String")) &&
                     (params[1].getName().equals("int")) ) {
                    Object[] args = new Object[2];
                    args[0] = host;
                    args[1] = new Integer(port);
                    s = (Socket)(m[i].newInstance(args));
                    return s;
                } else if ( (m_cipherSuites != null) && (params.length == 3) &&
                     (params[0].getName().equals("java.lang.String")) &&
                     (params[1].getName().equals("int")) &&
                     (params[2].getName().equals(cipherClassName)) ) {
                    Object[] args = new Object[3];
                    args[0] = host;
                    args[1] = new Integer(port);
                    args[2] = m_cipherSuites;
                    s = (Socket)(m[i].newInstance(args));
                    return s;
                }
            }
            throw new LDAPException("No appropriate constructor in " +
                                  m_packageName,
                                  LDAPException.PARAM_ERROR);
        } catch (ClassNotFoundException e) {
            throw new LDAPException("Class " + m_packageName + " not found",
                                  LDAPException.PARAM_ERROR);
        } catch (Exception e) {
            throw new LDAPException("Failed to create SSL socket",
                                  LDAPException.CONNECT_ERROR);
        }
    }             
    
}

